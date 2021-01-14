package com.rupeswar.codershub.ui.codeforces.userInfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.rupeswar.codershub.R
import com.rupeswar.codershub.ui.codeforces.CodeforcesViewModel
import com.rupeswar.codershub.MySingleton
import org.json.JSONObject
import java.util.*

class CodeforcesUserInfoFragment : Fragment() {

    private lateinit var codeforcesViewModel: CodeforcesViewModel
    private var root: View? = null
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        codeforcesViewModel = ViewModelProvider(this).get(CodeforcesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_codeforces_user_info, container, false)
        id = arguments?.getString("id")!!
        activity?.actionBar?.title  = getString(R.string.menu_codeforces, id)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, codeforcesViewModel.getUserURL(id), null,
            {
                val progressBar: ProgressBar = root!!.findViewById(R.id.progressBar)
                progressBar.visibility = View.GONE

                setUpUserData(it)
            },
            {
                Log.d("myApp", "An Error Occurred -> ${it.stackTraceToString()}")
                val progressBar: ProgressBar = root!!.findViewById(R.id.progressBar)
                progressBar.visibility = View.GONE

                val errorView: TextView = root!!.findViewById(R.id.errorView)
                errorView.text = "An Unexpected Error Occurred.\n"
                errorView.visibility = View.VISIBLE
            })

        MySingleton.getInstance(root!!.context).addToRequestQueue(jsonObjectRequest)

        return root
    }

    private fun setUpUserData(jsonObject: JSONObject) {
        val userData = jsonObject.getJSONArray("result").getJSONObject(0)

        val imageView: ImageView = root!!.findViewById(R.id.user_image)
        val imageURL = "https:" + userData.getString("titlePhoto")
        Glide.with(root!!.context).load(imageURL).into(imageView)

        val handle: TextView = root!!.findViewById(R.id.user_handle)
        handle.text = userData.getString("handle")

        val rating: TextView = root!!.findViewById(R.id.rating)
        rating.text = userData.getString("rating")

        val maxRating: TextView = root!!.findViewById(R.id.max_rating)
        maxRating.text = userData.getString("maxRating")

        val rank: TextView = root!!.findViewById(R.id.rank)
        rank.text = userData.getString("rank").capitalize(Locale.ROOT)

        val maxRank: TextView = root!!.findViewById(R.id.max_rank)
        maxRank.text = userData.getString("maxRank").capitalize(Locale.ROOT)

        val contestHistory: Button = root!!.findViewById(R.id.contest_history)
        contestHistory.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", id)
            findNavController().navigate(R.id.nav_codeforces_contest_history, bundle)
        }

        val cardView: CardView = root!!.findViewById(R.id.cardView)

        imageView.visibility = View.VISIBLE
        handle.visibility = View.VISIBLE
        cardView.visibility = View.VISIBLE
        contestHistory.visibility = View.VISIBLE
    }
}