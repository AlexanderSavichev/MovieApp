package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kinopoiskapi.databinding.ActivitySearchBinding

class ActivitySearch : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.setOnItemSelectedListener {item->
            when (item.itemId){
                R.id.navigation_favourites -> {startActivity(ActivityFavourites().favouriteIntent(this))
                    true}
                R.id.navigation_settings -> {startActivity(ActivityPreferences().settingsIntent(this))
                    true}
                R.id.navigation_home -> {startActivity(MainActivity().mainIntent(this))
                    true}
                else -> false
            }
        }

    }

    fun searchIntent(context: Context): Intent {
        return Intent(context, ActivitySearch::class.java)
    }
}