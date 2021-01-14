package com.rupeswar.codershub.models.conversations

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.models.Message
import com.rupeswar.codershub.models.User

data class Personal(
    override val cid: String? = "",
    override val lastUsed: Long? = 0,
    val members: ArrayList<DocumentReference>? = ArrayList(),
    val memberIds: ArrayList<String>? = ArrayList(),
    val memberNames: ArrayList<String>? = ArrayList()
) : Conversations(false)
