package com.crypto.payment.repository

import com.crypto.payment.models.TronScanObjectModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/contract/events")
    fun getTronInfo(
        @Query("address") address:String,
        @Query("start") start:String,
        @Query("limit") limit:String,
        @Query("start_timestamp") start_timestamp:Long,
        @Query("end_timestamp") end_timestamp:Long)
        : Call<TronScanObjectModel>
    companion object {
        var BASE_URL = "https://apilist.tronscan.org"
        fun create() : ApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)
        }
    }
}