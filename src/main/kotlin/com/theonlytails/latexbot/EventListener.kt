package com.theonlytails.latexbot

import com.nfbsoftware.latex.LaTeXConverter
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class EventListener : ListenerAdapter() {
	override fun onMessageReceived(event: MessageReceivedEvent) {
		if (event.author.isBot) return

		val command = event.message.contentRaw.removePrefix(prefix)

		when {
			command.startsWith("latex") -> onLatex(event)
			command == "help" -> onHelp(event)
		}
	}
}

fun onHelp(event: MessageReceivedEvent) {
	event.channel.sendMessage(
		EmbedBuilder().apply {
			setTitle("LaTeXBot")
			addField("About", "This bot is used to parse LaTeX expressions. Just type ${prefix}latex", false)
		}.build()
	).queue()
}

fun onLatex(event: MessageReceivedEvent) {
	val latexExpression = event.message.contentRaw.removePrefix("${prefix}latex")

	try {
		event.channel.sendFile(LaTeXConverter.convertToImage(latexExpression)).queue()
	} catch (e: Exception) {
		event.channel.sendMessage(
			EmbedBuilder().apply {
				addField("Something Went wrong! Please try again!", "", false)
			}.build()
		)
	}
}