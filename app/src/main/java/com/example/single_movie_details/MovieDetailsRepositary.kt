package com.example.single_movie_details

import androidx.lifecycle.LiveData
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.repostory.MovieDetailsNetworkDataSource
import com.example.Data.repostory.NetworkState
import com.example.Data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MovieDetailsRepositary(private val apiService:TheMovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource:MovieDetailsNetworkDataSource
    lateinit var movieRepo:MovieDetailsRepositary

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable,id:Int):LiveData<MovieDetails>{
        movieDetailsNetworkDataSource= MovieDetailsNetworkDataSource( apiService,compositeDisposable)
movieDetailsNetworkDataSource.fetchMovieDetails(id)
        return movieDetailsNetworkDataSource.downloadMovieDataResponse
    }


    fun getMovieNetworkState(): LiveData<NetworkState> {
     return   movieDetailsNetworkDataSource.networkState
    }



}