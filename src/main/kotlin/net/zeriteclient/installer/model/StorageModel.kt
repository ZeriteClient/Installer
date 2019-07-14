package net.zeriteclient.installer.model

import com.google.gson.JsonObject

object StorageModel {

    var local = false
    var optifineUrl = ""
    var version: VersionModel? = null
    var mcVersion = "1.8.9"
    var optifine = true

    val meta = hashMapOf<String, JsonObject>()
    val versions = hashMapOf<String, VersionModel>()

}