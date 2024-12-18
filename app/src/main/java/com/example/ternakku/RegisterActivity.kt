package com.example.ternakku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import com.example.ternakku.base.BaseAuthActivity
import com.example.ternakku.databinding.ActivityRegisterBinding
import com.example.ternakku.domain.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : BaseAuthActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animation()
        initListener()
    }
    private fun initListener() = with(binding) {

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val nama = etNama.text.toString()
            val noTelepon = etNotelp.text.toString()
            val role = spinnerRoleRegister.selectedItem.toString().lowercase()
            val confirmPassword = etConfpassword.text.toString()
            val userDomain = User(
                email = email,
                password = password,
                nama = nama,
                noTelepon = noTelepon,
                role = role
            )

            if (password != confirmPassword) {
                showToast("Password tidak sama!")
                dismissProgressDialog()
                return@setOnClickListener
            }

            showProgressDialog()
            register(userDomain = userDomain) { isSuccess ->
                dismissProgressDialog()
                if (isSuccess) onSuccess()
                else showToast("Register gagal")
            }
        }
    }

    private fun animation() {
        binding.etNama.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in))
        binding.etEmail.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in))
        binding.etNotelp.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in))
        binding.etPassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in))
        binding.etConfpassword.startAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_in))

        binding.btnRegister.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_up))
    }

    private fun onSuccess() {
        lifecycleScope.launch {
            showToast("Register Berhasil")
            delay(500)
            goToLoginActivity()
        }
    }
}