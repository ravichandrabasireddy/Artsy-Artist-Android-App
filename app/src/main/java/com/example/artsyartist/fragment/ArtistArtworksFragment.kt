package com.example.artsyartist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.artsyartist.adapter.artworksAdapter
import com.example.artsyartist.databinding.FragmentArtistArtworksBinding
import com.example.artsyartist.model.artworks


class ArtistArtworksFragment(private val artworksData: ArrayList<artworks>?) : Fragment() {
    private var _binding: FragmentArtistArtworksBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistArtworksBinding.inflate(inflater, container, false)
        val view = binding.root
        if(artworksData!=null){
                if(artworksData.size>0){
                    binding.recyclerViewArtworks.visibility=View.VISIBLE
                    binding.itemNoArtworks.visibility=View.INVISIBLE
                    setupRecycler()

                }else{
                    binding.recyclerViewArtworks.visibility=View.INVISIBLE
                    binding.itemNoArtworks.visibility=View.VISIBLE
                }

        }
        return view
    }


    private fun setupRecycler() {
        binding.recyclerViewArtworks.adapter = artworksAdapter(context!!,artworksData)
        binding.recyclerViewArtworks.setHasFixedSize(true)
    }
}