package com.rupeswar.codershub.ui.conversations

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.daos.ConversationsDAO
import com.rupeswar.codershub.daos.UserDAO
import com.rupeswar.codershub.models.conversations.Personal

class ConversationsViewModel : ViewModel() {

    fun getConversations() : CollectionReference {
        val conversationsDAO = ConversationsDAO()
        return conversationsDAO.conversationsCollection
    }

    fun addConversation(email: String) {
        val conversationsDAO = ConversationsDAO()
        conversationsDAO.addConversation(email)
    }

    fun addGroupConversation(groupName: String, emails: ArrayList<String>) {
        val conversationsDAO = ConversationsDAO()
        conversationsDAO.addGroupConversation(groupName, emails)
    }
}