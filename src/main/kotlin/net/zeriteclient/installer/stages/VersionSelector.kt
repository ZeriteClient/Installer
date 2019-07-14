package net.zeriteclient.installer.stages

import com.jfoenix.controls.JFXListView
import javafx.scene.layout.Priority
import kfoenix.jfxbutton
import kfoenix.jfxlistview
import net.zeriteclient.installer.InstallerStyle
import net.zeriteclient.installer.InstallerUI
import net.zeriteclient.installer.model.StorageModel
import tornadofx.*

class VersionSelector : View() {
    var list: JFXListView<String>? = null

    override val root = vbox {
        vbox {
            addClass(InstallerStyle.stepHeader)
            text("ZERITE") {
                addClass(InstallerStyle.title)
            }
            text("Select a Minecraft version") {
                addClass(InstallerStyle.subHeading)
            }
        }

        list = jfxlistview {
            asyncItems { StorageModel.meta.keys }
            vgrow = Priority.ALWAYS
        }

        vbox {
            addClass(InstallerStyle.buttonBar)
            jfxbutton("CONTINUE") {
                action {
                    val entry = StorageModel.meta.entries.firstOrNull { v ->
                        v.key.equals(list!!.selectedItem ?: return@action, ignoreCase = true)
                    } ?: return@action

                    StorageModel.mcVersion = entry.key
                    StorageModel.version = StorageModel.versions.entries.firstOrNull { it.key == entry.value.get("client").asString }?.value
                            ?: return@action
                    StorageModel.optifineUrl = entry.value.get("optifine")?.asString ?: ""

                    find<InstallerUI> { root.selectionModel.select(2) }
                }
            }
        }
    }
}