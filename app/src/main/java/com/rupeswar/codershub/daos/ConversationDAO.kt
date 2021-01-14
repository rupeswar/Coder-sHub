package com.rupeswar.codershub.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.models.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ConversationDAO(cid: String) {

    private val db = FirebaseFirestore.getInstance()
    val messagesCollection = db.collection("conversations").document(cid).collection("messages")
    private val auth = Firebase.auth

    fun addMessage(messageText: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val messageReference = messagesCollection.document()
            val message = Message(
                messageReference.id,
                auth.currentUser!!.uid,
                System.currentTimeMillis(),
                messageText
            )
            messageReference.set(message)
        }
    }

    fun getMessageById(mId: String): Task<DocumentSnapshot> {
        return messagesCollection.document(mId).get()
    }
}