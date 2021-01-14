package com.rupeswar.codershub.ui.codeforces.contests

import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rupeswar.codershub.MySingleton
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.codeforces.Contest
import com.rupeswar.codershub.ui.codeforces.CodeforcesViewModel
import com.rupeswar.codershub.utils.sendNotification

class ContestsFragment : Fragment() {

    private lateinit var codeforcesViewModel: CodeforcesViewModel
    private var root: View? = null
    private lateinit var pager: ViewPager2
    private lateinit var pagerAdapter: ContestsFragmentPagerAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        codeforcesViewModel = ViewModelProvider(this).get(CodeforcesViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_contests, container, false)
        pager = root!!.findViewById(R.id.pager)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, codeforcesViewModel.getAllContests(), null, {
            val upcomingContests = ArrayList<Contest>();
            val pastContests = ArrayList<Contest>();

            val contestsJsonArray = it.getJSONArray("result")
            for(i in 0 until contestsJsonArray.length()) {
                val contestJsonObject = contestsJsonArray.getJSONObject(i)
                val contest = Contest(contestJsonObject.getInt("id"), contestJsonObject.getString("name"), contestJsonObject.getLong("durationSeconds")*1000L, contestJsonObject.getLong("startTimeSeconds")*1000L)
                if(contestJsonObject.getString("phase") == "BEFORE")
                    upcomingContests.add(contest)
                else
                    pastContests.add(contest)
            }
            updatePager(arrayOf(upcomingContests, pastContests))
            val progressBar: ProgressBar = root!!.findViewById(R.id.progressBar)
            progressBar.visibility = View.GONE
//            Log.d("myApp", "Upcoming Contests -> $upcomingContests")
//            Log.d("myApp", "Past Contests -> $pastContests")
        }, {
            Log.d("myApp", "An Error Occurred")
        })

        MySingleton.getInstance(root!!.context).addToRequestQueue(jsonObjectRequest)

        fab = root!!.findViewById(R.id.fab)
        fab.setOnClickListener {
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.sendNotification("Test Message", root!!.context)
        }

        return root
    }

    private fun updatePager(contestLists: Array<java.util.ArrayList<Contest>>) {
        pagerAdapter = ContestsFragmentPagerAdapter(this, contestLists)
        pager.adapter = pagerAdapter
        tabLayout = root!!.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, pager) { tab, position ->
            tab.text = if(position == 0) "Upcoming" else "Past"
        }.attach()
    }
}