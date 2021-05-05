package com.theonlytails.latexbot

import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity

const val prefix = "!"

val jda = JDABuilder
	.createDefault(dotenv()["TOKEN"] ?: throw IllegalArgumentException("token not found"))
	.build()

fun main() {
	jda.presence.setPresence(
		OnlineStatus.DO_NOT_DISTURB,
		Activity.listening("testing-channel")
	)

	jda.addEventListener(EventListener())
}