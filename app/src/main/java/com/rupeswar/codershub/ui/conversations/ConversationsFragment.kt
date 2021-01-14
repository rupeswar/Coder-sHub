package com.rupeswar.codershub.ui.conversations

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.SnapshotParser
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.conversations.Conversations
import com.rupeswar.codershub.models.conversations.Group
import com.rupeswar.codershub.models.conversations.Personal

class ConversationsFragment : Fragment() {

    private lateinit var conversationsViewModel: ConversationsViewModel
    private var root: View? = null
    private lateinit var adapter: ConversationsAdapter
    private val uid = Firebase.auth.currentUser!!.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        conversationsViewModel = ViewModelProvider(this).get(ConversationsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_conversations, container, false)

        setUpRecyclerView()
        setUpAddConversation()

        return root
    }

    private fun setUpRecyclerView() {
        val conversationsCollections = conversationsViewModel.getConversations()
        val query = conversationsCollections.whereArrayContains("memberIds", uid).orderBy("lastUsed", Query.Direction.DESCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<Conversations>()
            .setQuery(query, SnapshotParser {

                val type = it.getBoolean("group")
//                if(type == null) {
//                    Log.d("myApp", "group null for ${it.id}")
//                    type = false
//                }
//                return@SnapshotParser (if(type!!) it.toObject(Group::class.java) else it.toObject(Personal::class.java))!!

                if(type!!)
                    return@SnapshotParser it.toObject(Group::class.java)!!
                else {
                    val conversation = it.toObject(Personal::class.java)!!
                    conversation.name = if(conversation.memberIds!![0] == Firebase.auth.currentUser!!.uid) conversation.memberNames!![1] else conversation.memberNames!![0]
                    return@SnapshotParser conversation
                }
            }).build()

        adapter = ConversationsAdapter(recyclerViewOptions)

        val recyclerView: RecyclerView = root!!.findViewById(R.id.conversation_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root!!.context)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun setUpAddConversation() {
        val fab: FloatingActionButton = root!!.findViewById(R.id.add_personal_conversation)
//        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.nav_add_conversation))
        fab.setOnClickListener {
            val chooseConversationType = ChooseConversationTypeDialogFragment()
            chooseConversationType.show(this.parentFragmentManager, "Choose Conversation Type")
        }
    }
}