package com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.presenter

import androidx.core.util.PatternsCompat
import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.passwordrecoverinteractor.PasswordRecover
import com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.PasswordRecoverContract
import com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover.exceptions.PasswordRecoverException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PasswordRecoverPresenter(passwordRecoverInteractor: PasswordRecover): PasswordRecoverContract.PasswordRecoverPresenter,CoroutineScope {


    private var passwordRecoverInteractor: PasswordRecover? = null
    private var view: PasswordRecoverContract.PasswordRecoverView? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.passwordRecoverInteractor = passwordRecoverInteractor
    }

    override fun attachView(passwordRecoverView: PasswordRecoverContract.PasswordRecoverView) {
        this.view = passwordRecoverView
    }

    override fun detachView() {
        view = null
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun detachJob() {
        coroutineContext.cancel()
    }

    override fun sedPasswordRecover(email: String) {
        launch {
            try {
                if(isViewAttached()) {
                    view?.showProgress()
                    passwordRecoverInteractor?.sendPasswordResetEmail(email)
                    view?.hideProgress()
                    view?.navigateToLogin()
                }
            }catch (e:PasswordRecoverException){
                if(isViewAttached()){
                    view?.hideProgress()
                    view?.showError(e.message)
                }
            }
        }
    }

    override fun checkEmailValid(email: String): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

}