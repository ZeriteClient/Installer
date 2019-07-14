package net.zeriteclient.installer.stages

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import javafx.scene.layout.Priority
import javafx.scene.text.Text
import kfoenix.jfxbutton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.zeriteclient.installer.InstallerStyle
import net.zeriteclient.installer.InstallerUI
import net.zeriteclient.installer.model.StorageModel
import net.zeriteclient.installer.model.VersionModel
import tornadofx.*
import java.net.URL

class WelcomeStage : View() {
    override val root = vbox {
        var setupText: Text? = null

        vbox {
            text("ZERITE") {
                addClass(InstallerStyle.title)
            }

            setupText = text("Loading manifest") {
                addClass(InstallerStyle.subHeading)
            }

            vgrow = Priority.ALWAYS
            addClass(InstallerStyle.headerContainer)
        }

        vbox {
            addClass(InstallerStyle.buttonBar)
            jfxbutton("BEGIN INSTALLATION") {
                action {
                    if (StorageModel.meta.size > 0) {
                        find<VersionSelector> {
                            list?.items?.addAll(StorageModel.meta.keys)
                        }
                        find<InstallerUI> { root.selectionModel.select(1) }
                    }
                }
            }
        }

        GlobalScope.launch {
            val gson = Gson()
            val res = JsonParser().parse(URL("https://raw.githubusercontent.com/Zerite/Repo/master/json/installer_manifest.json").readText()).asJsonObject

            println(res["meta"])

            StorageModel.meta.putAll(gson.fromJson(res["meta"], object : TypeToken<HashMap<String, JsonObject>>() {}.type))
            StorageModel.versions.putAll(gson.fromJson(res["versions"], object : TypeToken<HashMap<String, VersionModel>>() {}.type))

            println(StorageModel.versions)

            setupText?.text = "Minecraft, refined."
        }
    }

    init {
        currentWindow?.apply {
            width = 1280.0
            height = 720.0
            currentStage?.isResizable = false
        }
    }
}