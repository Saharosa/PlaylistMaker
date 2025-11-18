package com.example.playlistmaker.ID

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.impl.sharedPrefsRepositoryImpl
import com.example.playlistmaker.data.search.impl.TrackHistoryRepositoryImpl
import com.example.playlistmaker.data.search.impl.TrackRepositoryImpl
import com.example.playlistmaker.data.search.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.SharedPrefsRepository
import com.example.playlistmaker.domain.search.api.TrackHistoryRepository
import com.example.playlistmaker.domain.search.api.TrackRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<TrackRepository> { TrackRepositoryImpl(get()) }
    single<TrackHistoryRepository> { TrackHistoryRepositoryImpl(get(),get()) }
    single<SharedPrefsRepository> { sharedPrefsRepositoryImpl(get()) }
}

val networkModule = module {
    single<NetworkClient> { RetrofitNetworkClient() }
}

val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
    single { Gson() }
}
