package com.example.passhashgenerator.vievmodel

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passhashgenerator.utils.EventVraper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class MainVievModel @Inject constructor(private val application: Application) : ViewModel() {

    private val _isSplashScreenActive = MutableStateFlow(true)
    val isSplashScreenActive = _isSplashScreenActive.asStateFlow()

    init {
        viewModelScope.launch {
            delay(600)
            _isSplashScreenActive.value = false
        }
    }

    private var _textResult = MutableLiveData<String>()
    val textResult: LiveData<String>
        get() = _textResult

    private var _eventForSnackBar = MutableLiveData<EventVraper<String>>()
    val eventForSnackBar: LiveData<EventVraper<String>>
        get() = _eventForSnackBar


    fun getHash(text: String, algorithm: String) {
        val typeSHA = checkTypeAlgorithm(algorithm)
        val bytes = MessageDigest.getInstance(typeSHA).digest(text.toByteArray())
        _textResult.value = toHex(bytes)
    }

    private fun toHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun checkTypeAlgorithm(algorithm: String): String {
        return when (algorithm) {
            "SIMPLE" -> "MD5"
            "NORMAL" -> "SHA256"
            "HARD" -> "SHA512"
            else -> throw RuntimeException("MainVievModel--checkType--error, no such type")
        }
    }

    fun saveToClipBoard() {
        val clipboardManager =
            application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        ClipData.newPlainText("Encrypted text", textResult.value).also {
            clipboardManager.setPrimaryClip(it)
        }
        _eventForSnackBar.value = EventVraper("Copied")
    }

}