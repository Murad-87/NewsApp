package com.example.testapp1.utils

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.showToastLL(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun Fragment.showToastLS(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.handleBackButtonPressed(action: () -> Boolean) {
    val callBack = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            action.invoke()
        }
    }

    requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
}