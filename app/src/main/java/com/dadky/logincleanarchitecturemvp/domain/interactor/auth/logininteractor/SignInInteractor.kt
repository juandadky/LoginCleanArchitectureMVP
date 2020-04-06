package com.dadky.logincleanarchitecturemvp.domain.interactor.auth.logininteractor

interface SignInInteractor {

    suspend fun signInWithEmailAndPassword(email:String,password:String)

}