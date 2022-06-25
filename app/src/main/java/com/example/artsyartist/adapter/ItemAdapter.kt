package com.example.artsyartist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artsyartist.ArtistInfoActivity
import com.example.artsyartist.R
import com.example.artsyartist.model.artistInfo

class ItemAdapter(
    private val context: Context,
    private val dataset: ArrayList<artistInfo>?
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val artistName: TextView = view.findViewById(R.id.item_artist_name)
        val artistNationality: TextView = view.findViewById(R.id.item_artist_nationality)
        val artistBirthYear: TextView = view.findViewById(R.id.item_artist_birth_year)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset!![position]
        holder.artistName.text = item.getArtistNames()
        holder.artistNationality.text = item.getArtistNationality()
        holder.artistBirthYear.text =  item.getArtistBirthDate()
        holder.itemView.setOnClickListener {
            holder.itemView.tag=item.getArtistIds()
            val i = Intent(context, ArtistInfoActivity::class.java)
            i.putExtra("artistId",item.getArtistIds())
            i.putExtra("artistName",item.getArtistNames())
            holder.itemView.context.startActivity(i)
        }
    }

    override fun getItemCount() = dataset!!.size
}