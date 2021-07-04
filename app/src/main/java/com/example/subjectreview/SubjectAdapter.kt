package com.example.subjectreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter

class SubjectAdapter(data: OrderedRealmCollection<Subject>) :
    RealmRecyclerViewAdapter<Subject, SubjectAdapter.ViewHolder>(data, true){

    private var listener: ((Long?) -> Unit)? = null

    fun setOnItemClickListener(listener:(Long?) -> Unit){
        this.listener = listener
    }

    init {
        setHasStableIds(true)
    }

    class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell){
        val title: TextView = cell.findViewById(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectAdapter.ViewHolder, position: Int) {
        val subject: Subject? = getItem(position)
        holder.title.text = subject?.title
        holder.itemView.setOnClickListener{
            listener?.invoke(subject?.id)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position)?.id?:0
    }
}