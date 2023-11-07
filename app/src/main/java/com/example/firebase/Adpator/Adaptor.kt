package com.example.firebase.Adpator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebase.R
import com.example.firebase.model.Model

class Adaptor(val array: ArrayList<Model>): RecyclerView.Adapter<Adaptor.ViewHolder>() {

    var setOnItemClickListener:((Model)->Unit)?=null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var name:TextView=itemView.findViewById(R.id.textView)
        var email:TextView=itemView.findViewById(R.id.textView2)
        var query:TextView=itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.query_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text= array[position].name
        holder.email.text= array[position].email
        holder.query.text= array[position].query

        holder.itemView.setOnClickListener {
            setOnItemClickListener?.invoke(array[position])
        }
    }

    override fun getItemCount(): Int {
        return array.size
    }
}