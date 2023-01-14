package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kinopoiskapi.data.repository.models.MovieDto
import com.example.kinopoiskapi.data.repository.models.TrailerDto
import com.example.kinopoiskapi.databinding.ActivityDetailedBinding
import com.example.kinopoiskapi.presentation.adapters.TrailerAdapter
import com.example.kinopoiskapi.presentation.elements.RatingDefiner
import com.example.kinopoiskapi.presentation.viewModels.DetailedViewModel


class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var viewModel:DetailedViewModel
    private lateinit var adapter: TrailerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DetailedViewModel::class.java]

        val movies: MovieDto = intent.getSerializableExtra(EXTRA_MOVIE) as MovieDto
        adapter = TrailerAdapter(object : TrailerAdapter.TrailerClickListener{
            override fun onPlayClick(trailer: TrailerDto, position: Int) {
                println("Click!!!")
            }
        })

        val backgroundKp: Drawable? = ContextCompat.getDrawable(
            this, RatingDefiner().colorOfRatingKp(movies)
        )
        val backgroundImdb: Drawable? =
            ContextCompat.getDrawable(
                this, RatingDefiner().colorOfRatingImdb(movies)
            )

        Glide.with(this)
            .load(movies.poster.url)
            .into(binding.PosterId)

        binding.trailerRecycler.adapter = adapter
        binding.trailerRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.loadTrailers(movies.id)
        viewModel.trailers.observe(this){
            adapter.setData(it)
        }

        binding.textViewRatingIMDB.background = backgroundImdb
        binding.textViewRatingKP.background = backgroundKp
        binding.textViewRatingIMDB.text = movies.rating.imdb.toString()
        binding.textViewRatingKP.text = movies.rating.kp.toString().subSequence(0, 3)
        binding.DetailedTitleId.text = movies.name
        binding.DetailedYearId.text = movies.year.toString()
        binding.DetailedDescriptionId.text = movies.description
    }

    companion object{
        const val EXTRA_MOVIE = "movie"
        fun mainIntent(context: Context, movies:MovieDto):Intent{
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movies)
            return intent
        }
    }
}