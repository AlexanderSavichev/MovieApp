package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.adapters.FavouritesAdapter
import com.example.kinopoiskapi.presentation.viewModels.FavouritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class ActivityFavourites : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        val favouritesId:RecyclerView = findViewById(R.id.favouritesId)
        val navView:BottomNavigationView = findViewById(R.id.nav_view)
        val favouritesAdapter = FavouritesAdapter(object : FavouritesAdapter.MovieClickListener{
            override fun onMovieClick(movie: MovieDto) {
                return startActivity(DetailedActivity.mainIntent(this@ActivityFavourites, movie))
            }
        })
        val favouriteViewModel:FavouritesViewModel = ViewModelProvider(this)[FavouritesViewModel::class.java]

        favouritesId.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        favouritesId.adapter = favouritesAdapter
        favouriteViewModel.getAllFavourites()?.observe(this){
            favouritesAdapter.setData(it)
        }
        navView.setOnItemSelectedListener {item->
            when (item.itemId){
                R.id.navigation_home -> {startActivity(MainActivity().mainIntent(this))
                    true}
                R.id.navigation_settings -> {startActivity(ActivityPreferences().settingsIntent(this))
                    true}
                R.id.navigation_search -> {startActivity(ActivitySearch().searchIntent(this))
                    true}
                else -> false
            }
        }
    }

    fun favouriteIntent(context: Context): Intent {
        return Intent(context, ActivityFavourites::class.java)
    }
}