package com.wzy.arms.di.module

import com.wzy.arms.di.qualifier.AppUrl
import com.wzy.arms.network.api.ApiService
import com.wzy.arms.network.helper.OkHttpHelper
import com.wzy.arms.network.helper.RetrofitHelper
import com.wzy.arms.network.support.ApiConstants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author: wzy
 * date: 2019/11/11
 * desc:Api网络模型
 */
@Module
class ApiModule {
    fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient, baseUrl: String): Retrofit {
        return builder
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpHelper.getOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Provides
    @Singleton
    fun provideRetrofitHelper(apiService: ApiService): RetrofitHelper {
        return RetrofitHelper(apiService)
    }

    @Singleton
    @Provides
    @AppUrl
    fun provideAppRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, ApiConstants.APP_BASE_URL)
    }

    @Singleton
    @Provides
    fun provideApiService(@AppUrl retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
