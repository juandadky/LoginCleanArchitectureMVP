package com.dadky.logincleanarchitecturemvp.presentation.auth.login.presenter

import com.dadky.logincleanarchitecturemvp.domain.interactor.auth.logininteractor.SignInInteractor
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.LoginContract
import com.dadky.logincleanarchitecturemvp.presentation.auth.login.exceptions.FirebaseLoginException
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginPresenter(signInInteractor: SignInInteractor): LoginContract.LoginPresenter,CoroutineScope {

    var view:LoginContract.LoginView? = null
    var signInInteractor:SignInInteractor? = null

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    init {
        this.signInInteractor = signInInteractor
    }

    override fun attachView(view: LoginContract.LoginView) {
        this.view = view
    }

    override fun dettachView() {
        view = null
    }

    override fun detachJob(){
        coroutineContext.cancel()
    }

    override fun isViewAttached(): Boolean {
        return view != null
    }

    override fun signInUserWithEmailAndPassword(email: String, password: String) {

        launch {
            view?.showProgressBar()

            try{
                signInInteractor?.signInWithEmailAndPassword(email,password)

                if(isViewAttached()){
                    view?.hideProgressBar()
                    view?.navigateToMain()
                }
            }catch (e:FirebaseLoginException){
                if(isViewAttached()) {
                    view?.showError(e.message)
                    view?.hideProgressBar()
                }
            }
        }

    }

    override fun checkEmptyFields(email: String, password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }


}