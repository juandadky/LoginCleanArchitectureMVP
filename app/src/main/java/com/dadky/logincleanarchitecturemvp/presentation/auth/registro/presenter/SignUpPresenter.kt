package com.dadky.logincleanarchitecturemvp.presentation.auth.registro.presenter

import androidx.core.util.PatternsCompat
import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.registerinteractor.SignUpInteractor
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.exceptions.FirebaseLoginException
import com.dadky.logincleanarchitecturemvp.presentation.auth.registro.RegisterContract
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SignUpPresenter(signUpInteractor:SignUpInteractor): RegisterContract.RegisterPresenter,CoroutineScope {

    var view: RegisterContract.RegisterView? = null
    var signUpInteractor:SignUpInteractor? = null

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.signUpInteractor = signUpInteractor
    }

    override fun attachView(view: RegisterContract.RegisterView) {
        this.view = view
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun detachView() {
        view = null
    }

    override fun detachJob() {
        coroutineContext.cancel()
    }

    override fun checkEmptyName(fullname: String): Boolean {
        return fullname.isEmpty()
    }

    override fun checkValidEmail(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun checkEmptyPasswords(pw1: String, pw2: String): Boolean {
        return pw1.isEmpty() or pw2.isEmpty()
    }

    override fun checkPasswordMatch(pw1: String, pw2: String): Boolean {
        return pw1 == pw2
    }

    override fun signUp(fullname: String, email: String, password: String) {

        launch {
            try {
                if (isViewAttached()) {
                    view?.showProgress()
                    signUpInteractor?.signUp(fullname, email, password)
                    view?.navigateToMain()
                    view?.hideProgress()
                }
            } catch (e: FirebaseLoginException) {
                if (isViewAttached()) {
                    view?.showError(e.message)
                    view?.hideProgress()
                }
            }
        }
    }
}