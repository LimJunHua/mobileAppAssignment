package com.example.mobileassignment
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val userMainPost: ArrayList<post>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userMainPost[position]

        holder.username.text = currentitem.username
        holder.Content.text = currentitem.content
       
    }

    override fun getItemCount(): Int {
       return userMainPost.size
    }

    class MyViewHolder(itemView :View) :RecyclerView.ViewHolder(itemView){
        val username :TextView = itemView.findViewById(R.id.tvPostUsername)
        val Content : TextView = itemView.findViewById(R.id.tvContent)
    }
}