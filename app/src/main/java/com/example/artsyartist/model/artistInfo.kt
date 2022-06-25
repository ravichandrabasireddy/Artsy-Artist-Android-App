package com.example.artsyartist.model

import java.io.Serializable

class artistInfo: Serializable {

    private var artistName: String? = null
    private var artistId: String? = null
    private var artistNationality: String? = null
    private var artistBirthDate: String? = null
    private var artistDeathDate: String? = null
    private var artistBiography: String? = null

    fun getArtistNames(): String {
        return artistName.toString()
    }

    fun setArtistNames(name: String) {
        this.artistName = name
    }

    fun getArtistNationality(): String {
        return artistNationality.toString()
    }

    fun setArtistNationality(name: String) {
        this.artistNationality = name
    }

    fun getArtistIds(): String {
        return artistId.toString()
    }

    fun setArtistIds(id: String) {
        this.artistId = id
    }

    fun getArtistBirthDate(): String {
        return artistBirthDate.toString()
    }

    fun setArtistBirthDate(birthDay: String) {
        this.artistBirthDate = birthDay
    }

    fun getArtistDeathDate(): String {
        return artistDeathDate.toString()
    }

    fun setArtistDeathDate(deathDay: String) {
        this.artistDeathDate = deathDay
    }

    fun getArtistBiography(): String {
        return artistBiography.toString()
    }

    fun setArtistBiography(biography: String) {
        this.artistBiography = biography
    }
}