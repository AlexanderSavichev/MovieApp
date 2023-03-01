package com.example.kinopoiskapi.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class AppPreferences (context: Context)
{
    private var sharedPreferences = context.getSharedPreferences(TOKEN, MODE_PRIVATE)
    companion object{
        const val TOKEN = "myToken"
        const val TOKEN_VALUE = "tokenValue"
        const val DEFAULT_TOKEN = "ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06"
    }

    var token: String?
        get() {
            return sharedPreferences.getString(
                TOKEN_VALUE,
                DEFAULT_TOKEN
            )
        }
        set(value) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(TOKEN_VALUE, value)
            editor.apply()
        }

}