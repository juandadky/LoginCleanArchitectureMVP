package com.dadky.logincleanarchitecturemvp.domain.interactor.auth.registerinteractor

import com.dadky.logincleanarchitecturemvp.presentation.auth.login.exceptions.FirebaseLoginException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class SignUpInteractorImpl: SignUpInteractor {

    override suspend fun signUp(fullname: String, email: String, password: String): Unit = suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener{ itSignUp ->
            if(itSignUp.isSuccessful){

                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(fullname)
                    .build()

                FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener {
                    if(it.isSuccessful){
                        continuation.resume(Unit)
                    }else{
                        continuation.resumeWithException(FirebaseLoginException(it.exception.toString()))
                    }
                }

                continuation.resume(Unit)
            }else{
                continuation.cancel(FirebaseLoginException(itSignUp.exception.toString()))
            }
        }
    }
}