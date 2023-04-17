package ru.megboyzz.dnevnik.viewmodel.model

data class UserProfileModel(
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val groupName: String,
    val term: Int,
    val isTerm: Boolean,
    val avatarUrl: String
){
    override fun toString() = "$lastName ${firstName.first()}. ${middleName.first()}."
}
