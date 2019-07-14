package net.zeriteclient.installer.stages

import com.google.gson.Gson
import com.jfoenix.controls.JFXProgressBar
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import javafx.scene.text.Text
import kfoenix.jfxprogressbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.zeriteclient.installer.*
import net.zeriteclient.installer.model.StorageModel
import net.zeriteclient.installer.model.json.LauncherProfile
import net.zeriteclient.installer.model.json.LauncherProfiles
import net.zeriteclient.installer.model.json.Library
import net.zeriteclient.installer.model.json.Profile
import net.zeriteclient.installer.util.downloadFile
import tornadofx.*
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author Cubxity
 * @since 7/4/2019
 */
class InstallingStage : View() {

    override val root = stackpane {
        try {
            var progressBar: JFXProgressBar? = null
            var headerText: Text? = null
            var subheaderText: Text? = null
            var progressText: Text? = null

            vbox {
                text("ZERITE") {
                    addClass(InstallerStyle.title)
                }
                text("Now installing!") {
                    addClass(InstallerStyle.subHeading)
                }
                vgrow = Priority.ALWAYS
                addClass(InstallerStyle.headerContainer)
            }
            vbox {
                pane {
                    vgrow = Priority.ALWAYS
                }
                vbox {
                    hbox {
                        headerText = text("Downloading") {
                            addClass(InstallerStyle.progressGroup)
                        }
                        pane {
                            hgrow = Priority.ALWAYS
                        }
                    }

                    hbox {
                        subheaderText = text("Downloading Zerite") {
                            fill = Color.WHITE
                        }

                        pane {
                            hgrow = Priority.ALWAYS
                        }

                        progressText = text("0% Complete") {
                            fill = Color.WHITE
                        }
                    }

                    addClass(InstallerStyle.statusContainer)
                }

                progressBar = jfxprogressbar {
                    progress = 0.0
                    useMaxWidth = true
                }
            }

            vgrow = Priority.ALWAYS
            GlobalScope.launch {
                try {
                    while (find<InstallerUI>().root.selectionModel.selectedIndex < 3) {
                    }

                    // Get versions and download URL
                    val version = StorageModel.version ?: return@launch
                    val download = version.downloads.entries.firstOrNull { it.key.equals(StorageModel.mcVersion, true) }?.value
                            ?: return@launch

                    // Create directories
                    zeriteLibraryDir.mkdirs()
                    launchWrapperDir.mkdirs()
                    optiFineDir.mkdirs()
                    zeriteVersionDir.mkdirs()

                    // Base JARs
                    val baseVersionFolder = File(mcDir, "versions$sep${StorageModel.mcVersion}")
                    val baseVersionJar = File(baseVersionFolder, "${StorageModel.mcVersion}.jar")
                    val baseVersionJson = File(baseVersionFolder, "${StorageModel.mcVersion}.json")

                    // Check for local file
                    if (StorageModel.local) {
                        // It is local, simply copy
                        val file = File(javaClass.protectionDomain.codeSource.location.toURI().path)
                        file.copyTo(zeriteLibraryJar, true)
                    } else {
                        // Download Zerite
                        downloadFile(download, zeriteLibraryJar) { bytesRead, contentLength, _ ->
                            val percent = bytesRead.toDouble() / contentLength
                            progressBar!!.progress = percent
                            progressText!!.text = "${(percent * 100).toInt()}% Complete"
                        }
                    }

                    // Set status
                    subheaderText!!.text = "Downloading Launch Wrapper"
                    progressBar!!.progress = 0.0

                    // Download launchwrapper
                    downloadFile(launchWrapperURL, launchWrapperJar) { bytesRead, contentLength, _ ->
                        val percent = bytesRead.toDouble() / contentLength
                        progressBar!!.progress = percent
                        progressText!!.text = "${(percent * 100).toInt()}% Complete"
                    }

                    val useOptifine = StorageModel.optifine && StorageModel.optifineUrl.isNotEmpty()

                    // Check for OptiFine compat
                    if (useOptifine) {
                        // Set status
                        subheaderText!!.text = "Downloading OptiFine"
                        progressBar!!.progress = 0.0

                        // Temp file
                        val tempJar = File.createTempFile("OptiFine", ".jar")

                        // Download OptiFine
                        downloadFile(StorageModel.optifineUrl, tempJar) { bytesRead, contentLength, _ ->
                            val percent = bytesRead.toDouble() / contentLength
                            progressBar!!.progress = percent
                            progressText!!.text = "${(percent * 100).toInt()}% Complete"
                        }

                        // Set status
                        headerText!!.text = "Patching"
                        subheaderText!!.text = "Patching OptiFine"
                        progressBar!!.progress = 0.0
                        progressText!!.text = "0% Complete"

                        // Patch
                        val child = URLClassLoader(arrayOf<URL>(tempJar.toURI().toURL()), javaClass.classLoader)
                        val classToLoad = Class.forName("optifine.Patcher", true, child)
                        val method = classToLoad.getDeclaredMethod("process", File::class.java, File::class.java, File::class.java)
                        method.invoke(null, baseVersionJar, tempJar, optiFineJar)
                    }

                    // Set status
                    headerText!!.text = "Creating profile"
                    subheaderText!!.text = "Reading base profile"
                    progressBar!!.progress = 0.0
                    progressText!!.text = "0% Complete"

                    // Create JSON parser
                    val gson = Gson()

                    // Read to JSON object
                    val profile = gson.fromJson(baseVersionJson.readText(), Profile::class.java)

                    // Modify it
                    profile.minecraftArguments += " --tweakClass $tweakClass"
                    profile.mainClass = "net.minecraft.launchwrapper.Launch"
                    profile.id = "Zerite"

                    // Add libraries
                    profile.libraries.addAll(arrayOf(
                            Library(zeriteMaven, null, null, null, null),
                            Library(launchWrapperMaven, null, null, null, null)
                    ))

                    // Check for OptiFine
                    if (useOptifine) {
                        profile.libraries.add(Library(optiFineMaven, null, null, null, null))
                    }

                    // Set status
                    subheaderText!!.text = "Writing new profile"
                    progressBar!!.progress = 0.25
                    progressText!!.text = "25% Complete"

                    // Write profile
                    zeriteVersionJson.writeText(gson.toJson(profile))

                    // Copy old JAR
                    zeriteVersionJar.writeBytes(baseVersionJar.readBytes())

                    // Set status
                    subheaderText!!.text = "Reading launcher profiles"
                    progressBar!!.progress = 0.5
                    progressText!!.text = "50% Complete"

                    // Read to JSON object
                    val profiles = gson.fromJson(launcherProfilesJson.readText(), LauncherProfiles::class.java)

                    // Values
                    val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(Date())
                    val icon = "data:image/png;base64," + String(Base64.getEncoder().encode(javaClass.getResourceAsStream("/logo.png").readBytes()), Charsets.UTF_8)

                    // Add profile
                    profiles.profiles["Zerite"] = LauncherProfile(dateString, icon, null, dateString, "Zerite", "Zerite", "custom")

                    // Set status
                    subheaderText!!.text = "Writing launcher profiles"
                    progressBar!!.progress = 0.75
                    progressText!!.text = "75% Complete"

                    // Write JSON
                    launcherProfilesJson.writeText(gson.toJson(profiles))

                    // Set status
                    headerText!!.text = "Completed!"
                    subheaderText!!.text = "Open the Minecraft launcher to play"
                    progressBar!!.progress = 1.0
                    progressText!!.text = "100% Complete"
                } catch (e: Exception) {
                }
            }
        } catch (e: Exception) {
        }
    }
}