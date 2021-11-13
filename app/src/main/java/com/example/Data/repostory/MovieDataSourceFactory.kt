package com.example.Data.repostory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.vo.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: TheMovieDBInterface, private val compositable: CompositeDisposable):DataSource.Factory<Int,Movie>() {
    val moviesDataSourceLive=MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource=MovieDataSource(apiService,compositable)
        moviesDataSourceLive.postValue(movieDataSource)
        return movieDataSource
    }


}