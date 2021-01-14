package com.rupeswar.codershub.ui.conversations.conversation

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.daos.ConversationDAO
import com.rupeswar.codershub.daos.ConversationsDAO
import com.rupeswar.codershub.daos.UserDAO
import com.rupeswar.codershub.models.Message
import com.rupeswar.codershub.models.conversations.Conversations
import com.rupeswar.codershub.ui.userInfo.UserInfoViewModel

class ConversationViewModel : ViewModel() {

    fun getConversation(cid: String): Task<DocumentSnapshot> {
        val conversationsDAO = ConversationsDAO()
        return conversationsDAO.getConversationById(cid)
    }

    fun getMessages(cid: String): CollectionReference {
        val conversationDAO = ConversationDAO(cid)
        return conversationDAO.messagesCollection
    }

    fun putMessage(cid: String, text: String) {
        val conversationDAO = ConversationDAO(cid)
        conversationDAO.addMessage(text)
    }
}