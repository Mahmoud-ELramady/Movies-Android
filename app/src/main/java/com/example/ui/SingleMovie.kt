package com.example.ui

import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.Data.api.POSTER_BASE_URL
import com.example.Data.api.TheMovieDBClient
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.repostory.NetworkState
import com.example.Data.vo.MovieDetails
import com.example.movies.R
import com.example.single_movie_details.MovieDetailsRepositary
import com.example.single_movie_details.SingleMovieViewModel
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.util.*

class SingleMovie : AppCompatActivity() {
    lateinit var viewModel: SingleMovieViewModel
    lateinit var movieRepo: MovieDetailsRepositary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)


        val id=intent.getIntExtra("id",1)

        val apiService:TheMovieDBInterface=TheMovieDBClient.getClient()

        movieRepo= MovieDetailsRepositary(apiService)

        viewModel=getViewModel(id)

        viewModel.movieDetails.observe(this, Observer {
            bindU(it)
        })

        viewModel.movieState.observe(this, Observer {
            pr.visibility=if (it== NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility=if (it== NetworkState.ERROR) View.VISIBLE else View.GONE
        })



    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun bindU(it: MovieDetails) {
        tv_title.text=it.title
        tv_rating.text= it.rating.toString()
        tv_run_time.text= it.runtime.toString()+" minutes"
        tv_release_date.text=it.releaseDate
        tv_overview.text=it.overview
        tv_sub_title.text=it.tagline

        val formatCurrency:NumberFormat=NumberFormat.getCurrencyInstance(Locale.US)
        tv_budget.text=formatCurrency.format(it.budget)
        tv_revenue.text=formatCurrency.format(it.revenue)

        val moviePosterURL= POSTER_BASE_URL+it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(iv_image_poster)


    }

    fun getViewModel(id:Int): SingleMovieViewModel {
        return ViewModelProvider(this,object :ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("Unchecked_Cast")
                return SingleMovieViewModel(movieRepo, id) as T
            }
        })[SingleMovieViewModel::class.java]

    }


}