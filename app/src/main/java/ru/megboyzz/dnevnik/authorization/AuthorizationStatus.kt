package ru.megboyzz.dnevnik.authorization

enum class AuthorizationStatus {

    LOGGED,
    LOGGING,
    WRONG_CREDENTIALS,
    NO_INTERNET,
    MAINTAIN,
    CREDENTIALS_LOADING,
    NOTHING,
    UNKNOWN_ERROR

}