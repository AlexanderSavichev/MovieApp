package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.kinopoiskapi.databinding.ActivityPreferencesBinding

class ActivityPreferences : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(TOKEN, Context.MODE_PRIVATE)

        binding.darkModeGroup.setOnCheckedChangeListener { _, id ->
            when (id){
                binding.darkButtonOn.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.darkButtonOff.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.darkButtonAuto.id -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }

        binding.navView.setOnItemSelectedListener {item->
            when (item.itemId){
                R.id.navigation_favourites -> {startActivity(ActivityFavourites().favouriteIntent(this))
                    true}
                R.id.navigation_home -> {startActivity(MainActivity().mainIntent(this))
                    true}
                R.id.navigation_search -> {startActivity(ActivitySearch().searchIntent(this))
                    true}
                else -> false
            }
        }

        binding.setTokenButton.setOnClickListener {
            val n: String = binding.setToken.text.toString()
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(TOKEN_VALUE, n)
            editor.apply()
            binding.setToken.setText("")
        }

        binding.setDefaultTokenButton.setOnClickListener {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(TOKEN_VALUE, DEFAULT_TOKEN)
            editor.apply()
            val toast = Toast.makeText(this.applicationContext, MESSAGE_TOKEN, Toast.LENGTH_LONG)
            toast.show()
        }


    }

    fun settingsIntent(context: Context): Intent {
        return Intent(context, ActivityPreferences::class.java)
    }

    fun getToken(context: Context): String? {
        val sharedPreferences:SharedPreferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)
        return sharedPreferences.getString(TOKEN_VALUE, DEFAULT_TOKEN)
    }

    companion object{
        const val TOKEN = "myToken"
        const val TOKEN_VALUE = "tokenValue"
        const val DEFAULT_TOKEN = "ZQQ8GMN-TN54SGK-NB3MKEC-ZKB8V06"
        const val MESSAGE_TOKEN = "Гостевой токен применен"
    }
}