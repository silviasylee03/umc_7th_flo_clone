package umc.com.mobile.umc_7th_flo.ui.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object JoinRetrofitObj { // Retrofit 객체
    // base url
    private const val BASE_URL = "http://3.35.121.185"

    private var retrofit: Retrofit? = null

    fun getRetrofit(): Retrofit {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }
}