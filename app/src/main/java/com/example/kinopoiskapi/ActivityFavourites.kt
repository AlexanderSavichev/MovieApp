package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.presentation.adapters.FavouritesAdapter
import com.example.kinopoiskapi.presentation.viewModels.FavouritesViewModel


class ActivityFavourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val favouritesId:RecyclerView = findViewById(R.id.favouritesId)
        val favouritesAdapter = FavouritesAdapter()
        val favouriteViewModel:FavouritesViewModel = ViewModelProvider(this)[FavouritesViewModel::class.java]

        favouritesId.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        favouritesId.adapter = favouritesAdapter
        favouriteViewModel.getAllFavourites()?.observe(this){
            favouritesAdapter.setData(it)
        }
    }

    fun favouriteIntent(context: Context): Intent {
        return Intent(context, ActivityFavourites::class.java)
    }
}