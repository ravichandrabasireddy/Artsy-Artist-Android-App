package com.example.artsyartist.model

import java.io.Serializable

class FavouritesList :Serializable{
    private var artistInfoList: ArrayList<artistInfo> = ArrayList()

    fun getArtistInfoList(): ArrayList<artistInfo> {
        return artistInfoList
    }

    fun addArtistInfo(artistInfo: artistInfo) {
        this.artistInfoList.add(artistInfo)
    }

    fun removeArtistInfo(artistInfo: artistInfo) {
       this.artistInfoList.removeIf {artistdata->artistdata.getArtistIds()==artistInfo.getArtistIds() }
    }

    fun getSize():Int{
        return artistInfoList.size
    }
}