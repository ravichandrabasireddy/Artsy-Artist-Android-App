package com.example.artsyartist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.artsyartist.R
import com.example.artsyartist.databinding.FragmentArtistInfoBinding
import com.example.artsyartist.model.artistInfo


class ArtistInfoFragment(private val artistInfoData: artistInfo?) : Fragment() {
    private var _binding: FragmentArtistInfoBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        bindDataToView(view)
        return view
    }

    private fun bindDataToView(view: View) {
        val artistName = artistInfoData?.getArtistNames()
        val artistNationality = artistInfoData?.getArtistNationality()
        val artistBirthYear = artistInfoData?.getArtistBirthDate()
        val artistDeathYear = artistInfoData?.getArtistDeathDate()
        val artistBiography = artistInfoData?.getArtistBiography()
        view.tag=artistInfoData?.getArtistIds()
        if (artistName != null && artistName != "") {
            view.findViewById<TextView>(R.id.artistNameContainer).text = artistName
            view.findViewById<LinearLayout>(R.id.artistNameLayout).visibility = View.VISIBLE
        }
        if (artistNationality != null && artistNationality != "") {
            view.findViewById<TextView>(R.id.artistNationalityContainer).text = artistNationality
            view.findViewById<LinearLayout>(R.id.artistNationalityLayout).visibility = View.VISIBLE
        }
        if (artistBirthYear != null && artistBirthYear != "") {
            view.findViewById<TextView>(R.id.artistBirthYearContainer).text = artistBirthYear
            view.findViewById<LinearLayout>(R.id.artistBirthYearLayout).visibility = View.VISIBLE
        }
        if (artistDeathYear != null && artistDeathYear != "") {
            view.findViewById<TextView>(R.id.artistDeathYearContainer).text = artistDeathYear
            view.findViewById<LinearLayout>(R.id.artistDeathYearLayout).visibility = View.VISIBLE
        }
        if (artistBiography != null && artistBiography != "") {
            view.findViewById<TextView>(R.id.artistBiographyContainer).text = artistBiography
            view.findViewById<LinearLayout>(R.id.artistBiographyLayout).visibility = View.VISIBLE
        }
    }

}