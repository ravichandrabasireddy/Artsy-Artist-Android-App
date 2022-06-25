package com.example.artsyartist


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.artsyartist.adapter.ViewPagerAdapter
import com.example.artsyartist.databinding.ActivityArtistInfoBinding
import com.example.artsyartist.model.FavouritesList
import com.example.artsyartist.model.artistInfo
import com.example.artsyartist.model.artworks
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


class ArtistInfoActivity : AppCompatActivity() {
    private var artistName: String = ""
    private var artistId: String = ""
    private lateinit var binding: ActivityArtistInfoBinding
    private var artsyUrl = "https://artsy-artist-server.wl.r.appspot.com/"
    private val artistInformation = artistInfo()
    private lateinit var dataModelArrayList: ArrayList<artworks>
    private var requestQueue: RequestQueue? = null
    private var progressBar: ProgressBar? = null
    private var isDataLoaded = false
    private var tabTitle = arrayOf("Details", "Artwork")
    private var mFragmentManager: FragmentManager? = null
    private var mFragmentTransaction: FragmentTransaction? = null
    private var tabIcons = arrayOf(R.drawable.ic_info, R.drawable.ic_art)
    private lateinit var favoritesPref: SharedPreferences
    private lateinit var favouritesDataFromSharedPreferenceString:String
    private lateinit var favouritesDataFromSharedPreference:FavouritesList
    private lateinit var gson:Gson
    lateinit var view:View
    private lateinit var myMenu:Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view = binding.root
        setContentView(view)
        artistName = intent.getStringExtra("artistName").toString()
        artistId = intent.getStringExtra("artistId").toString()
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = artistName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        requestQueue = Volley.newRequestQueue(this)
        progressBar = findViewById(R.id.progress_bar_info)

        mFragmentManager = supportFragmentManager
        mFragmentTransaction = mFragmentManager?.beginTransaction()
        favoritesPref = applicationContext.getSharedPreferences("favourites", 0)
        favouritesDataFromSharedPreferenceString = favoritesPref.getString("favouritesList", null).toString()
        gson=Gson()
        if(favouritesDataFromSharedPreferenceString!=null && favouritesDataFromSharedPreferenceString!="null" ){
            favouritesDataFromSharedPreference = gson.fromJson(favouritesDataFromSharedPreferenceString, FavouritesList::class.java)
        }else{
            favouritesDataFromSharedPreference= FavouritesList()
        }

        loadView(null, null)
    }

    private fun getArtistInfo() {
        progressBar?.visibility = View.VISIBLE
        binding.itemLoading.visibility = View.VISIBLE
        isDataLoaded = false
        val artistInfoUrl = artsyUrl + "get/artist/" + artistId
        val stringRequest = StringRequest(
            Request.Method.GET, artistInfoUrl,
            { response ->
                try {
                    val artistObject = JSONObject(response)
                    artistInformation.setArtistIds(artistObject.getString("id"))
                    artistInformation.setArtistNames(artistObject.getString("name"))
                    artistInformation.setArtistNationality(artistObject.getString("nationality"))
                    artistInformation.setArtistBirthDate(artistObject.getString("birthday"))
                    artistInformation.setArtistDeathDate(artistObject.getString("deathday"))
                    artistInformation.setArtistBiography(artistObject.getString("biography"))
                    updateFavourites()
                    getArtistArtworks()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { err -> Log.println(Log.ERROR, "Error", err.toString()) })

        requestQueue?.add(stringRequest)
    }

    private fun getArtistArtworks() {
        val artistInfoUrl = artsyUrl + "get/artworks/artist/" + artistId
        val stringRequest = StringRequest(
            Request.Method.GET, artistInfoUrl,
            { response ->
                try {
                    val dataArray =
                        JSONObject(response).getJSONObject("_embedded").getJSONArray("artworks")
                    dataModelArrayList = ArrayList()
                    for (i in 0 until dataArray.length()) {
                        val dataObj = dataArray.getJSONObject(i)
                        val getLinks = dataObj.getJSONObject("_links")
                        val artworkModel = artworks()
                        artworkModel.setArtworkNames(dataObj.getString("title"))
                        artworkModel.setArtworkIds(dataObj.getString("id"))
                        artworkModel.setArtistNames(artistName)
                        artworkModel.setArtistIds(artistId)
                        artworkModel.setImageURLs(
                            getLinks.getJSONObject("thumbnail")
                                .getString("href"),
                        )
                        dataModelArrayList.add(artworkModel)
                    }
                    isDataLoaded = true
                    setTabView(artistInformation, dataModelArrayList)
                    progressBar?.visibility = View.INVISIBLE
                    binding.itemLoading.visibility = View.INVISIBLE
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { err -> Log.println(Log.ERROR, "Error", err.toString()) })

        requestQueue?.add(stringRequest)
    }

    private fun updateFavourites(){
        val menuItem = myMenu.findItem(R.id.app_bar_favourite)
        Log.d("mu",favouritesDataFromSharedPreference.getArtistInfoList().toString())
        if(favouritesDataFromSharedPreference!=null && favouritesDataFromSharedPreference.getSize()>=0){
            val artistFound = favouritesDataFromSharedPreference.getArtistInfoList().find { artist->artist.getArtistIds()==artistInformation.getArtistIds() }
            if(artistFound?.getArtistIds()==artistInformation.getArtistIds()){
                Log.d("MyDate",artistFound.toString())
                menuItem.setIcon(R.drawable.ic_favourite_check)
                menuItem.isChecked = true
            }else{
                Log.d("MyDate",artistFound.toString())
                menuItem.setIcon(R.drawable.ic_favourite)
                menuItem.isChecked = false
            }
        }
    }


    private fun setTabView(artistInfo: artistInfo?, artworks: ArrayList<artworks>?) {
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, artistInfo, artworks)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.artistTabs, binding.viewPager) { tab, position ->
            tab.setIcon(tabIcons[position])
            tab.text = tabTitle[position]
        }.attach()
    }

    private fun loadView(artistInfo: artistInfo?, artworks: ArrayList<artworks>?) {
        setTabView(artistInfo, artworks)
        getArtistInfo()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favourites, menu)
        myMenu=menu!!
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.app_bar_favourite -> {
                if (isDataLoaded) {
                    val preferenceEdit=favoritesPref.edit()
                    val artistFound = favouritesDataFromSharedPreference.getArtistInfoList()
                        .find { artist->artist.getArtistIds()==artistInformation.getArtistIds() }
                    if (artistFound?.getArtistIds()==artistInformation.getArtistIds()) {
                        favouritesDataFromSharedPreference.removeArtistInfo(artistInformation)
                        item.setIcon(R.drawable.ic_favourite)
                        Toast.makeText(
                            this,
                            "$artistName is removed from favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        item.isChecked = false
                    }else{
                        favouritesDataFromSharedPreference.addArtistInfo(artistInformation)
                        item.setIcon(R.drawable.ic_favourite_check)
                        Toast.makeText(
                            this,
                            "$artistName is added to favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                        item.isChecked = true
                    }
                    preferenceEdit.putString("favouritesList", gson.toJson(favouritesDataFromSharedPreference))
                    preferenceEdit.commit()

                } else {
                    Toast.makeText(this, "$artistName is not loaded", Toast.LENGTH_SHORT)
                        .show()
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}