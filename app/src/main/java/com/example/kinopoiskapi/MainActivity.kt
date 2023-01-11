package com.example.kinopoiskapi


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.presentation.adapters.MovieAdapter
import com.example.kinopoiskapi.presentation.adapters.OnReachEndListener
import com.example.kinopoiskapi.databinding.ActivityMainBinding
import com.example.kinopoiskapi.presentation.viewModels.MainViewModel
import com.example.kinopoiskapi.presentation.views.FavouriteView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding
    private val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler{ coroutineContext, throwable -> println("$throwable") }
    private val scope= CoroutineScope(Dispatchers.Main + coroutineExceptionHandler )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.darkModeButton.setOnClickListener {
            showDarkModeDialog()
        }

        moviesAdapter = MovieAdapter(object : MovieAdapter.SmileClickListener {
            override fun onSmileClick(movie: MovieDto, position:Int, happiness:FavouriteView.Happiness) {

                when(happiness){
                    FavouriteView.Happiness.NEUTRAL -> {
                        movie.isFavourite = true
                        movie.isBad = false
                        binding.recyclerViewMovies.adapter?.notifyItemChanged(position)
                    }
                    FavouriteView.Happiness.HAPPY -> {
                        movie.isBad = true
                        movie.isFavourite = false
                        binding.recyclerViewMovies.adapter?.notifyItemChanged(position)
                    }
                    FavouriteView.Happiness.SAD -> {
                        movie.isBad = false
                        movie.isFavourite = false
                        binding.recyclerViewMovies.adapter?.notifyItemChanged(position)
                    }
                }
            }
        })
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.recyclerViewMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.movies.observe(this) { t ->
            moviesAdapter.setData(t)
        }
        scope.launch {
            viewModel.loadMovies()
        }

        moviesAdapter.setOnReachEndListener(object : OnReachEndListener {
            override fun onReachEnd() {
                scope.launch { viewModel.loadMovies() }
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
                when(selectedMode){
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
}


