package com.example.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.Data.repostory.NetworkState
import com.example.Data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable

class SingleMovieViewModel(private val moviesRepositary: MovieDetailsRepositary,id:Int):ViewModel() {

    private val compositeDisposable=CompositeDisposable()

    val movieDetails:LiveData<MovieDetails> by lazy{
        moviesRepositary.fetchSingleMovieDetails(compositeDisposable,id)
    }

    val movieState:LiveData<NetworkState> by lazy{
        moviesRepositary.getMovieNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}