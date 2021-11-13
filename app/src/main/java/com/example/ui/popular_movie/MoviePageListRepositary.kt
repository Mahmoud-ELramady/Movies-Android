package com.example.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.Data.api.POST_PER_PAGE
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.repostory.MovieDataSource
import com.example.Data.repostory.MovieDataSourceFactory
import com.example.Data.repostory.NetworkState
import com.example.Data.vo.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MoviePageListRepositary(private val apiService:TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataFactory:MovieDataSourceFactory

    fun FetchLiveMoviePagedList(composit:CompositeDisposable):LiveData<PagedList<Movie>>{
        movieDataFactory= MovieDataSourceFactory(apiService,composit)
        val config=PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList=LivePagedListBuilder(movieDataFactory,config).build()
        return moviePagedList
    }


    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource,NetworkState>(
            movieDataFactory.moviesDataSourceLive,MovieDataSource::networkState
        )
    }

}