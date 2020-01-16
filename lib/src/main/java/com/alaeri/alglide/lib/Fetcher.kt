package com.alaeri.alglide.lib

import androidx.annotation.WorkerThread
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentLength
import timber.log.Timber
import java.nio.ByteBuffer


class Fetcher {

    private val client = HttpClient(OkHttp)


    @Suppress("BlockingMethodInNonBlockingContext")
    @WorkerThread
    suspend fun fetch(url: String): ByteArray {
        val byteArray = client.get<ByteArray>(url) {}
//        Timber.d("ktor response: $response")
//        val contentLength = response.contentLength() ?: throw IllegalArgumentException("ContentLength not found")
//        val byteArray = ByteArray(contentLength.toInt())
//        val byteBuffer = ByteBuffer.wrap(byteArray)
//        response.content.readFully(byteBuffer)
        return byteArray
    }

}