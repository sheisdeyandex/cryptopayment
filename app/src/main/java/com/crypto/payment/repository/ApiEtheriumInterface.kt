package com.crypto.payment.repository

import com.crypto.payment.models.EtheriumResultModel
import com.crypto.payment.models.TronScanObjectModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEtheriumInterface {
    @GET("api")
    fun getErc20Info(
        @Query("module") module:String,
        @Query("action") action:String,
        @Query("contractaddress") contractaddress:String,
        @Query("address") address:String,
        @Query("page") page:String,
        @Query("offset") offset:String,
        @Query("startblock") startblock:String,
        @Query("endblock") endblock:String,
        @Query("sort") sort:String,
        @Query("apikey") apikey:String,
        )
        : Call<EtheriumResultModel>
    @GET("api")
    fun getEthInfo(
        @Query("module") module:String,
        @Query("action") action:String,
        @Query("address") address:String,
        @Query("page") page:String,
        @Query("offset") offset:String,
        @Query("startblock") startblock:String,
        @Query("endblock") endblock:String,
        @Query("sort") sort:String,
        @Query("apikey") apikey:String,
    )
            : Call<EtheriumResultModel>
    companion object {
        var BASE_URL = "https://api.etherscan.io"
        fun create() : ApiEtheriumInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiEtheriumInterface::class.java)
        }
    }
}