package com.example.socialmediaapp.daos

import com.example.socialmediaapp.models.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.model.Document
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    val db = FirebaseFirestore.getInstance()
    val userCollection = db.collection("users")
    fun addUser(users: Users?){
        users?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userCollection.document(users.uid).set(it)
            }
        }
    }


    fun getUserId(id:String) : Task<DocumentSnapshot>{
        return userCollection.document(id).get()
    }
}