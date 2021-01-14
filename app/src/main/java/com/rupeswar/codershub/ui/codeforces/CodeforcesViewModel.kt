package com.rupeswar.codershub.ui.codeforces

import androidx.lifecycle.ViewModel

class CodeforcesViewModel : ViewModel() {

    private val codeforcesApiURL = "https://codeforces.com/api/"

    fun getUserURL(id: String): String {
        return codeforcesApiURL + "user.info?handles=" + id
    }

    fun getContestHistoryURL(id: String): String {
        return  codeforcesApiURL + "user.rating?handle=" + id
    }
    
    fun getAllContests(): String {
        return codeforcesApiURL + "contest.list"
    }
}