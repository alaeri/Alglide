package com.alaeri.alglide.lib

import kotlinx.coroutines.delay

class Fetcher {
    suspend fun fetch(url: String): ByteArray {
        //dummy fetch
        delay(2000)
        return ByteArray(2000000)
    }
}