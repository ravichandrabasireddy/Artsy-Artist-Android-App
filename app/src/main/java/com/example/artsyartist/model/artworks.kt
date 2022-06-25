package com.example.artsyartist.model

class artworks {
    private var artistName: String? = null
    private var artworkId: String? = null
    private var artworkName: String? = null
    private var artistId: String? = null
    private var imgURL: String? = null

    fun getArtistNames(): String {
        return artistName.toString()
    }

    fun setArtistNames(name: String) {
        this.artistName = name
    }

    fun getArtworkIds(): String {
        return artworkId.toString()
    }

    fun setArtworkIds(id: String) {
        this.artworkId = id
    }

    fun setArtistIds(id: String) {
        this.artistId = id
    }

    fun getArtistId(): String {
        return this.artistId.toString()
    }

    fun setArtworkNames(artwork: String) {
        this.artworkName = artwork
    }

    fun getArtworkNames(): String {
        return this.artworkName.toString()
    }

    fun getImageURLs(): String {
        return imgURL.toString()
    }

    fun setImageURLs(imgURL: String) {
        this.imgURL = imgURL
    }
}