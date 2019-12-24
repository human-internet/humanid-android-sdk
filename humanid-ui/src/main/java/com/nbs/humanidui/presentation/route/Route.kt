package com.nbs.humanidui.presentation.route

import com.humanid.auth.HumanIDAuth
import com.nbs.nucleo.utils.showToast

class Route{
    fun checkIsLoggedIn(onLoggedIn: ()-> Unit, onNotLoggedIn: ()->Unit, onCheckInLoading: ()->Unit){
        if (HumanIDAuth.getInstance().currentUser != null){
            onCheckInLoading.invoke()
            HumanIDAuth.getInstance().currentUser?.let {
                HumanIDAuth.getInstance().loginCheck(it.userHash)
                        .addOnCompleteListener {task ->
                            if (task.isSuccessful){
                                onLoggedIn.invoke()
                            }else{
                                onNotLoggedIn.invoke()
                            }
                        }.addOnFailureListener {exception ->
                            onNotLoggedIn.invoke()
                            showToast(exception.message.toString())
                        }
            }

        }else{
            onNotLoggedIn.invoke()
        }
    }
}