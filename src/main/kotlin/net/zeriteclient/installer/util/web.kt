package net.zeriteclient.installer.util

import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import okio.*
import java.io.File
import java.io.FileOutputStream

fun downloadFile(url: String,
                 downloadFile: File,
                 downloadProgressFun: (bytesRead: Long, contentLength: Long, isDone: Boolean) -> Unit) {
    val request = with(Request.Builder()) {
        url(url)
    }.build()
    val client = with(OkHttpClient.Builder()) {
        addNetworkInterceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val responseBody = originalResponse.body
            originalResponse.newBuilder().body(ProgressResponseBody(responseBody!!,
                        downloadProgressFun)).build()
        }
    }.build()
    try {
        val execute = client.newCall(request).execute()
        val outputStream = FileOutputStream(downloadFile)

        val body = execute.body
        body?.let {
            with(outputStream) {
                write(body.bytes())
                close()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

class ProgressResponseBody(val responseBody: ResponseBody,
                           val downloadProgressFun: (bytesRead: Long, contentLength: Long, isDone: Boolean) -> Unit) : ResponseBody() {

    private lateinit var bufferedSource: BufferedSource

    override fun contentLength(): Long = responseBody.contentLength()

    override fun contentType(): MediaType? = responseBody.contentType()

    override fun source(): BufferedSource {
        if (!::bufferedSource.isInitialized) {
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead: Long = 0
            override fun read(sink: Buffer, byteCount: Long): Long {
                val read: Long = super.read(sink, byteCount)
                totalBytesRead += if (read != -1L) read else 0
                downloadProgressFun(totalBytesRead, responseBody.contentLength(), read == -1L)
                return read
            }
        }
    }
}