package net.zeriteclient.installer.stages

import javafx.scene.layout.Priority
import kfoenix.jfxbutton
import net.zeriteclient.installer.InstallerStyle
import net.zeriteclient.installer.InstallerUI
import net.zeriteclient.installer.model.StorageModel
import tornadofx.*


/**
 * @author Cubxity
 * @since 7/4/2019
 */
class FeaturesSelector : View() {
    private var optifine = true

    override val root = vbox {
        vbox {
            addClass(InstallerStyle.stepHeader)
            text("ZERITE") {
                addClass(InstallerStyle.title)
            }
            text("Choose your features") {
                addClass(InstallerStyle.subHeading)
            }
        }
        vbox {
            vgrow = Priority.ALWAYS
            jfxbutton("OPTIFINE") {
                addClass(InstallerStyle.btnOn)
                action {
                    optifine = if (optifine) {
                        removeClass(InstallerStyle.btnOn)
                        addClass(InstallerStyle.btnOff)
                        false
                    } else {
                        removeClass(InstallerStyle.btnOff)
                        addClass(InstallerStyle.btnOn)
                        true
                    }
                }
            }
            addClass(InstallerStyle.featuresContainer)
        }
        vbox {
            addClass(InstallerStyle.buttonBar)
            jfxbutton("CONTINUE") {
                action {
                    StorageModel.optifine = optifine
                    find<InstallerUI> { root.selectionModel.select(3) }
                }
            }
        }
    }
}