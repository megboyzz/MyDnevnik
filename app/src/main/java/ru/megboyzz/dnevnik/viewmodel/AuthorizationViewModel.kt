package ru.megboyzz.dnevnik.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.IllegalArgumentException

//Если есть попадание в этот вьюмодел то нам нужна авторизация
class AuthorizationViewModel(application: Application) : AndroidViewModel(application) {

    val login = MutableStateFlow("")
    val password = MutableStateFlow("")
    val isLogged = MutableStateFlow(false)

    init {



    }


    fun login(){



    }

}


class AuthorizationViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorizationViewModel::class.java))
            return AuthorizationViewModel(application) as T
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}