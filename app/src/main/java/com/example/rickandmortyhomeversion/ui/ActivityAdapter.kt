package com.example.rickandmortyhomeversion.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyhomeversion.R
import com.example.rickandmortyhomeversion.models.ResultResponse

class MainAdapter(
    val onClick: (ResultResponse) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val characters = mutableListOf<ResultResponse>()


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.characterImageView)
        private val textView1: TextView = itemView.findViewById(R.id.idTextView)
        private val textView2: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(resultResponse: ResultResponse) {
            Glide.with(itemView.context).load(resultResponse.image).into(imageView)
            textView1.text = resultResponse.name
            textView2.text = resultResponse.id.toString()

            itemView.setOnClickListener { onClick.invoke(resultResponse) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MainViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val listItem = characters[position]
        holder.bind(listItem)
    }

    override fun getItemCount() = characters.size

    fun setData(list: List<ResultResponse>) {
        characters.clear()
        characters.addAll(list)

        notifyDataSetChanged()
    }


}