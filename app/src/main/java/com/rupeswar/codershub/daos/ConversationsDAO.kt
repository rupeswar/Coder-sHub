package com.rupeswar.codershub.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.models.conversations.Conversations
import com.rupeswar.codershub.models.conversations.Group
import com.rupeswar.codershub.models.conversations.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ConversationsDAO() {

    private val db = FirebaseFirestore.getInstance()
    val conversationsCollection = db.collection("conversations")
    private val auth = Firebase.auth

    fun addConversation(email: String) {
        val userDAO = UserDAO()
        val recipientIdTask = userDAO.getUserByEmail(email)

        GlobalScope.launch(Dispatchers.IO) {
            val recipientDocumentSnapshot = recipientIdTask.await().documents[0]
            val recipientId = recipientDocumentSnapshot.getString("uid") ?: return@launch
            val recipient = userDAO.getUserDocumentById(recipientId)
            val members = ArrayList<DocumentReference>()
            members.add(recipient)
            members.add(userDAO.getUserDocumentById(auth.currentUser!!.uid))
            val membersId = ArrayList<String>()
            membersId.add(recipientId)
            membersId.add(auth.currentUser!!.uid)
            val membersNames = ArrayList<String>()
            membersNames.add(recipientDocumentSnapshot.getString("displayName")!!)
            membersNames.add(
                userDAO.getUserDocumentById(auth.currentUser!!.uid).get().await()
                    .getString("displayName")!!
            )
            val conversationReference = conversationsCollection.document()
            val conversation = Personal(
                conversationReference.id,
                System.currentTimeMillis(),
                members,
                membersId,
                membersNames
            )
            conversationReference.set(conversation)
        }
    }

    fun addGroupConversation(groupName: String, emails: ArrayList<String>) {
        val userDAO = UserDAO()
        val memberIdTasks = ArrayList<Task<QuerySnapshot>>()
        for (email in emails) {
            memberIdTasks.add(userDAO.getUserByEmail(email))
        }

        GlobalScope.launch(Dispatchers.IO) {
            val memberIds = ArrayList<String>()
            val members = ArrayList<DocumentReference>()
            for (memberIdTask in memberIdTasks) {
                val memberId = memberIdTask.await().documents[0].getString("uid") ?: return@launch
                memberIds.add(memberId)
                val member = userDAO.getUserDocumentById(memberId)
                members.add(member)
            }
            memberIds.add(auth.currentUser!!.uid)
            members.add(userDAO.getUserDocumentById(auth.currentUser!!.uid))

            val conversationReference = conversationsCollection.document()
            val conversation = Group(
                conversationReference.id,
                System.currentTimeMillis(),
                groupName,
                members,
                memberIds
            )
            conversationReference.set(conversation)
        }
    }

    fun getConversationById(cId: String): Task<DocumentSnapshot> {
        return conversationsCollection.document(cId).get()
    }
}