package ch.protonmail.android.protonmailtest.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.protonmail.android.protonmailtest.model.WeatherInfo
import ch.protonmail.android.protonmailtest.repository.DataRepository
import org.koin.standalone.KoinComponent


class ProtonViewModel(val dataRepository: DataRepository) : ViewModel(), KoinComponent {
    private val TAG = ProtonViewModel::class.qualifiedName
    var listOfUpcoming = MutableLiveData<List<WeatherInfo>>()
    var listOfHottest = MutableLiveData<List<WeatherInfo>>()

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
            }
        })
    }
}