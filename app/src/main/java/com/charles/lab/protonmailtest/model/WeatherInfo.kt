package com.charles.lab.protonmailtest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class WeatherInfo(
    @SerializedName("day") val day: Int,
    @SerializedName("description") val description: String,
    @SerializedName("sunrise") val sunrise: Int,
    @SerializedName("sunset") val sunset: Int,
    @SerializedName("chance_rain") val chance_rain: Double,
    @SerializedName("high") val high: Int,
    @SerializedName("low") val low: Int,
    @SerializedName("image") val image: String
) : Serializable