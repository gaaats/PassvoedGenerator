package com.example.passhashgenerator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class MainVievModel @Inject constructor() : ViewModel() {

    private var _textResult = MutableLiveData<String>()
    val textResult: LiveData<String>
        get() = _textResult


    fun getHash(text: String, algorithm: String): String {
        val typeSHA = checkType(algorithm)
        val bytes = MessageDigest.getInstance(typeSHA).digest(text.toByteArray())
        _textResult.value = toHex(bytes)
        return toHex(bytes)

    }

    private fun toHex(bytes: ByteArray): String {
        Log.d("MY_TAG", bytes.joinToString("") { "%02x".format(it) })
        return bytes.joinToString { "%02x".format(it) }
    }

    private fun checkType(algorithm: String): String {
        return when (algorithm) {
            "SIMPLE" -> "MD5"
            "NORMAL" -> "SHA256"
            "HARD" -> "SHA512"
            else -> throw RuntimeException("MainVievModel--checkType--error, no such type")
        }
    }
}