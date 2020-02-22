package com.charles.lab.protonmailtest.repository

import com.charles.lab.protonmailtest.NetWorkApi
import com.charles.lab.protonmailtest.model.WeatherInfo
import retrofit2.Call
import retrofit2.Response

class DataRepository(val netWorkApi: NetWorkApi) {

    fun getUpcoming(onData: OnData) {
        netWorkApi.getUpcoming().enqueue(object : retrofit2.Callback<List<WeatherInfo>> {
            override fun onResponse(
                call: Call<List<WeatherInfo>>,
                response: Response<List<WeatherInfo>>
            ) {
                onData.onSuccess((response.body() as List<WeatherInfo>))
            }

            override fun onFailure(call: Call<List<WeatherInfo>>, t: Throwable) {
                onData.onFailure(t.message)
            }
        })
    }

    fun getHottest(onData: OnData) {
        netWorkApi.getHottest().enqueue(object : retrofit2.Callback<List<WeatherInfo>> {
            override fun onResponse(
                call: Call<List<WeatherInfo>>,
                response: Response<List<WeatherInfo>>
            ) {
                onData.onSuccess((response.body() as List<WeatherInfo>))
            }

            override fun onFailure(call: Call<List<WeatherInfo>>, t: Throwable) {
                onData.onFailure(t.message)
            }
        })
    }

    interface OnData {
        fun onSuccess(data: List<WeatherInfo>)
        fun onFailure(msg: String?)
    }
}