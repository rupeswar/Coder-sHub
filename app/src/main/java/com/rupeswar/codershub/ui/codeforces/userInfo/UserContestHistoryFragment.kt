package com.rupeswar.codershub.ui.codeforces.userInfo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.codeforces.RatingChange
import com.rupeswar.codershub.ui.codeforces.CodeforcesViewModel
import com.rupeswar.codershub.MySingleton

class UserContestHistoryFragment : Fragment() {

    private lateinit var codeforcesViewModel: CodeforcesViewModel
    private var root: View? = null
    private lateinit var id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        codeforcesViewModel = ViewModelProvider(this).get(CodeforcesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_user_contest_history, container, false)
        id = arguments?.getString("id")!!

        val recyclerView: RecyclerView = root!!.findViewById(R.id.contest_recycler_view)
        val progressBar: ProgressBar = root!!.findViewById(R.id.progressBar)
        val adapter = RatingChangeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root!!.context)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            codeforcesViewModel.getContestHistoryURL(id),
            null,
            {
                val ratingChangeJsonArray = it.getJSONArray("result")
                progressBar.visibility = View.GONE

                if (ratingChangeJsonArray.length() == 0) {
                    val emptyView: TextView = root!!.findViewById(R.id.emptyView)
                    emptyView.visibility = View.VISIBLE
                } else {
                    val ratingChangeArray = ArrayList<RatingChange>()
                    for (i in ratingChangeJsonArray.length() - 1 downTo 0) {
                        val ratingChangeJsonObject = ratingChangeJsonArray.getJSONObject(i)
                        val ratingChange = RatingChange(
                            ratingChangeJsonObject.getInt("contestId"),
                            ratingChangeJsonObject.getString("contestName"),
                            ratingChangeJsonObject.getInt("rank"),
                            ratingChangeJsonObject.getInt("oldRating"),
                            ratingChangeJsonObject.getInt("newRating")
                        )
                        ratingChangeArray.add(ratingChange)
                    }

                    Log.d("myApp", "Size is ${ratingChangeArray.size}")

                    adapter.updateRatingChanges(ratingChangeArray)
                    recyclerView.visibility = View.VISIBLE
                }
            },
            {
                Log.d("myApp", "An Error Occurred ${it.stackTraceToString()}")
            })

        MySingleton.getInstance(root!!.context).addToRequestQueue(jsonObjectRequest)

        return root
    }
}