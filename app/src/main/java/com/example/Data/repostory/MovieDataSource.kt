package com.example.Data.repostory

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.Data.api.FIRST_PAGE
import com.example.Data.api.TheMovieDBInterface
import com.example.Data.vo.Movie
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDataSource (val apiService:TheMovieDBInterface,val composite:CompositeDisposable)
    : PageKeyedDataSource<Int, Movie>() {

    private var page= FIRST_PAGE
    val networkState:MutableLiveData<NetworkState> = MutableLiveData()




    override fun loadInitial(arams: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)
        composite.add(apiService.getMoviePopular(page)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                callback.onResult(it.movieList,null,page+1)
                    networkState.postValue(NetworkState.LOADED)
                },{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource",it.message)

                }
            )
        )

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        networkState.postValue(NetworkState.LOADING)
        composite.add(apiService.getMoviePopular(params.key)
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                  if (it.totalPages>=params.key){
                      callback.onResult(it.movieList,params.key+1)
                      networkState.postValue(NetworkState.LOADED)
                  }else{
                      networkState.postValue(NetworkState.ENDOFLIST)
                  }

                }  ,{
                    networkState.postValue(NetworkState.ERROR)
                    Log.e("MovieDataSource",it.message)

                }
            )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
    }

}