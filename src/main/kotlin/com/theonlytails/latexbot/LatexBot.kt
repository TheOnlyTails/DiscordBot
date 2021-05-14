package com.theonlytails.latexbot

import io.github.cdimascio.dotenv.dotenv
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.interactions.commands.OptionType.STRING
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val prefix = "/"
val logger: Logger = LoggerFactory.getLogger("LaTeXBot")

val jda = JDABuilder
	.createDefault(dotenv()["TOKEN"] ?: throw IllegalArgumentException("token not found"))
	.addEventListeners(EventListener())
	.setActivity(Activity.listening("testing"))
	.setStatus(OnlineStatus.DO_NOT_DISTURB)
	.build()

val botAvatar = jda.getUserById(745331926100869161L)?.effectiveAvatarUrl
	?: throw NullPointerException("The avatar for TheOnlyTails couldn't be found.")

fun main() {
	jda.getGuildById(758284094827266048L)?.updateCommands()?.addCommands(
		command("help", "How to use the bot."),
		command("latex", "Parses a LaTeX expression and replies with a rendered version.") {
			+option(STRING, "expression", "The expression parsed by the command.") {
				isRequired = true
			}
		}
	)?.queue()
}