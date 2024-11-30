package umc.com.mobile.umc_7th_flo.ui.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import umc.com.mobile.umc_7th_flo.ui.model.SignUpView
import umc.com.mobile.umc_7th_flo.data.User
import umc.com.mobile.umc_7th_flo.databinding.ActivitySignUpBinding
import umc.com.mobile.umc_7th_flo.ui.model.AuthService

class SignUpActivity : AppCompatActivity() , SignUpView {
    lateinit var binding: ActivitySignUpBinding
    private var email : String = ""
    private var password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpSignUpBtn.setOnClickListener {
//            email = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
//            password = binding.signUpPasswordEt.text.toString()
//
//            val signUpCompletion = signUp()
//            if(signUpCompletion) {
//                finish()
//            }
            signUp()

//            Log.d("message", JoinResponse.message)
//            Log.d("액세스 토큰 값", loginResponse.result.accessToken)
//
//            // 로그인 연동 후 받은 토큰 값 저장
//            var token: String = loginResponse.result.accessToken
//            Log.d("토큰 값", token)
//
//            // 로그인 연동 후 받은 아이디 저장
//            var id: Int = JoinResponse.result.id
//            Log.d("Nickname액티비티 사용자 아이디 값", id.toString())
//
//            saveToken(token)
//            saveId(id)
        }
    }

    private fun getUser() : User {
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()
        val name : String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

    private fun signUp() {
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }

//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val user = userDB.userDao().getUsers()
//        Log.d("sign-up", user.toString())
//
//        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())

        Log.d("SIGNUP-ACT/ASYNC", "Hello, FLO")
    }

    // 토큰을 SharedPreferences에 저장
    private fun saveToken(token: String){
        val sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString("token", token)
            apply()
        }
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        Log.d("SIGNUP-ACT/FAILURE", "회원가입 실패")
    }

}