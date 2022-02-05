package com.example.socialmediaapp.daos

import com.example.socialmediaapp.models.Posts
import com.example.socialmediaapp.models.Users
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PostDao {
    val db = FirebaseFirestore.getInstance()
    val getCollection = db.collection("posts")
    val auth = Firebase.auth

    fun addPost(text :String){
        val currentUserid = auth.currentUser!!.uid
        GlobalScope.launch(Dispatchers.IO) {
            val userDao = UserDao()
            val user = userDao.getUserId(currentUserid).await().toObject(Users::class.java)
            val currentTime = System.currentTimeMillis()
            val post = Posts(text, user!!,currentTime)
            getCollection.document().set(post)
        }
    }

    fun getPostById(postId: String): Task<DocumentSnapshot> {
        return getCollection.document(postId).get()
    }
    fun UpdateLiked(postId : String){
        GlobalScope.launch(Dispatchers.IO) {
            val currentUserid = auth.currentUser!!.uid
            val post = getPostById(postId).await().toObject(Posts::class.java)
            val isLiked = post!!.likedBy.contains(currentUserid)
            if(isLiked){
                post.likedBy.remove(currentUserid)
            }else{
                post.likedBy.add(currentUserid)
            }
            getCollection.document(postId).set(post)
        }
    }
}