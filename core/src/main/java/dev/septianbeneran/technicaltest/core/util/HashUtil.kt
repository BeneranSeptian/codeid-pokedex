package dev.septianbeneran.technicaltest.core.util

import java.security.MessageDigest

object HashUtil {
    fun hashString(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}