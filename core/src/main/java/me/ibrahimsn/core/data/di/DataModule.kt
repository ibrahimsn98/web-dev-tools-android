package me.ibrahimsn.core.data.di

import android.content.Context
import android.content.SharedPreferences
import me.ibrahimsn.core.data.database.AppDatabase
import me.ibrahimsn.core.data.source.DefaultPreferences
import me.ibrahimsn.core.domain.source.LocalDataSource
import org.koin.dsl.module

private const val SECRET_KEY = "t3sT_m4sT3r_k3Y"

val dataModule = module {

    single {
        provideSharedPrefs(context = get())
    }

    single {
        providePreferences(sharedPreferences = get())
    }

    factory {
        AppDatabase.getDatabase(context = get())
    }
}

private fun provideSharedPrefs(context: Context): SharedPreferences = context.getSharedPreferences(
    SECRET_KEY, Context.MODE_PRIVATE
)

fun providePreferences(sharedPreferences: SharedPreferences): LocalDataSource.Preferences =
    DefaultPreferences(sharedPreferences)
