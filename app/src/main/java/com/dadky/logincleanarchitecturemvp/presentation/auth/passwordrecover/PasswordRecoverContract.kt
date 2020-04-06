package com.dadky.logincleanarchitecturemvp.presentation.auth.passwordrecover

interface PasswordRecoverContract {

    interface PasswordRecoverView{
        fun showError(errormsg:String?)
        fun showProgress()
        fun hideProgress()
        fun recoverPassword()
        fun navigateToLogin()
    }

    interface PasswordRecoverPresenter{
        fun attachView(passwordRecoverView:PasswordRecoverView)
        fun detachView()
        fun isViewAttached():Boolean
        fun detachJob()
        fun sedPasswordRecover(email:String)
        fun checkEmailValid(email:String):Boolean
    }
}