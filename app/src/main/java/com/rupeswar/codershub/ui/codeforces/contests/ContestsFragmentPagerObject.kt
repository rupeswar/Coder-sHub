package com.rupeswar.codershub.ui.codeforces.contests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.codeforces.Contest

class ContestsFragmentPagerObject(private val contests: ArrayList<Contest>, private val isPast: Boolean) : Fragment() {

    private var root: View? = null
    private lateinit var contestRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_contests_pager_object, container, false)
        contestRecyclerView = root!!.findViewById(R.id.contestRecyclerView)
        val adapter = ContestAdapter(isPast)
        contestRecyclerView.adapter = adapter
        contestRecyclerView.layoutManager = LinearLayoutManager(root!!.context)
        adapter.updateContests(contests)

        return root
    }
}