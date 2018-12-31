package com.android.academy.fprojectnew


import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.android.academy.fprojectnew.datamodel.Scene_model

class ListAdapter(val contentList: ArrayList<Scene_model>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.time.text = contentList[position].time
        holder.content.text = contentList[position].content
        holder.background.cardBackgroundColor
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time_field)
        val content: TextView = itemView.findViewById(R.id.text_field)
        val background: CardView = itemView.findViewById(R.id.card)
    }

}