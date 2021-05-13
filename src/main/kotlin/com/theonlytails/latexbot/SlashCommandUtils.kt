package com.theonlytails.latexbot

import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.OptionData

@JvmInline
value class SlashCommand(val commandData: CommandData) {
	operator fun OptionData.unaryPlus() = commandData.addOption(this)
}

operator fun CommandData.plus(optionData: OptionData) = this.addOption(optionData)

fun option(type: OptionType, name: String, description: String, body: OptionData.() -> Unit = {}) =
	OptionData(type, name, description).apply(body)

fun command(name: String, description: String, body: SlashCommand.() -> CommandData = { this.commandData }) =
	SlashCommand(CommandData(name, description)).body()

operator fun SlashCommandEvent.get(name: String) =
	getOption(name) ?: throw IllegalArgumentException("Invalid Parameter Received")

fun SlashCommandEvent.reply(embed: () -> MessageEmbed) = reply(embed()).queue()