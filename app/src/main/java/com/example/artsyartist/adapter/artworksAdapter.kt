package com.example.artsyartist.adapter


import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.artsyartist.R
import com.example.artsyartist.model.artworks
import com.example.artsyartist.model.genes
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject


class artworksAdapter(
    private val context: Context,
    private val dataset: ArrayList<artworks>?
) : RecyclerView.Adapter<artworksAdapter.ItemViewHolder>() {
    private val artsyUrl = "https://artsy-artist-server.wl.r.appspot.com/get/genes/artwork/"

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textView: TextView = view.findViewById(R.id.artworkName)
        val imageView: ImageView = view.findViewById(R.id.artworkImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.artist_artwork_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset?.get(position)
        holder.textView.text = item?.getArtworkNames()
        val imageURl = item?.getImageURLs()
        Picasso.get().load(imageURl).error(R.drawable.artsy_logo).into(holder.imageView)
        holder.imageView.setOnClickListener {
            getGenesData(item?.getArtworkIds().toString())
        }

    }

    private fun getGenesData(itemId: String) {

        val artworksUrl=artsyUrl+itemId
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, artworksUrl,
            { response ->
                try {
                    val dataArray =
                        JSONObject(response).getJSONObject("_embedded").getJSONArray("genes")
                    val dataObjectLength=dataArray.length()
                    if(dataObjectLength>0){
                        val dataObj = dataArray.getJSONObject(0)
                        val genesFirstData = genes()
                        genesFirstData.setArtworkGenesId(dataObj.getString("id"))
                        genesFirstData.setGenesName(dataObj.getString("name"))
                        genesFirstData.setArtworkIds(itemId)
                        genesFirstData.setGenesDescription(dataObj.getString("description"))
                        genesFirstData.setImageURLs(dataObj.getJSONObject("_links").getJSONObject("thumbnail").getString("href"))
                        loadDialog(genesFirstData)
                    }else{
                        loadNoDataDialog()
                    }

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { err -> Log.println(Log.ERROR, "Error", err.toString()) })

        requestQueue.add(stringRequest)
    }

    private fun loadDialog(dialogData:genes){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.artwork_genes_layout)
        val genesLayout=dialog.findViewById<LinearLayout>(R.id.genesLayout)
        val notFoundGenes=dialog.findViewById<LinearLayout>(R.id.layout_no_category)
        notFoundGenes.visibility=View.GONE
        genesLayout.visibility=View.VISIBLE
        val textHeading=dialog.findViewById<TextView>(R.id.genesHeadingContainer)
        val textDescription=dialog.findViewById<TextView>(R.id.genesDescriptionContainer)
        val image= dialog.findViewById<ImageView>(R.id.genesImage)
        textHeading.text = dialogData.getGenesName()
        textDescription.text = dialogData.getGenesDescription()
        Picasso.get().load(dialogData.getImageURLs()).error(R.drawable.artsy_logo).into(image)
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

    }

    private fun loadNoDataDialog(){
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.artwork_genes_layout)
        val genesLayout=dialog.findViewById<LinearLayout>(R.id.genesLayout)
        val notFoundGenes=dialog.findViewById<LinearLayout>(R.id.layout_no_category)
        genesLayout.visibility=View.GONE
        notFoundGenes.visibility=View.VISIBLE
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

    }

    override fun getItemCount() = dataset!!.size
}
