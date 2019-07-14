package net.zeriteclient.installer

import javafx.geometry.Pos
import javafx.scene.layout.BackgroundPosition
import javafx.scene.paint.Color
import javafx.scene.paint.Color.rgb
import kfoenix.JFXStylesheet.Companion.jfxListView
import kfoenix.JFXStylesheet.Companion.jfxProgressBar
import kfoenix.JFXStylesheet.Companion.jfxUnselectedColor
import tornadofx.*


/**
 * @author Cubxity
 * @since 7/4/2019
 */
class InstallerStyle : Stylesheet() {
    companion object {
        val all by csselement("*")

        val title by cssclass()
        val buttonBar by cssclass()
        val subHeading by cssclass()
        val headerContainer by cssclass()
        val featuresContainer by cssclass()
        val stepHeader by cssclass()
        val progressGroup by cssclass()
        val statusContainer by cssclass()

        val btnOn by cssclass()
        val btnOff by cssclass()

        val jfxButton by cssclass()
        val jfxTabPane by cssclass()
    }

    init {
        root {
            backgroundImage += javaClass.getResource("/background.png").toURI()
            backgroundPosition += BackgroundPosition.CENTER
            alignment = Pos.TOP_CENTER
            font = loadFont("/fonts/Roboto-Regular.ttf", 14)!!
        }
        all {
            alignment = Pos.TOP_CENTER
        }
        title {
            font = loadFont("/fonts/Roboto-Bold.ttf", 60)!!
            fill = Color.WHITE
        }
        subHeading {
            font = loadFont("/fonts/Roboto-Medium.ttf", 16)!!
            fill = Color.WHITE
        }
        jfxButton {
            font = loadFont("/fonts/Roboto-Regular.ttf", 15)!!
            backgroundColor += rgb(255, 255, 255, 0.3)
            textFill = Color.WHITE
            prefWidth = 300.px
            padding = box(5.px)
            borderColor += box(Color.WHITE)
            borderRadius += box(3.px)
        }
        headerContainer {
            alignment = Pos.CENTER
        }
        featuresContainer {
            padding = box(10.px)
            spacing = 10.px
        }
        progressGroup {
            font = loadFont("/fonts/Roboto-Medium.ttf", 22)!!
            fill = Color.WHITE
        }
        stepHeader {
            padding = box(50.px, 0.px)
        }
        jfxTabPane {
            tabMaxHeight = 0.px
            tabHeaderArea {
                visibility = FXVisibility.HIDDEN
            }
        }
        jfxListView {
            maxWidth = 320.px
            listCell {
                backgroundColor += rgb(0, 0, 0, .0)
                jfxUnselectedColor.set(Color.GREY)
            }
            backgroundColor += rgb(255, 255, 255, 0.3)
            borderColor += box(Color.WHITE)
            borderRadius += box(3.px)
        }
        buttonBar {
            padding = box(50.px)
        }
        btnOn {
            backgroundColor += rgb(61, 165, 115)
        }
        btnOff {
            backgroundColor += rgb(180, 90, 96)
        }
        jfxProgressBar {
            prefHeight = 30.px
            bar {
                backgroundColor += rgb(41, 182, 246)
            }
        }
        statusContainer {
            padding = box(10.px)
        }
    }
}