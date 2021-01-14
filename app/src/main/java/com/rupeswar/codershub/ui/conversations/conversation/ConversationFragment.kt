package com.rupeswar.codershub.ui.conversations.conversation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.Message
import com.rupeswar.codershub.models.User
import com.rupeswar.codershub.models.conversations.Conversations
import com.rupeswar.codershub.models.conversations.Group
import com.rupeswar.codershub.models.conversations.Personal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ConversationFragment : Fragment() {

    private lateinit var conversationViewModel: ConversationViewModel
    private var root: View? = null
    private lateinit var cid: String
    private var conversation: Conversations? = null
    private lateinit var adapter: ConversationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        conversationViewModel = ViewModelProvider(this).get(ConversationViewModel::class.java)
        cid = arguments?.getString("cid").toString()
        root = inflater.inflate(R.layout.fragment_conversation, container, false)

        GlobalScope.launch {
            val documentSnapshot = conversationViewModel.getConversation(cid).await()
            val type: Boolean? = documentSnapshot.getBoolean("group")
            conversation = (if(type!!) documentSnapshot.toObject(Group::class.java) else documentSnapshot.toObject(Personal::class.java))!!
        }

        setUpRecyclerView()
        setUpSendMessage()

        return root
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun setUpRecyclerView() {
        val messagesCollection = conversationViewModel.getMessages(cid)
        val query = messagesCollection.orderBy("time", Query.Direction.ASCENDING)
        val recyclerViewOptions =
            FirestoreRecyclerOptions.Builder<Message>().setQuery(query, Message::class.java).build()
        adapter = ConversationAdapter(recyclerViewOptions)

        val recyclerView: RecyclerView = root!!.findViewById(R.id.messages)
        recyclerView.visibility = View.VISIBLE
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(root!!.context)
    }

    private fun setUpSendMessage() {
        val editText: EditText = root!!.findViewById(R.id.type_message)
        val button: ImageButton = root!!.findViewById(R.id.send_message)

        button.setOnClickListener {
            conversationViewModel.putMessage(cid, editText.text.toString())
            editText.text.clear()
        }
    }
}