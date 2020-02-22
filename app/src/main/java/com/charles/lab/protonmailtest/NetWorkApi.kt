package com.charles.lab.protonmailtest

import com.charles.lab.protonmailtest.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET

interface NetWorkApi {

    @GET("/api/forecast/")
    fun getUpcoming(): Call<List<WeatherInfo>>

    @GET("/api/forecast/")
    fun getHottest(): Call<List<WeatherInfo>>

}