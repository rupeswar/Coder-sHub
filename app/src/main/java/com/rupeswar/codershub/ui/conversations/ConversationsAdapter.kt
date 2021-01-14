package com.rupeswar.codershub.ui.conversations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.conversations.Conversations

class ConversationsAdapter(options: FirestoreRecyclerOptions<Conversations>) :
    FirestoreRecyclerAdapter<Conversations, ConversationsAdapter.ConversationViewHolder>(options) {

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val conversationName : TextView = itemView.findViewById(R.id.conversation_name)
        val conversation : ConstraintLayout = itemView.findViewById(R.id.conversation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        return ConversationViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false))
    }

    override fun onBindViewHolder(
        holder: ConversationViewHolder,
        position: Int,
        model: Conversations
    ) {
        holder.conversationName.text = model.name

        val bundle = Bundle()
        bundle.putString("cid", snapshots.getSnapshot(holder.adapterPosition).id)
        holder.conversation.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_conversation, bundle))
    }
}