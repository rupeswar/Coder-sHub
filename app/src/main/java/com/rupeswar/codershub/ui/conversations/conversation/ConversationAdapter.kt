package com.rupeswar.codershub.ui.conversations.conversation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.Message

class ConversationAdapter(options: FirestoreRecyclerOptions<Message>) :
    FirestoreRecyclerAdapter<Message, ConversationAdapter.MessageViewHolder>(options) {

    private val auth = Firebase.auth

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message: TextView = itemView.findViewById(R.id.message)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.message_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        val message = holder.message
        message.text = model.text
        Log.d("Message Found: ", model.text.toString())
        val constraintLayout = holder.constraintLayout
        val params = message.layoutParams as ConstraintLayout.LayoutParams
        if(model.senderId == auth.currentUser!!.uid){
            message.background = ContextCompat.getDrawable(message.context, R.drawable.sent_message_box)
            constraintLayout.setPadding(100, 0, 0, 0)
            params.horizontalBias = 1.0F
        }
        else {
            message.background = ContextCompat.getDrawable(message.context, R.drawable.received_message_box)
            constraintLayout.setPadding(0, 0, 100, 0)
            params.horizontalBias = 0.0F
        }

        message.text = model.text
    }
}