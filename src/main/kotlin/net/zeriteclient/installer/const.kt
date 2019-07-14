package net.zeriteclient.installer

import net.zeriteclient.installer.util.findMCDir
import java.io.File

// Separator char
val sep: String = File.separator

// Base dirs
val mcDir = findMCDir()
private val librariesDir = File(mcDir, "libraries")
private val versionsDir = File(mcDir, "versions")

// Base files
val launcherProfilesJson = File(mcDir, "launcher_profiles.json")

// Zerite Version
val zeriteVersionDir = File(versionsDir, "Zerite")
val zeriteVersionJar = File(zeriteVersionDir, "Zerite.jar")
val zeriteVersionJson = File(zeriteVersionDir, "Zerite.json")

// Zerite Library
val zeriteLibraryDir = File(librariesDir, "net${sep}zeriteclient${sep}Zerite${sep}LATEST")
val zeriteLibraryJar = File(zeriteLibraryDir, "Zerite-LATEST.jar")
const val zeriteMaven = "net.zeriteclient:Zerite:LATEST"

// Launchwrapper Library
val launchWrapperDir = File(librariesDir, "net${sep}minecraft${sep}launchwrapper${sep}Zerite")
val launchWrapperJar = File(launchWrapperDir, "launchwrapper-Zerite.jar")
const val launchWrapperURL = "https://libraries.minecraft.net/net/minecraft/launchwrapper/1.7/launchwrapper-1.7.jar"
const val launchWrapperMaven = "net.minecraft:launchwrapper:Zerite"

// OptiFine Library
val optiFineDir = File(librariesDir, "optifine${sep}OptiFine${sep}Zerite")
val optiFineJar = File(optiFineDir, "OptiFine-Zerite.jar")
const val optiFineMaven = "optifine:OptiFine:Zerite"

// Other values
const val tweakClass = "net.zeriteclient.zerite.injection.stages.tweaker.ZeriteTweaker"