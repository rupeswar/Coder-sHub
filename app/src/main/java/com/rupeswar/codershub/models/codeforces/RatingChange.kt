package com.rupeswar.codershub.models.codeforces

data class RatingChange(val cid: Int, val name: String, val rank: Int, val oldRating: Int, val newRating: Int, val change: Int = newRating-oldRating)
