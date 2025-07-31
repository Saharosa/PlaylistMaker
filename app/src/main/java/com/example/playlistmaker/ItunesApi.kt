package com.example.playlistmaker

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {
    @GET("/search?entity=song") // Указываем базовый путь для поиска песен
    fun search(@Query("term") text: String): Call<TrackResponse> // Параметр для поискового текста
}