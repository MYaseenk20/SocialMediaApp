package com.example.socialmediaapp

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapp.daos.PostDao
import com.example.socialmediaapp.models.Posts
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), onclickedBtn {
    lateinit var dao: PostDao
    lateinit var madaptor : PostAdaptor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tab.setOnClickListener {
            val intent = Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_fav -> {
//            Firebase.auth.signOut()
            FirebaseAuth.getInstance().signOut()
//            Auth.GoogleSignInApi.signOut() ;
            val intent = Intent(this,Signin::class.java)
            startActivity(intent)
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView() {
        dao = PostDao()
        val query = dao.getCollection.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOption = FirestoreRecyclerOptions.Builder<Posts>().setQuery(query,Posts::class.java).build()
        madaptor = PostAdaptor(recyclerViewOption,this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = madaptor
    }

    override fun onStart() {
        super.onStart()
        madaptor.startListening()
    }

    override fun onStop() {
        super.onStop()
        madaptor.stopListening()
    }

    override fun onLikeBtn(postId: String) {
        dao.UpdateLiked(postId)
    }
}