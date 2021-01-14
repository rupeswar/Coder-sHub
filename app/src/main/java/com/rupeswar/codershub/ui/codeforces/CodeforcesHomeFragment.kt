package com.rupeswar.codershub.ui.codeforces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.rupeswar.codershub.R

class CodeforcesHomeFragment : Fragment() {

    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_codeforces_home, container, false)

        setUpCodeforcesFragment()

        return root
    }

    private fun setUpCodeforcesFragment() {
        val editText: EditText = root!!.findViewById(R.id.codeforces_id)
        val userInfo: CardView = root!!.findViewById(R.id.codeforces_user_info)

        userInfo.setOnClickListener {
            val id = editText.text.toString()

            if(id.equals("", true))
                Toast.makeText(root!!.context, "No Codeforces Handle Entered!!!", Toast.LENGTH_SHORT).show()
            else {
                val bundle = Bundle()
                bundle.putString("id", id)
                findNavController().navigate(R.id.nav_codeforces_user_info, bundle)
            }
        }

        val contests: CardView = root!!.findViewById(R.id.codeforces_contests)

        contests.setOnClickListener {
            findNavController().navigate(R.id.nav_codeforces_contests)
        }
    }
}