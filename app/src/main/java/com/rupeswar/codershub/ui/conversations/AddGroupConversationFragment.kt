package com.rupeswar.codershub.ui.conversations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.rupeswar.codershub.R

class AddGroupConversationFragment : Fragment() {

    private lateinit var conversationsViewModel: ConversationsViewModel
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        conversationsViewModel = ViewModelProvider(this).get(ConversationsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_add_group_conversation, container, false)

        setupAddConversation()

        return root
    }

    private fun setupAddConversation() {
        val nextButton: Button = root!!.findViewById(R.id.add_more_info)
        val addButton: Button = root!!.findViewById(R.id.add_group_conversation)
        val editText: EditText = root!!.findViewById(R.id.name)

        nextButton.setOnClickListener {
            val groupName = editText.text.toString()
            if(groupName.equals("", true)) {
                Toast.makeText(root!!.context, "Add Group Name!!!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            editText.text.clear()
            val params = nextButton.layoutParams as ConstraintLayout.LayoutParams
            params.horizontalBias = 0.25F
            editText.hint = " Enter Group Member Email"
            nextButton.text = "Add Another Member"
            addButton.visibility = View.VISIBLE
            val memberEmails = ArrayList<String>()

            nextButton.setOnClickListener {
                val memberName = editText.text.toString()
                if(memberName.equals("", true)) {
                    Toast.makeText(root!!.context, "Add Participant Email!!!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                memberEmails.add(memberName)
                editText.text.clear()
            }

            addButton.setOnClickListener {
                val memberName = editText.text.toString()
                if(memberName.equals("", true)) {
                    Toast.makeText(root!!.context, "Add Participant Email!!!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                memberEmails.add(memberName)
                editText.text.clear()

                conversationsViewModel.addGroupConversation(groupName, memberEmails)
            }
        }
    }
}