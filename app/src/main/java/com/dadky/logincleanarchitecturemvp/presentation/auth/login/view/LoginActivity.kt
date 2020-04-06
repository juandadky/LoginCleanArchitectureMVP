package com.dadky.logincleanarchitecturemvp.presentation.auth.login.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.dadky.logincleanarchitecturemvp.R
import com.dadky.logincleanarchitecturemvp.base.BaseActivity
import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.logininteractor.SignInInteractorImpl
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.LoginContract
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.presenter.LoginPresenter
import com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.view.PasswordRecoverActivity
import com.dadky.logincleanarchitecturemvp.presentation.main.view.MainActivity
import com.dadky.logincleanarchitecturemvp.presentation.auth.registro.view.SignUpActivity
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity(),LoginContract.LoginView {

    private lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = LoginPresenter(SignInInteractorImpl())
        presenter.attachView(this)

        btn_signin.setOnClickListener {
            signIn()
        }

        txt_register.setOnClickListener {
            navigateToRegister()
        }

        etxt_password_recovery.setOnClickListener {
            navigateToPasswordRecover()
        }
    }

    override fun getLayout(): Int{
        return R.layout.activity_main
    }

    override fun showError(msgError: String?) {
        toast(this,msgError)
    }

    override fun showProgressBar() {
        progressBar_signin.visibility = View.VISIBLE
        btn_signin.visibility = View.GONE
    }

    override fun hideProgressBar() {
        progressBar_signin.visibility = View.GONE
        btn_signin.visibility = View.VISIBLE
    }

    override fun signIn() {
        val email = etxt_email.text.toString().trim()
        val password = etxt_password.text.toString().trim()
        if(presenter.checkEmptyFields(email,password)){
            toast(this, "Uno o ambos campos son vacios")
        }else{
            presenter.signInUserWithEmailAndPassword(email, password)
        }
    }

    override fun navigateToMain() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun navigateToRegister() {
        startActivity(Intent(this,SignUpActivity::class.java))
    }

    override fun navigateToPasswordRecover() {
        startActivity(Intent(this,PasswordRecoverActivity::class.java))
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.dettachView()
        presenter.detachJob()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dettachView()
        presenter.detachJob()
    }


}
