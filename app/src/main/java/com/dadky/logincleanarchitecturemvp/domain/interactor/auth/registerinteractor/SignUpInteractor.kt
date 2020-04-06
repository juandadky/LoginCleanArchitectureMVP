package com.dadky.logincleanarchitecturemvp.domain.interactor.auth.registerinteractor

interface SignUpInteractor {

    suspend fun signUp(fullname:String,email:String,password:String)

}