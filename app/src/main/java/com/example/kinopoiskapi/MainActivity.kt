package com.example.kinopoiskapi


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.adapters.MovieAdapter
import com.example.kinopoiskapi.presentation.adapters.OnReachEndListener
import com.example.kinopoiskapi.databinding.ActivityMainBinding
import com.example.kinopoiskapi.presentation.viewModels.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moviesAdapter = MovieAdapter(object : MovieAdapter.MovieClickListener {

            override fun onMovieClick(movie: MovieDto, position: Int) {
                return startActivity(DetailedActivity.mainIntent(this@MainActivity, movie))
            }
        })
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.recyclerViewMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.movies.observe(this) { t ->
            moviesAdapter.setData(t)
        }
        if (savedInstanceState == null){
                viewModel.loadMovies(this)
        }

        viewModel.isLoading.observe(this) { loading ->
            if (loading)
                binding.progressLoading.visibility = View.VISIBLE
            else
                binding.progressLoading.visibility = View.GONE
        }
        moviesAdapter.setOnReachEndListener(object : OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies(this@MainActivity)
            }
        })
        binding.navView.setOnItemSelectedListener {item->
            when (item.itemId){
                R.id.navigation_favourites -> {startActivity(ActivityFavourites().favouriteIntent(this))
                true}
                R.id.navigation_settings -> {startActivity(ActivityPreferences().settingsIntent(this))
                true}
                R.id.navigation_search -> {startActivity(ActivitySearch().searchIntent(this))
                true}
                else -> false
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    fun mainIntent(context: Context): Intent {
        return Intent(context, MainActivity::class.java)
    }



}


