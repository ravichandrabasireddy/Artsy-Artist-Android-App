package com.example.artsyartist.model

class searchResult {
    private var artistName: String? = null
    private var artistId: String? = null
    private var imgURL: String? = null
    private var type:String?=null

    fun getArtistNames(): String {
        return artistName.toString()
    }

    fun setArtistNames(name: String) {
        this.artistName = name
    }

    fun getArtistIds(): String {
        return artistId.toString()
    }

    fun setArtistIds(url: String) {
        this.artistId= url.split('/').last()
    }


    fun getTypes(): String {
        return this.type.toString()
    }

    fun setTypes(type: String) {
        this.type = type
    }

    fun getImageURLs(): String {
        return imgURL.toString()
    }

    fun setImageURLs(imgURL: String) {
        this.imgURL = imgURL
    }
}