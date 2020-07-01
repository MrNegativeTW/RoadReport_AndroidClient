package com.txwstudio.app.roadreport.service

import com.txwstudio.app.roadreport.json.weather.WeatherJson
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
https://opendata.cwb.gov.tw/api/v1/rest/datastore/O-A0001-001?
Authorization=CWB-5E768A58-42AC-42E6-AE2F-078BE496737A&
format=JSON&
elementName=TEMP&
stationId=C0R130
 */

private const val BASE_URL = "https://opendata.cwb.gov.tw/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET(
        "api/v1/rest/datastore/O-A0001-001?" +
                "Authorization=CWB-5E768A58-42AC-42E6-AE2F-078BE496737A&" +
                "format=JSON&" +
                "elementName=TEMP"
    )
    fun getWeatherTemp(@Query("stationId") stationId: String):
            Call<WeatherJson>

    @GET(
        "api/v1/rest/datastore/O-A0002-001?" +
                "Authorization=CWB-5E768A58-42AC-42E6-AE2F-078BE496737A&" +
                "format=JSON&" +
                "elementName=MIN_10"
    )
    fun getWeatherHum(@Query("stationId") stationId: String):
            Call<WeatherJson>
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}