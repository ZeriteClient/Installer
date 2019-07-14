package net.zeriteclient.installer

import net.zeriteclient.installer.stages.FeaturesSelector
import net.zeriteclient.installer.stages.InstallingStage
import net.zeriteclient.installer.stages.VersionSelector
import net.zeriteclient.installer.stages.WelcomeStage
import javafx.scene.control.TabPane
import javafx.scene.input.KeyCode
import kfoenix.jfxtabpane
import tornadofx.View
import tornadofx.setStageIcon
import tornadofx.tab


/**
 * @author Cubxity
 * @since 7/4/2019
 */

class InstallerUI : View("Zerite Installer") {
    override val root = jfxtabpane {
        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
        tab(WelcomeStage())
        tab(VersionSelector())
        tab(FeaturesSelector())
        tab(InstallingStage())
//        setOnKeyPressed { if (it.code == KeyCode.TAB && it.isControlDown) it.consume() }
    }

    init {
        setStageIcon(resources.image("/logo.png"))
    }
}