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

            when {
                nama.isEmpty() -> {
                    showToast("Nama tidak boleh kosong!")
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    showToast("Email tidak boleh kosong!")
                    return@setOnClickListener
                }
                noTelepon.isEmpty() -> {
                    showToast("No Telepon tidak boleh kosong!")
                    return@setOnClickListener
                }
                password.isEmpty() -> {
                    showToast("Password tidak boleh kosong!")
                    return@setOnClickListener
                }
                confirmPassword.isEmpty() -> {
                    showToast("Konfirmasi Password tidak boleh kosong!")
                    return@setOnClickListener
                }
                password != confirmPassword -> {
                    showToast("Password dan Konfirmasi Password tidak sama!")
                    return@setOnClickListener
                }
                password.length < 8 -> {
                    showToast("Password harus memiliki minimal 8 karakter!")
                    return@setOnClickListener
                }
            }

            val userDomain = User(
                email = email,
                password = password,
                nama = nama,
                noTelepon = noTelepon,
                role = role
            )

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