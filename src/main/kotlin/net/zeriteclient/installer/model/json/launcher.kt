package net.zeriteclient.installer.model.json

import com.google.gson.JsonObject

data class LauncherProfiles(
        var analyticsFailCount: Int,
        var analyticsToken: String,
        var authenticationDatabase: JsonObject,
        var clientToken: String,
        var launcherVersion: JsonObject,
        var profiles: HashMap<String, LauncherProfile>,
        var selectedUser: JsonObject,
        var settings: JsonObject
)

data class LauncherProfile(
        var created: String,
        var icon: String,
        var javaArgs: String?,
        var lastUsed: String,
        var name: String,
        var lastVersionId: String,
        var type: String
)