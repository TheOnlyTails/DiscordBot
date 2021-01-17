package com.theonlytails.latexbot

import com.nfbsoftware.latex.LaTeXConverter
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

const val prefix = "!"

class EventListener : ListenerAdapter() {
	override fun onMessageReceived(event: MessageReceivedEvent) {
		if (event.author.isBot) return

		val message = event.message.contentRaw

		if (message == "${prefix}help") {
			event.channel.sendMessage(EmbedBuilder()
				.setTitle("LaTeXBot")
				.addField("About", "This bot is used to parse LaTeX expressions. Just type !latex", false)
				.build()).queue()
		}

		if (message.startsWith("${prefix}latex")) {
			val latexExpression = message.removePrefix("${prefix}latex")

			try {
				event.channel.sendFile(LaTeXConverter.convertToImage(latexExpression)).queue()
			} catch (e: Exception) {
				event.channel.sendMessage(EmbedBuilder()
					.addField("Something Went wrong! Please try again!", "", false)
					.build())
			}
		}
	}
}