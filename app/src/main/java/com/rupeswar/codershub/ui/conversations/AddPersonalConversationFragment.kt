package com.rupeswar.codershub.ui.conversations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rupeswar.codershub.R

class AddPersonalConversationFragment : Fragment() {

    private lateinit var conversationsViewModel: ConversationsViewModel
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        conversationsViewModel = ViewModelProvider(this).get(ConversationsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_add_personal_conversation, container, false)

        setUpAddConversation()

        return root
    }

    private fun setUpAddConversation() {
        val button: Button = root!!.findViewById(R.id.add_personal_conversation)
        val editText: EditText = root!!.findViewById(R.id.member_name)
        button.setOnClickListener {
            val recipient = editText.text.toString()
            if(!recipient.equals("", true)) {
                conversationsViewModel.addConversation(recipient)
            }
            else {
                Toast.makeText(root!!.context, "Add Participant Email!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}