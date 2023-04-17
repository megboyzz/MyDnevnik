package ru.megboyzz.dnevnik.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.megboyzz.dnevnik.App
import ru.megboyzz.dnevnik.entities.context.GroupType
import ru.megboyzz.dnevnik.entities.context.SchoolType
import ru.megboyzz.dnevnik.entities.db.GlobalUserContext
import ru.megboyzz.dnevnik.viewmodel.model.UserProfileModel
import java.lang.IllegalArgumentException

class ProfileViewModel(application: App) : AndroidViewModel(application) {

    val profile = MutableStateFlow<UserProfileModel?>(null)

    val userContextDao = application.database.globalUserContextDao()

    init{
        viewModelScope.launch(Dispatchers.IO) {
            val globalUserContext = userContextDao.getGlobalUserContext()
            val convertedProfile = convertGlobalUserContextToProfile(globalUserContext)
            profile.emit(convertedProfile)
        }
    }

    private fun convertGlobalUserContextToProfile(
        globalUserContext: GlobalUserContext
    ): UserProfileModel{
        val info = globalUserContext.apiUserContext.info

        val personContext = globalUserContext.apiUserContext.contextPersons.find {
            it.school.type == SchoolType.Regular
        } ?: globalUserContext.apiUserContext.contextPersons[0]

        val term = personContext.reportingPeriodGroup.periods.find {
            it.isCurrent
        } ?: personContext.reportingPeriodGroup.periods[0]

        return UserProfileModel(
            firstName = info.firstName,
            middleName = info.middleName,
            lastName = info.lastName,
            groupName = personContext.group.name,
            term = term.number + 1,
            isTerm = term.type == GroupType.Quarter,
            avatarUrl = info.avatarUrl
        )
    }

}

class ProfileViewModelFactory(private val application: App) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(application) as T
        throw  IllegalArgumentException("Unknown ViewModel Class")
    }

}