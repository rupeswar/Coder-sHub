package com.rupeswar.codershub.ui.conversations

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.rupeswar.codershub.R

class ChooseConversationTypeDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val items = arrayOf("Personal Conversation", "Group Conversation")
            builder.setTitle("Choose Conversation Type").setItems(items) { _, which ->
                if (which == 0) {
                    findNavController().navigate(R.id.nav_add_personal_conversation)
                } else if (which == 1) {
                    findNavController().navigate(R.id.nav_add_group_conversation)
                }
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}