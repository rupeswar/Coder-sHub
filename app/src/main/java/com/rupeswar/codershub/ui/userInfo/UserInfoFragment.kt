package com.rupeswar.codershub.ui.userInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.rupeswar.codershub.R
import com.rupeswar.codershub.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserInfoFragment : Fragment() {

    private lateinit var userInfoViewModel: UserInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userInfoViewModel =
            ViewModelProvider(this).get(UserInfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_info, container, false)
        val userImage: ImageView = root.findViewById(R.id.user_image)
        val userName: TextView = root.findViewById(R.id.user_name)
        val userMail: TextView = root.findViewById(R.id.user_email)

        GlobalScope.launch {
            val user = userInfoViewModel.getUser().await().toObject(User::class.java)!!
            withContext(Dispatchers.Main) {
                Glide.with(userImage.context).load(user.imageUrl).circleCrop().into(userImage)
                userName.text = user.displayName
                userMail.text = "Email : " + user.email
            }
        }

        return root
    }
}