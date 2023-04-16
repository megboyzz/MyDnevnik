package ru.megboyzz.dnevnik.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.App
import ru.megboyzz.dnevnik.authorization.AuthorizationManager
import ru.megboyzz.dnevnik.authorization.AuthorizationStatus
import java.lang.IllegalArgumentException

//Если есть попадание в этот вьюмодел то нам нужна авторизация
class AuthorizationViewModel(application: App) : AndroidViewModel(application) {

    val login = MutableStateFlow("")
    val password = MutableStateFlow("")
    val rememberMe = MutableStateFlow(false)

    val loginStatus = MutableStateFlow(AuthorizationStatus.NOTHING)
    private val authManager = AuthorizationManager(application.database.credentialsDao())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            loginStatus.emit(authManager.checkCredentials())
        }
    }

    fun login(){

        viewModelScope.launch(Dispatchers.IO) {

            loginStatus.emit(AuthorizationStatus.LOGGING)
            loginStatus.emit(authManager.login(login.value, password.value, rememberMe.value))

        }

    }

}


class AuthorizationViewModelFactory(private val application: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthorizationViewModel::class.java))
            return AuthorizationViewModel(application) as T
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}