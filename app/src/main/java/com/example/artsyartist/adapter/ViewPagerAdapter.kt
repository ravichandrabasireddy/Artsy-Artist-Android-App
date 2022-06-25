package com.example.artsyartist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.artsyartist.fragment.ArtistArtworksFragment
import com.example.artsyartist.fragment.ArtistInfoFragment
import com.example.artsyartist.model.artistInfo
import com.example.artsyartist.model.artworks


class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle,private val artistInfoData: artistInfo?,private val artworksData:ArrayList<artworks>?) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ArtistInfoFragment(artistInfoData)
            }
            1 -> {
                ArtistArtworksFragment(artworksData)
            }
            else -> ArtistInfoFragment(artistInfoData)
        }
    }

}