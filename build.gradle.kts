import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	idea
	application
	id("com.github.johnrengelman.shadow") version "latest.release"
	kotlin("jvm") version "latest.release"
	kotlin("plugin.serialization") version "latest.release"
}

val mavenGroup: String by extra
val version: String by extra
val jdaVersion: String by extra

repositories {
	mavenCentral()
	maven(url = "https://m2.dv8tion.net/releases")
	maven(url = "https://jitpack.io")
}

dependencies {
	// implementation(group = "net.dv8tion", name = "JDA", version = "latest.release")
	implementation(group = "com.github.dv8fromtheworld", name = "jda", version = "feature~slash-commands-SNAPSHOT")
	implementation(group = "com.github.minndevelopment", name = "jda-ktx", version = "master-SNAPSHOT")
	implementation(group = "com.nfbsoftware", name = "latex-converter", version = "latest.release")
	implementation(group = "ch.qos.logback", name = "logback-classic", version = "latest.release")
	implementation(group = "io.github.cdimascio", name = "dotenv-kotlin", version = "latest.release")
	implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.9.0")
	implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version = "latest.release")
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions {
		jvmTarget = "16"
	}
}