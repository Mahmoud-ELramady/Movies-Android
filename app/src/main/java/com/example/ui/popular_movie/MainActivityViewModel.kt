package com.example.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.Data.repostory.NetworkState
import com.example.Data.vo.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivityViewModel(val movieRepo:MoviePageListRepositary) : ViewModel() {
    private val composit=CompositeDisposable()

    val moviePagedList:LiveData<PagedList<Movie>> by lazy {
        movieRepo.FetchLiveMoviePagedList(composit)
    }


    val networkState:LiveData<NetworkState> by lazy {
        movieRepo.getNetworkState()
    }

    fun ListIsEmpty():Boolean{
        return moviePagedList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        composit.dispose()
    }


}