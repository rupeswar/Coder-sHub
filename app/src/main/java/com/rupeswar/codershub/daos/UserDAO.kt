package com.rupeswar.codershub.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rupeswar.codershub.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDAO {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    fun addUser(user: User?) {
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                usersCollection.document(user.uid!!).set(user)
            }
        }
    }

    fun getUserDocumentById(uId: String): DocumentReference {
        return usersCollection.document(uId)
    }

    fun getUserByEmail(email: String): Task<QuerySnapshot> {
        return usersCollection.whereEqualTo("email", email).get()
    }
}