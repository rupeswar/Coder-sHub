package com.rupeswar.codershub.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.rupeswar.codershub.daos.UserDAO
import com.rupeswar.codershub.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserInfoViewModel : ViewModel() {

    private  val auth = Firebase.auth;

    fun getUser() : Task<DocumentSnapshot> {
        return getUserDocument().get()
    }

    fun getUserDocument(): DocumentReference {
        val userDAO = UserDAO()
        val currentUserId = auth.currentUser!!.uid
        return userDAO.getUserDocumentById(currentUserId)
    }
}