package com.example.ui.popular_movie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.Data.api.TheMovieDBClient
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.repostory.NetworkState
import com.example.movies.R
import com.example.single_movie_details.SingleMovieViewModel
import com.example.ui.SingleMovie
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepo:MoviePageListRepositary
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService:TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepo=MoviePageListRepositary(apiService)
        viewModel=getViewModel()
        val movieAdapter=PopularMoviePageListAdapter(this)
        val gridLayoutManager=GridLayoutManager(this,3)
        gridLayoutManager.spanSizeLookup=object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType=movieAdapter.getItemViewType(position)
                if (viewType==movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        };

        rv_main.layoutManager=gridLayoutManager
        rv_main.setHasFixedSize(true)
        rv_main.adapter=movieAdapter

        viewModel.moviePagedList.observe(this, Observer {

            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            pr_main.visibility=if (viewModel.ListIsEmpty() && it== NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_main.visibility= if (viewModel.ListIsEmpty() && it== NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.ListIsEmpty()){
                movieAdapter.setNetworkState(it)
            }
        })

    }


    fun getViewModel(): MainActivityViewModel {
        return ViewModelProvider(this,object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("Unchecked_Cast")
                return MainActivityViewModel(movieRepo) as T
            }
        })[MainActivityViewModel::class.java]

    }
}