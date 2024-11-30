package umc.com.mobile.umc_7th_flo.ui.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.com.mobile.umc_7th_flo.data.User

interface JoinApiItf {
    @POST("/join")
    fun join(@Body user : User): Call<JoinResponse>
}