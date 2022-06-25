package com.example.artsyartist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.artsyartist.adapter.SearchAdapter
import com.example.artsyartist.databinding.ActivitySearchResultBinding
import com.example.artsyartist.model.searchResult
import org.json.JSONException
import org.json.JSONObject


class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var dataModelArrayList: ArrayList<searchResult>
    private var artsyUrl = "https://artsy-artist-server.wl.r.appspot.com/artist/search?query="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataModelArrayList = ArrayList()
        binding.progressBarLoading.visibility = View.INVISIBLE
        binding.itemLoading.visibility = View.INVISIBLE
        val searchQuery = intent.getStringExtra("searchQuery")
        artsyUrl += searchQuery
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = searchQuery!!.uppercase()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getResults()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun getResults() {
        binding.progressBarLoading.visibility = View.VISIBLE
        binding.itemLoading.visibility = View.VISIBLE
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, artsyUrl,
            { response ->
                try {
                    val dataArray =
                        JSONObject(response).getJSONObject("_embedded").getJSONArray("results")
                    for (i in 0 until dataArray.length()) {

                        val dataObj = dataArray.getJSONObject(i)
                        val getLinks = dataObj.getJSONObject("_links")

                        if (dataObj.getString("og_type").toString() == "artist") {
                            val playerModel = searchResult()
                            playerModel.setTypes(dataObj.getString("og_type"))
                            playerModel.setArtistNames(dataObj.getString("title"))
                            playerModel.setImageURLs(
                                getLinks.getJSONObject("thumbnail")
                                    .getString("href"),
                            )
                            playerModel.setArtistIds(
                                getLinks.getJSONObject("self").getString("href")
                            )

                            dataModelArrayList.add(playerModel)

                        }
                    }

                    val arrayLength=dataModelArrayList.size
                    if(arrayLength>0){
                        binding.itemNoSearchResults.visibility=View.INVISIBLE
                        binding.recyclerViewSearch.visibility=View.VISIBLE
                        setupRecycler()
                    }else{
                        binding.recyclerViewSearch.visibility=View.INVISIBLE
                        binding.searchFrameLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.grey))
                        binding.itemNoSearchResults.visibility=View.VISIBLE
                    }
                    binding.progressBarLoading.visibility = View.INVISIBLE
                    binding.itemLoading.visibility = View.INVISIBLE

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { err -> Log.println(Log.ERROR, "Error", err.toString()) })

        requestQueue.add(stringRequest)

    }

    private fun setupRecycler() {
        binding.recyclerViewSearch.adapter = SearchAdapter(this, dataModelArrayList)
        binding.recyclerViewSearch.setHasFixedSize(true)
    }

}