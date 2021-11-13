package com.example.Data.repostory

import android.util.Log
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.vo.MovieDetails
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsNetworkDataSource(private val apiService:TheMovieDBInterface, private val compositable: CompositeDisposable) {
    private val _networkState=MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState


    private val _downloadMovieDataResponse=MutableLiveData<MovieDetails>()
    val downloadMovieDataResponse: LiveData<MovieDetails>
        get() = _downloadMovieDataResponse


    fun fetchMovieDetails(id:Int){
        _networkState.postValue(NetworkState.LOADING)

        try {

            compositable.add(apiService.getMovieDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _downloadMovieDataResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("movieDetails",it.message)
                    }
                )


            )

        }catch (e:Exception){
            Log.e("movieDetails",e.message)

        }



    }


}