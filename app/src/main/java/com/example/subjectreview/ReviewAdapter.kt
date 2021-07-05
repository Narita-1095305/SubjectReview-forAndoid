package com.example.subjectreview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import java.util.*

class ReviewAdapter(data: OrderedRealmCollection<Review>) :
    RealmRecyclerViewAdapter<Review, ReviewAdapter.ViewHolder>(data, true) {
    private var listener: ((Long?) -> Unit)? = null

    fun setOnItemClickListener(listener:(Long?) -> Unit){
        this.listener = listener
    }

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell){
        val point: TextView = cell.findViewById(android.R.id.text1)
        val detail: TextView = cell.findViewById(android.R.id.text2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ReviewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val review: Review? = getItem(position)
        holder.point.text = "評価得点:"+review?.point.toString()
        holder.detail.text = "詳細:"+review?.detail
        holder.itemView.setBackgroundColor(if(position % 2 == 0) Color.LTGRAY else Color.WHITE)
        holder.itemView.setOnClickListener{
            listener?.invoke(review?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id?:0
    }
}