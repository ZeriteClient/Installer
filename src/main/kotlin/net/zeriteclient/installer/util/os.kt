package net.zeriteclient.installer.util

import java.io.File

fun findMCDir(): File {
    val os = System.getProperty("os.name").toLowerCase()
    return if (os.contains("mac")) {
        File(System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + "minecraft" + File.separator);
    } else if (os.contains("nix") || os.contains("nux")) {
        File(System.getProperty("user.home") + File.separator + ".minecraft");
    } else {
        File(System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + ".minecraft" + File.separator);
    }
}