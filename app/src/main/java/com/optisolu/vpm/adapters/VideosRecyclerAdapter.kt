package com.optisolu.vpm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.optisolu.vpm.R
import com.optisolu.vpm.models.Data

class VideosRecyclerAdapter(private val context: Context): PagingDataAdapter<Data, VideosRecyclerAdapter.ViewHolder>(DataDifferentiator) {
    companion object {
        private const val TAG = "VideosRecyclerAdapter"
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val email: TextView = view.findViewById(R.id.email)
        val avatar: ImageView = view.findViewById(R.id.avatar)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = getItem(position)?.first_name + " " + getItem(position)?.last_name
        holder.email.text = getItem(position)?.email
        Glide.with(context).load(getItem(position)?.avatar).into(holder.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.videos_recycler_layout_item, parent, false)
        )
    }

    object DataDifferentiator : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}