package com.patres.automation.util

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

object Base64Converter {

    fun calculateByteArrayOutputStream(inputStream: InputStream): ByteArrayOutputStream {
        val imageByteArrayOutputStream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int = inputStream.read(buffer)
        while (len > -1) {
            imageByteArrayOutputStream.write(buffer, 0, len)
            len = inputStream.read(buffer)
        }
        imageByteArrayOutputStream.flush()
        return imageByteArrayOutputStream
    }

    fun base64ToInputStream(string: String): InputStream {
        return ByteArrayInputStream(Base64.getDecoder().decode(string))
    }

    fun base64ToByteArray(base64: String): ByteArray = calculateByteArrayOutputStream(base64ToInputStream(base64)).toByteArray()
}