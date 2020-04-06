package com.dadky.logincleanarchitecturemvp.presentation.auth.registro.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dadky.logincleanarchitecturemvp.R
import com.dadky.logincleanarchitecturemvp.base.BaseActivity
import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.registerinteractor.SignUpInteractorImpl
import com.dadky.logincleanarchitecturemvp.presentation.main.view.MainActivity
import com.dadky.logincleanarchitecturemvp.presentation.auth.registro.RegisterContract
import com.dadky.logincleanarchitecturemvp.presentation.auth.registro.presenter.SignUpPresenter
import kotlinx.android.synthetic.main.activity_register.*

class SignUpActivity : BaseActivity(),RegisterContract.RegisterView {

    private lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = SignUpPresenter(SignUpInteractorImpl())
        presenter.attachView(this)

        btn_signup.setOnClickListener {
            signUp()
        }
    }

    override fun getLayout(): Int {
        return R.layout.activity_register
    }

    override fun navigateToMain() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun signUp() {
        val fullname = etxt_fullname.text.toString().trim()
        val email = etxt_email_registro.text.toString().trim()
        val pw1 = etxt_pw1.text.toString().trim()
        val pw2 = etxt_pw2.text.toString().trim()

        if(presenter.checkEmptyName(fullname)){
            etxt_fullname.error = "El nombre esta vacio"
            return
        }

        if(!presenter.checkValidEmail(email)){
            etxt_email_registro.error = "El email no es válido"
            return
        }

        if(presenter.checkEmptyPasswords(pw1,pw2)){
            etxt_pw1.error = "campo vacio"
            etxt_pw2.error = "campo vacio"
            return
        }

        if(!presenter.checkPasswordMatch(pw1,pw2)){
            etxt_pw2.error = "no son iguales las contraseñas"
            return
        }

        presenter.signUp(fullname,email,pw1)

    }

    override fun showProgress() {
        progress_signup.visibility = View.VISIBLE
        btn_signup.visibility = View.GONE
    }

    override fun hideProgress() {
        progress_signup.visibility = View.GONE
        btn_signup.visibility = View.VISIBLE
    }

    override fun showError(errormsg: String?) {
        toast(this,errormsg)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.detachView()
        presenter.detachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.detachJob()
    }

}
