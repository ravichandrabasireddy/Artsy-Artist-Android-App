package com.example.artsyartist.model

class genes {
    private var artworkId: String? = null
    private var artworkGenesId: String? = null
    private var genesName: String? = null
    private var genesDescription: String? = null
    private var imgURL: String? = null

    fun getGenesName(): String {
        return genesName.toString()
    }

    fun setGenesName(name: String) {
        this.genesName = name
    }

    fun getArtworkIds(): String {
        return artworkId.toString()
    }

    fun setArtworkIds(id: String) {
        this.artworkId = id
    }

    fun setArtworkGenesId(id: String) {
        this.artworkGenesId = id
    }

    fun getArtworkGenesId(): String {
        return this.artworkGenesId.toString()
    }

    fun setGenesDescription(description: String) {
        this.genesDescription = description
    }

    fun getGenesDescription(): String {
        return this.genesDescription.toString()
    }

    fun getImageURLs(): String {
        return imgURL.toString()
    }

    fun setImageURLs(imgURL: String) {
        this.imgURL = imgURL
    }
}