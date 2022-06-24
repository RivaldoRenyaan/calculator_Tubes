package org.d3if2042.calculator.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if2042.calculator.model.TentangAplikasi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://raw.githubusercontent.com/RivaldoRenyaan/ApiKalkulator/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiServer {
    @GET("Filekalkulator.json")
    suspend fun getTentang(): TentangAplikasi
}

object ApiTentang {
    val service: ApiServer by lazy {
        retrofit.create(ApiServer::class.java)
    }
    fun getGambarUrl(): String {
        return BASE_URL + "kalkulator.png"
    }

}

enum class ApiStatus { LOADING, SUCCESS, FAILED }