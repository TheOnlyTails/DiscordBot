package com.theonlytails.latexbot

import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

fun embed(embed: EmbedBuilder.() -> EmbedBuilder) = EmbedBuilder().embed().build()

fun EmbedBuilder.field(name: String, description: String = "", inline: Boolean = false) =
	addField(name, description, inline)

fun EmbedBuilder.title(title: String) = setTitle(title)
fun EmbedBuilder.color(color: Color) = setColor(color)
fun EmbedBuilder.image(url: String) = setImage(url)
fun EmbedBuilder.footer(text: String, iconUrl: String = "") = setFooter(text, iconUrl.ifBlank { null })