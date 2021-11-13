package com.example.Data.api

import com.example.Data.vo.MovieDetails
import com.example.Data.vo.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/popular?api_key=9fcc1366b88e4a312921db128d1fc029&page=1
    //https://api.themoviedb.org/3/movie/popular?api_key=9fcc1366b88e4a312921db128d1fc029
    //https://api.themoviedb.org/3/


    @GET("movie/popular")
    fun getMoviePopular(@Query("page") page:Int): Single<MovieResponse>





    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int): Single<MovieDetails>
}