package com.dadky.logincleanarchitecturemvp.presentation.auth.registro

interface RegisterContract {

    interface RegisterView{
        fun navigateToMain()
        fun signUp()
        fun showProgress()
        fun hideProgress()
        fun showError(errormsg:String?)

    }

    interface RegisterPresenter{
        fun attachView(view: RegisterView)
        fun isViewAttached(): Boolean
        fun detachView()
        fun detachJob()
        fun checkEmptyName(fullname: String): Boolean
        fun checkValidEmail(email:String): Boolean
        fun checkEmptyPasswords(pw1:String, pw2:String): Boolean
        fun checkPasswordMatch(pw1: String, pw2: String): Boolean
        fun signUp(fullname: String,email: String,password:String)
    }

}