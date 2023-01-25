package com.kotlin.thirdprojectfirebase.ui.register

import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {

    fun isPhoneValid(phone: String): Boolean = when {
        phone.isEmpty() -> false
        phone.length != 10 -> false
        else -> true
    }
}