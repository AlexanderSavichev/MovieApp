package com.example.kinopoiskapi.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kinopoiskapi.data.repository.models.MovieDto

@Database(entities = [MovieDto::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao():MovieDao

    companion object{
        const val DB_NAME = "movie.db"

        private var INSTANCE: MovieDatabase? = null
        fun getInstance(context: Context): MovieDatabase? {
            if (INSTANCE==null){
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): MovieDatabase?{
            return Room.databaseBuilder(
                context.applicationContext,
                MovieDatabase::class.java,
                DB_NAME
            )
                .build()
        }
    }
}