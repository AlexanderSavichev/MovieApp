package com.example.kinopoiskapi


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.adapters.MovieAdapter
import com.example.kinopoiskapi.presentation.adapters.OnReachEndListener
import com.example.kinopoiskapi.databinding.ActivityMainBinding
import com.example.kinopoiskapi.presentation.viewModels.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.darkModeButton.setOnClickListener {
            showDarkModeDialog()
        }

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
        if (savedInstanceState == null)
            viewModel.loadMovies()
        viewModel.isLoading.observe(this) { loading ->
            if (loading)
                binding.progressLoading.visibility = View.VISIBLE
            else
                binding.progressLoading.visibility = View.GONE
        }
        moviesAdapter.setOnReachEndListener(object : OnReachEndListener {
            override fun onReachEnd() {
                viewModel.loadMovies()
            }
        })

    }

    private fun showDarkModeDialog() {
        var selectedIndex = 0
        val items = arrayOf("Dark Mode On", "Dark Mode Off", "Dark Mode Auto")
        var selectedMode: String = items[selectedIndex]
        MaterialAlertDialogBuilder(this)
            .setTitle("Dark mode settings")
            .setSingleChoiceItems(items, selectedIndex) { _, which ->
                selectedIndex = which
                selectedMode = items[which]
            }
            .setPositiveButton("Ok") { _, _ ->
                when (selectedMode) {
                    "Dark Mode On" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "Dark Mode Off" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "Dark Mode Auto" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourites_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favouritesId)
            startActivity(ActivityFavourites().favouriteIntent(this))
        return super.onOptionsItemSelected(item)
    }
}


