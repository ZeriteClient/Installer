import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.41"
    id("com.github.johnrengelman.shadow") version "2.0.4"
    id("maven")
}

group = "net.zeriteclient"
version = "0.1-PRE"

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M2")
    compile("com.github.bkenn:kfoenix:0.1.3")
    compile("com.google.code.gson:gson:2.2.4")
    compile("com.squareup.okhttp3:okhttp:4.0.1")
}

repositories {
    jcenter()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    manifest.attributes["Main-Class"] = "net.zeriteclient.installer.ZeriteInstallerKt"
}