package ch.protonmail.android.protonmailtest

import ch.protonmail.android.protonmailtest.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET

interface NetWorkApi {

    @GET("/api/forecast/")
    fun getUpcoming(): Call<List<WeatherInfo>>

    @GET("/api/forecast/")
    fun getHottest(): Call<List<WeatherInfo>>

}