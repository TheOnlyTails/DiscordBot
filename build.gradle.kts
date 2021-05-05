import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	idea
	application
	id("com.github.johnrengelman.shadow") version "latest.release"
	kotlin("jvm") version "latest.release"
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
	implementation(group = "net.dv8tion", name = "JDA", version = "latest.release")
	implementation(group = "com.nfbsoftware", name = "latex-converter", version = "latest.release")
	implementation(group = "ch.qos.logback", name = "logback-classic", version = "latest.release")
	implementation(group = "io.github.cdimascio", name = "dotenv-kotlin", version = "latest.release")
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions {
		jvmTarget = "16"
	}
}