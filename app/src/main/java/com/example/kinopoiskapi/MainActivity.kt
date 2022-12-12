package com.example.kinopoiskapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinopoiskapi.adapters.MovieAdapter
import com.example.kinopoiskapi.databinding.ActivityMainBinding
import com.example.kinopoiskapi.viewModels.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var moviesAdapter: MovieAdapter
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.Theme_KinopoiskAPI)
        } else {
            setTheme(R.style.Theme_KinopoiskAPINight)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sw.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        moviesAdapter = MovieAdapter()
        binding.recyclerViewMovies.adapter = moviesAdapter
        binding.recyclerViewMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.movies.observe(this) { t ->
            moviesAdapter.movies = t
        }
        viewModel.loadMovies()
        initSchrollListener()

    }

    fun initSchrollListener() {
        binding.recyclerViewMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val recyclerViewManager: LinearLayoutManager =
                    binding.recyclerViewMovies.layoutManager as LinearLayoutManager
                if (recyclerViewManager.findLastVisibleItemPosition() == moviesAdapter.movies.size - 1)
                    viewModel.loadMovies()
            }
        })
    }


}


