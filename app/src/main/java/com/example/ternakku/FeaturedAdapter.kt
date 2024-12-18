package com.example.ternakku

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeaturedAdapter(private val featuredItems: List<String>) : RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_featured, parent, false)
        return FeaturedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        // Set featured item data (can be image or text)
        holder.featuredText.text = featuredItems[position]
    }

    override fun getItemCount(): Int = featuredItems.size

    inner class FeaturedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val featuredText: TextView = itemView.findViewById(R.id.featuredText)
    }
}