package com.example.artsyartist

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.example.artsyartist.adapter.ItemAdapter
import com.example.artsyartist.databinding.ActivityHomeFavouriteBinding
import com.example.artsyartist.model.FavouritesList
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeFavouriteBinding
    private lateinit var favouritesPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.poweredByArtsy.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.artsy.net/")
            startActivity(openURL)
        }
        supportActionBar?.hide()
        binding.progressBar.visibility=View.VISIBLE
        binding.linearLayout.visibility=View.INVISIBLE
        binding.poweredByArtsy.visibility=View.INVISIBLE
        Handler().postDelayed({
            getData()
            binding.progressBar.visibility=View.INVISIBLE
            supportActionBar?.show()
            binding.linearLayout.visibility=View.VISIBLE
            binding.poweredByArtsy.visibility=View.VISIBLE
        },1000)


    }

    private fun getData(){
        favouritesPref= applicationContext.getSharedPreferences("favourites",0)
        val favouritesData=favouritesPref.getString("favouritesList",null).toString()
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("d MMMM yyyy")
        val date = simpleDateFormat.format(calendar.time)
        binding.todayDate.text = date
        var favouritesDataSerialized=FavouritesList()
        if(favouritesData!=null && favouritesData!="null"){
            favouritesDataSerialized= Gson().fromJson(favouritesData, FavouritesList::class.java)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_favourite)
        Log.d("MyData",favouritesDataSerialized.getArtistInfoList().toString())
        recyclerView.adapter = ItemAdapter(this, favouritesDataSerialized.getArtistInfoList())
        recyclerView.setHasFixedSize(true)

    }

    override fun onResume() {
        getData()
        super.onResume()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val searchItem=menu?.findItem(R.id.app_bar_search)
        val searchView=searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                val i = Intent(this@HomeActivity, SearchResultActivity::class.java)
                i.putExtra("searchQuery",query)
                startActivity(i)
                return false
            }
        })
        return true
    }



}