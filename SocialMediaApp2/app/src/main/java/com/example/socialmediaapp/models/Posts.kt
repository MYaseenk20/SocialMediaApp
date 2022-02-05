package com.example.socialmediaapp.models

data class Posts (
    val text : String ="",
    val createdBy : Users = Users(),
    val createdAt : Long = 0L,
    val likedBy : ArrayList<String> = ArrayList()
)