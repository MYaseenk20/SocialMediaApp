package com.example.socialmediaapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.models.Posts
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.posts.view.*

class PostAdaptor(options: FirestoreRecyclerOptions<Posts>,val listener : onclickedBtn) :
    FirestoreRecyclerAdapter<Posts, PostViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.posts,parent,false)
        val viewHolder = PostViewHolder(view)
        viewHolder.likeBtn.setOnClickListener {
            listener.onLikeBtn(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int, model: Posts) {
        holder.username.text = model.createdBy.displayName
        holder.posttitle.text = model.text
        Glide.with(holder.userimage.context).load(model.createdBy.imageUrl).into(holder.userimage)
        holder.likecount.text = model.likedBy.size.toString()
        holder.createdat.text = utils.getTimeAgo(model.createdAt)
        val auth = Firebase.auth
        val currentUserid = auth.currentUser!!.uid
        val isLiked = model!!.likedBy.contains(currentUserid)
        if(isLiked){
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context,R.drawable.ic_liked))
        }else{
            holder.likeBtn.setImageDrawable(ContextCompat.getDrawable(holder.likeBtn.context,R.drawable.ic_unlike))
        }




    }
}
interface onclickedBtn{
    fun onLikeBtn(postId:String)
}
class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val userimage = itemView.userImage
    val username = itemView.userName
    val createdat = itemView.createdAt
    val posttitle = itemView.postTitle
    val likeBtn = itemView.likeButton
    val likecount = itemView.likeCount

}