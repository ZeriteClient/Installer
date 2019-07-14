package net.zeriteclient.installer.model.json

import com.google.gson.JsonArray
import com.google.gson.JsonObject

data class Profile(
        var assetIndex: JsonObject,
        var assets: String,
        var downloads: JsonObject,
        var id: String,
        var libraries: ArrayList<Library>,
        var logging: JsonObject,
        var mainClass: String,
        var minecraftArguments: String,
        var minimumLauncherVersion: Int,
        var releaseTime: String,
        var time: String,
        var type: String
)

data class Library(
        var name: String,
        var rules: JsonArray?,
        var downloads: JsonObject?,
        var extract: JsonObject?,
        var natives: JsonObject?
)