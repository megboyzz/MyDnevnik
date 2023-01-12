package ru.megboyzz.dnevnik.entities.response

data class AuthorizationResponse(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val scope: String,
    val user: Long
)
/*
* 	"accessToken": "7wmx4EIXWQNcQtqmKahZ9kwpy6pKCRU8",
	"expiresIn": 2591999,
	"expiresIn_str": "2591999",
	"refreshToken": "Iu8rTLAFt8d1JuObaUwsPbqXkdZ3i3ct",
	"scope": "commoninfo,friendsandrelatives,educationalinfo,personaldata",
	"user": 1000006509021,
	"user_str": "1000006509021"*/