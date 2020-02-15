package ch.protonmail.android.protonmailtest

import ch.protonmail.android.protonmailtest.repository.DataRepository
import ch.protonmail.android.protonmailtest.viewmodel.ProtonViewModel
import com.google.gson.GsonBuilder
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = module {

    single { DataRepository(get()) }

    single { createWebService() }

    viewModel { ProtonViewModel(get()) }

}

fun createWebService(): NetWorkApi {
    val gson = GsonBuilder()
        .setLenient()
        .create()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("https://5c5c8ba58d018a0014aa1b24.mockapi.io")
        .build()

    return retrofit.create(NetWorkApi::class.java)
}