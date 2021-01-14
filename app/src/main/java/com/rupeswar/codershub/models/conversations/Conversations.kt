package com.rupeswar.codershub.models.conversations

import com.rupeswar.codershub.models.Message

abstract class Conversations(val group: Boolean? = false, var name: String? = "") {
    abstract val cid: String?
    abstract val lastUsed: Long?
}