import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	java
	idea
	application
	id("com.github.johnrengelman.shadow") version "6.1.0"
	kotlin("jvm") version "1.4.21"
}

val mavenGroup: String by extra
val version: String by extra
val jdaVersion: String by extra

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	testImplementation(group = "junit", name = "junit", version = "4.12")

	implementation(group = "net.dv8tion", name = "JDA", version = jdaVersion)
	implementation(group = "com.nfbsoftware", name = "latex-converter", version = "1.0.1")
	implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

tasks.withType<KotlinCompile>().configureEach {
	kotlinOptions {
		jvmTarget = "15"
		languageVersion = "1.4"
	}
}