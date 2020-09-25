package com.example.myapplication

import android.app.Application
import com.example.myapplication.data.FactServiceApi
import com.example.myapplication.data.NewsRepository
import com.example.myapplication.data.NewsRepositoryImpl
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.myapplication.data.networkUtil.NetworkResponseAdapterFactory
import com.example.myapplication.domain.GetNewsUseCase
import com.example.myapplication.presenter.NewsPresenter
import com.example.myapplication.presenter.NewsPresenterImpl
import com.example.myapplication.presenter.NewsPresenterState
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }


    val appModule = module {

        viewModel {
            MainViewModel(
                getNewsUseCase = get(),
                presenterState = get()
            )
        }

        single<NewsPresenter> {
            NewsPresenterImpl()
        }
        single<NewsPresenterState> {
            get<NewsPresenter>() as NewsPresenterState
        }

        single {
            GetNewsUseCase(
                repository = get(),
                presenter = get()
            )
        }
        single<NewsRepository> {
            NewsRepositoryImpl(
                api= get()
            )
        }
        single<OkHttpClient> {
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(AuthInterceptor(get()))
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
        }
        single {
            Retrofit.Builder()
                .addCallAdapterFactory(NetworkResponseAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(get())
                .baseUrl("http://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
        }
        single<Gson> {
            GsonBuilder().setLenient().create()
        }
        single { get<Retrofit>().create(FactServiceApi::class.java) }
//
//        single<FactsRepositoryUtil> {
//            FactsRepositoryUtilImpl(
//                factServiceApi = get(),
//                factsDao = get()
//            )
//        }
//
//        single<FactsRepository> {
//            FactRepositoryImpl(
//                factServiceApi = get(),
//                factsDao = get(),
//                factsRepositoryUtil = get()
//            )
//        }
//
//        single<GetFactsUseCase> { GetFactsUseCaseImpl(factRepository = get()) }
//        viewModel { FactsViewModel(getFactsUseCase = get()) }
//
//        single<FactsDao> { FactsDataBase.getInstance(androidApplication()).factsDao() }
    }
}