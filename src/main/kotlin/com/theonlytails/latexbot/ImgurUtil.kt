package com.theonlytails.latexbot

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import okhttp3.*

fun request(body: Request.Builder.() -> Request.Builder) = Request.Builder().body().build()
fun multipartBody(mediaType: MediaType, body: MultipartBody.Builder.() -> MultipartBody.Builder) =
	MultipartBody.Builder().setType(mediaType).body().build()

fun okHttpClient() = OkHttpClient().newBuilder().build()

@Serializable
data class ImgurImage(val data: JsonObject, val success: Boolean, val status: Int)