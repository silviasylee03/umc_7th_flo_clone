package umc.com.mobile.umc_7th_flo.ui.model

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import umc.com.mobile.umc_7th_flo.ui.model.JoinRetrofitObj.getRetrofit
import umc.com.mobile.umc_7th_flo.data.User

class AuthService {
    private lateinit var signUpView: SignUpView

    fun setSignUpView(signUpView: SignUpView) {
        this.signUpView = signUpView
    }

    fun signUp(user: User) {

        val signUpService = getRetrofit().create(JoinApiItf::class.java)

        signUpService.join(user).enqueue(object : Callback<JoinResponse> {
            override fun onResponse(call: Call<JoinResponse>, response: Response<JoinResponse>) {
                Log.d("JOIN/SUCCESS", response.toString())

                // Check if response body is null
                val resp = response.body()
                if (resp != null) {
                    if (response.isSuccessful) {
                        Log.d("JOIN/SUCCESS", "응답 코드: ${resp.code}, 메시지: ${resp.message}")
                    } else {
                        Log.e("JOIN/FAILURE", "응답 코드: ${resp.code}, 메시지: ${resp.message}")
                    }
                } else {
                    Log.e("JOIN/FAILURE", "응답 본문이 비어있습니다.")
                }
            }

            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {
                //실패처리
            }
        })
    }

}