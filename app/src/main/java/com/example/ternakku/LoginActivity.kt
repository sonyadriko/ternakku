package com.example.ternakku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.ternakku.base.BaseAuthActivity
import com.example.ternakku.databinding.ActivityLoginBinding

class LoginActivity : BaseAuthActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        binding.tvWarning.visibility = TextView.INVISIBLE

        // Live validation for password
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validatePassword(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupListeners() = with(binding) {
        val createAccountText = "Don't have an account? Create now"
        val spannable = SpannableString(createAccountText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }

        // Apply styles to "Create now"
        val startIndex = createAccountText.indexOf("Create now")
        val endIndex = createAccountText.length

        spannable.apply {
            setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(this@LoginActivity, R.color.primary_color)),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Set the styled text to TextView
        binding.tvCreateAccount.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }

        btnLogin.setOnClickListener {
            if (etEmail.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()) {
                showToast(getString(R.string.error_empty_fields)) // Replace with localized string
                return@setOnClickListener
            }

            if (validatePassword(etPassword.text.toString())) {
                showProgressDialog()
                login(etEmail.text.toString(), etPassword.text.toString(), doOnFailed = {
                    dismissProgressDialog()
                    showToast(getString(R.string.error_account_not_found)) // Replace with localized string
                })
            } else {
                showPasswordError()
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.length >= 8) {
            binding.tvWarning.visibility = TextView.INVISIBLE
            true
        } else {
            binding.tvWarning.apply {
                text = getString(R.string.error_password_length) // Replace with localized string
                visibility = TextView.VISIBLE
            }
            false
        }
    }

    private fun showPasswordError() {
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        binding.etPassword.startAnimation(shake)
    }
}
