package net.zeriteclient.installer

import javafx.application.Application
import net.zeriteclient.installer.model.StorageModel
import tornadofx.App
import tornadofx.reloadStylesheetsOnFocus

class ZeriteInstaller : App(InstallerUI::class, InstallerStyle::class) {
    init {
        reloadStylesheetsOnFocus()
    }
}

fun main(args: Array<String>) {
    StorageModel.local = args.isNotEmpty() && args[0].equals("local", ignoreCase = true)
    Application.launch(ZeriteInstaller::class.java)
}
