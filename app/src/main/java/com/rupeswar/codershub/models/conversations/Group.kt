package com.rupeswar.codershub.models.conversations

import com.google.firebase.firestore.DocumentReference
import com.rupeswar.codershub.models.Message
import com.rupeswar.codershub.models.User

data class Group(
    override val cid: String? = "",
    override val lastUsed: Long? = 0,
    val groupName: String? = "",
    val members: ArrayList<DocumentReference>? = ArrayList(),
    val memberIds: ArrayList<String>? = ArrayList()
) : Conversations(true, groupName)