package com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dadky.logincleanarchitecturemvp.R
import com.dadky.logincleanarchitecturemvp.base.BaseActivity
import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.passwordrecoverinteractor.PasswordRecoverImpl
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.view.LoginActivity
import com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.PasswordRecoverContract
import com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.presenter.PasswordRecoverPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_password_recover.*

class PasswordRecoverActivity : BaseActivity(),PasswordRecoverContract.PasswordRecoverView {

    private lateinit var presenter: PasswordRecoverPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = PasswordRecoverPresenter(PasswordRecoverImpl())
        presenter.attachView(this)

        btn_passwordrecovery.setOnClickListener {
            recoverPassword()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_password_recover
    }

    override fun showError(errormsg: String?) {
        toast(this,errormsg)
    }

    override fun showProgress() {
        progress_recover_pw.visibility = View.VISIBLE
        btn_passwordrecovery.visibility = View.GONE
    }

    override fun hideProgress() {
        progress_recover_pw.visibility = View.GONE
        btn_passwordrecovery.visibility = View.VISIBLE
    }

    override fun recoverPassword() {
        val email = etxt_recover_pw.text.trim().toString()
        if(!presenter.checkEmailValid(email)){
            etxt_recover_pw.error = "El email no es válido"
            return
        }
        presenter.sedPasswordRecover(email)
    }

    override fun navigateToLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        toast(this, "Email para recuperar contraseña enviado")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.detachJob()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
        presenter.detachJob()
    }
}
