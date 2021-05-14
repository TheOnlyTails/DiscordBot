package com.theonlytails.latexbot

import dev.minn.jda.ktx.Embed
import io.github.cdimascio.dotenv.dotenv
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody.Companion.FORM
import okhttp3.RequestBody.Companion.asRequestBody
import org.scilab.forge.jlatexmath.JMathTeXException
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
		Embed {
			author {
				name = "LaTeXBot"
				url = "https://github.com/theonlytails/latexbot/"
				iconUrl = botAvatar
			}
			title = "LaTeXBot"
			field {
				name = "About"
				value =
					"This bot is used to parse [LaTeX](https://www.latex-project.org/) expressions. Just type `${prefix}latex <expression>`"
			}
			footer {
				name = "Made by TheOnlyTails"
				iconUrl = jda.getUserById(645291351562518542L)?.avatarUrl
					?: throw NullPointerException("The avatar for TheOnlyTails couldn't be found.")
			}
			color = Color.green.rgb
		}
	}
}

fun onLatex(event: SlashCommandEvent) {
	// sets up a deferred reply
	event.acknowledge().queue()

	// gets the expression
	val latexExpression = event["expression"].asString

	fun error() = event.hook.editOriginal(
		Embed {
			field {
				name = "Something Went wrong! Please try again!"
				value = "The expression `$latexExpression` is invalid."
			}
			color = Color.red.rgb
		}
	).queue()

	try {
		// parses it into a rendered image
		val latexImage = latexExpression.parseLatex()

		// prepares the request to the imgur API
		val request = request {
			url("https://api.imgur.com/3/image")
			method("POST", multipartBody(FORM) {
				addFormDataPart("image", latexImage.name, latexImage.asRequestBody("text/plain".toMediaType()))
			})
			addHeader("Authorization", "Client-ID ${dotenv()["IMGUR_CLIENT"]}")
		}

		// executes the request and gets the response JSON
		val response = okHttpClient().newCall(request).execute().body?.string()
			?: throw NoSuchElementException("Couldn't fetch the Imgur response.").also { error() }

		// destructures the response
		val (data, success, status) = Json.decodeFromString<ImgurImage>(response)

		// gets the link from the image's data
		val link = data["link"].toString().trim('"')

		if (!success) {
			logger.error("Failed getting image from Imgur, status code = $status")
			error()
		}

		logger.debug(link)

		event.hook.editOriginal(
			Embed {
				author {
					name = event.user.name
					iconUrl = event.user.effectiveAvatarUrl
				}
				title = "LaTeX Rendering Result"
				field {
					name = "Processed expression:"
					value = "`$latexExpression`"
				}
				image = link
				footer {
					name = "LaTeXBot by TheOnlyTails"
					iconUrl = botAvatar
				}

				color = Color.green.rgb
			}).queue()
	} catch (e: JMathTeXException) {
		logger.error(e.localizedMessage)
		error()
	}
}