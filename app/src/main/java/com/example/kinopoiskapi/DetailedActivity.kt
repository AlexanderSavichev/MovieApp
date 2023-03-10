package com.example.kinopoiskapi

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
import com.example.kinopoiskapi.presentation.views.FavouriteView
import java.io.Serializable


class DetailedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedBinding
    private lateinit var viewModel: DetailedViewModel
    private lateinit var adapter: TrailerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[DetailedViewModel::class.java]


        val movies: MovieDto = getSerializable(this, MovieDto::class.java)

        adapter = TrailerAdapter(object : TrailerAdapter.TrailerClickListener {
            override fun onPlayClick(trailer: TrailerDto, position: Int) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(trailer.url)
                startActivity(intent)
            }
        })
        binding.cvSmile.setHappiness(FavouriteView.Happiness.NEUTRAL)

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
        binding.trailerRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel.loadTrailers(movies.id, this)
        viewModel.trailers.observe(this) {
            adapter.setData(it)
        }
        viewModel.isMovieFavourite(movies.id)?.observe(this) {
            if (it == null) {
                binding.cvSmile.setHappiness(FavouriteView.Happiness.NEUTRAL)
                FavouriteView.Happiness.NEUTRAL
                binding.cvSmile.setOnClickListener {
                    viewModel.addMovie(movies)
                }
            } else {
                binding.cvSmile.setHappiness(FavouriteView.Happiness.HAPPY)
                FavouriteView.Happiness.HAPPY
                binding.cvSmile.setOnClickListener {
                    viewModel.removeMovie(movies.id)
                }
            }
        }

        binding.textViewRatingIMDB.background = backgroundImdb
        binding.textViewRatingKP.background = backgroundKp
        binding.textViewRatingIMDB.text = movies.rating.imdb.toString()
        binding.textViewRatingKP.text = movies.rating.kp.toString().subSequence(0, 3)
        binding.DetailedTitleId.text = movies.name
        binding.DetailedYearId.text = movies.year.toString()
        binding.DetailedDescriptionId.text = movies.description

        binding.navView.setOnItemSelectedListener {item->
            when (item.itemId){
                R.id.navigation_favourites -> {startActivity(ActivityFavourites().favouriteIntent(this))
                    true}
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

    private fun <MovieDto : Serializable?> getSerializable(detailedActivity: DetailedActivity, java: Class<MovieDto>): MovieDto {
        return if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            detailedActivity.intent.getSerializableExtra(EXTRA_MOVIE, java)!!
        else
            @Suppress("UNCHECKED_CAST", "DEPRECATION")
            detailedActivity.intent.getSerializableExtra(EXTRA_MOVIE) as MovieDto
    }

    companion object {
        const val EXTRA_MOVIE = "movie"
        fun mainIntent(context: Context, movies: MovieDto): Intent {
            val intent = Intent(context, DetailedActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movies)
            return intent
        }
    }
}