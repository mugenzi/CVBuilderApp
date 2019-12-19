package com.example.cvbuilderapp.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

class Utils {
    companion object {
        fun showMessage(c: Context, message: String) {
            var toast = Toast.makeText(c, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }
}