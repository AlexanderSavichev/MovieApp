package com.example.kinopoiskapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.adapters.MovieAdapter
import com.example.kinopoiskapi.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var switch:Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_KinopoiskAPI)
        } else {
            setTheme(R.style.Theme_KinopoiskAPINight)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        switch = findViewById(R.id.sw)
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies)
        moviesAdapter = MovieAdapter()
        switch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        recyclerViewMovies.adapter = moviesAdapter
        recyclerViewMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(this, Observer { t ->
            moviesAdapter.movies = t
        })
        viewModel.loadMovies()
    }
}