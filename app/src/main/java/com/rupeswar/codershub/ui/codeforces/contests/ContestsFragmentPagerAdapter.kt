package com.rupeswar.codershub.ui.codeforces.contests

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rupeswar.codershub.models.codeforces.Contest

class ContestsFragmentPagerAdapter(fragment: Fragment, private val contestLists: Array<ArrayList<Contest>>): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return ContestsFragmentPagerObject(contestLists[position], position == 1)
    }
}