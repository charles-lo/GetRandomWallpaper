package com.charles.lab.protonmailtest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charles.lab.protonmailtest.model.WeatherInfo
import com.charles.lab.protonmailtest.repository.DataRepository
import org.koin.core.KoinComponent


class ProtonViewModel(val dataRepository: DataRepository) : ViewModel(), KoinComponent {
    private val TAG = ProtonViewModel::class.simpleName
    var listOfUpcoming = MutableLiveData<List<WeatherInfo>>()
    var listOfHottest = MutableLiveData<List<WeatherInfo>>()
    var networkError = MutableLiveData<Boolean>()

    init {
        listOfUpcoming.value = listOf()
        listOfHottest.value = listOf()
    }

    fun getUpcoming() {
        dataRepository.getUpcoming(object : DataRepository.OnData {
            override fun onSuccess(data: List<WeatherInfo>) {
                listOfUpcoming.value = data
            }

            override fun onFailure(msg: String?) {
                //REQUEST FAILED
                msg?.let{
                    Log.d(TAG, msg)
                }
                networkError.value = true
            }
        })
    }

    fun getHottest() {
        dataRepository.getHottest(object : DataRepository.OnData {
            override fun onSuccess(data: List<WeatherInfo>) {
                listOfHottest.value = data
            }

            override fun onFailure(msg: String?) {
                //REQUEST FAILED
                msg?.let{
                    Log.d(TAG, msg)
                }
                networkError.value = true
            }
        })
    }
}