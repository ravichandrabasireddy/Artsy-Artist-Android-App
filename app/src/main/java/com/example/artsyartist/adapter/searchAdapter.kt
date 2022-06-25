package com.example.artsyartist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artsyartist.ArtistInfoActivity
import com.example.artsyartist.R
import com.example.artsyartist.model.searchResult
import com.squareup.picasso.Picasso


class SearchAdapter(
    private val context: Context,
    private val dataset: ArrayList<searchResult>
) : RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.artistName)
        val imageView: ImageView = view.findViewById(R.id.artistImage)
        val tag=view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = item.getArtistNames()
        val imageURl=item.getImageURLs()
        Picasso.get().load(imageURl).error(R.drawable.artsy_logo).into(holder.imageView)
        holder.imageView.setOnClickListener {
            holder.tag.tag = item.getArtistIds()
            val i = Intent(context, ArtistInfoActivity::class.java)
            i.putExtra("artistId",item.getArtistIds())
            i.putExtra("artistName",item.getArtistNames())
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount() = dataset.size
}