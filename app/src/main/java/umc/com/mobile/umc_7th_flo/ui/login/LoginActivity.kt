package umc.com.mobile.umc_7th_flo.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import umc.com.mobile.umc_7th_flo.MainActivity
import umc.com.mobile.umc_7th_flo.ui.signup.SignUpActivity
import umc.com.mobile.umc_7th_flo.data.SongDatabase
import umc.com.mobile.umc_7th_flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.Main).launch {
            login() // 코루틴 내에서 호출
        }
    }

    private suspend fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email: String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd: String = binding.loginPasswordEt.text.toString()

        val user = withContext(Dispatchers.IO) {
            val songDB = SongDatabase.getInstance(this@LoginActivity)!!
            songDB.userDao().getUser(email, pwd) // 데이터베이스 작업
        }

        if (user != null) {
            Log.d("LoginActivity", user.id.toString())
            saveJwt(user.id)
            startMainActivity()
        } else {
            Toast.makeText(this, "회원 정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveJwt(jwt : Int) {
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}