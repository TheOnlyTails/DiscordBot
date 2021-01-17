package com.theonlytails.latexbot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import java.io.File
import java.util.Scanner

lateinit var jda: JDA

fun main() {
	//noinspection SpellCheckingInspection
	val pathFile = File("C:\\Users\\shach\\OneDrive\\Desktop\\DiscordBotToken\\DiscordBotTokenPath.txt")
	val pathScanner = Scanner(pathFile)

	val tokenFile = File(pathScanner.nextLine())
	val tokenScanner = Scanner(tokenFile)

	val token = tokenScanner.nextLine()

	jda = JDABuilder
		.createDefault(token)
		.build()

	jda.presence.setPresence(OnlineStatus.DO_NOT_DISTURB,
		Activity.listening("testing-channel"))

	jda.addEventListener(EventListener())
}