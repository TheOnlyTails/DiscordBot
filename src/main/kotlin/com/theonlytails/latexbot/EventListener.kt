package com.theonlytails.latexbot

import com.nfbsoftware.latex.LaTeXConverter
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody.Companion.FORM
import okhttp3.RequestBody.Companion.asRequestBody
import org.scilab.forge.jlatexmath.ParseException
import java.awt.Color

class EventListener : ListenerAdapter() {
	override fun onSlashCommand(event: SlashCommandEvent) {
		if (event.user.isBot) return

		when (event.name) {
			"latex" -> onLatex(event)
			"help" -> onHelp(event)
		}
	}
}

fun onHelp(event: SlashCommandEvent) {
	event.reply {
		embed {
			author(
				"Made by TheOnlyTails",
				"https://theonlytails.com/",
				jda.getUserById(645291351562518542L)?.avatarUrl
					?: throw NullPointerException("The avatar for TheOnlyTails couldn't be found.")
			)
			title("LaTeXBot")
			field("About", "This bot is used to parse LaTeX expressions. Just type `${prefix}latex <expression>`")
			color(Color.green)
		}
	}
}

fun onLatex(event: SlashCommandEvent) {
	event.acknowledge().queue()

	val latexExpression = event["expression"].asString

	try {
		val latexImage = LaTeXConverter.convertToImage(latexExpression)

		val body = multipartBody(FORM) {
			addFormDataPart("image", latexImage.name, latexImage.asRequestBody("text/plain".toMediaType()))
		}

		val request = request {
			url("https://api.imgur.com/3/image")
			method("POST", body)
			addHeader("Authorization", "Client-ID ${dotenv()["IMGUR_CLIENT"]}")
		}

		val response = okHttpClient().newCall(request).execute().body?.string()
			?: throw NoSuchElementException("Couldn't fetch the Imgur response.")

		val (data, success, status) = Json.decodeFromString<ImgurImage>(response)

		if (!success) logger.severe("Failed getting image from Imgur, status code = $status")

		logger.info(data["link"].toString())

		event.hook.editOriginal(
			embed {
				author(event.user.name, iconUrl = event.user.effectiveAvatarUrl)
				title("LaTeX Rendering Result")
				field("Processed expression:", "`$latexExpression`")
				image(data["link"].toString().trim('"'))
				footer("LaTeXBot by TheOnlyTails", botAvatar)
				color(Color.green)
			}
		).queue()
	} catch (e: ParseException) {
		e.printStackTrace()
		event.hook.editOriginal(
			embed {
				field(
					"Something Went wrong! Please try again!",
					"The expression `$latexExpression` is invalid."
				)
				color(Color.red)
			}
		).queue()
	}
}