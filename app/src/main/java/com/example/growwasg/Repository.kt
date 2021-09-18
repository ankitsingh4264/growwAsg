package com.example.growwasg

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.growwasg.dataclass.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Repository {
    val email=FirebaseAuth.getInstance().currentUser?.email

    private  val db=Firebase.firestore
    private val TAG="ankit"


   fun getUserData() :MutableLiveData<User> {
      val userData= MutableLiveData<User>()
        db.collection("users").document(email!!).get().addOnSuccessListener {
            if (it.data==null){
                //users doesn't exist
                val user= hashMapOf(
                    "email" to email,
                    "todos" to ArrayList<String>()
                )
                userData.value= User(email,ArrayList())
                db.collection("users").document(email).set(user)
            }else{
                 val data= it.toObject(User::class.java)
                userData.value=data
            }
        }.addOnFailureListener {
            Log.d("ankit", "getUser: something wrong")
        }
       return userData

    }

    fun listenTodo():MutableLiveData<ArrayList<String>> {
        val update= MutableLiveData<ArrayList<String>>()

        val docRef = db.collection("users").document(email!!)
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                update.value=snapshot.toObject(User::class.java)?.todos
                Log.d(TAG, "Current data: ${snapshot.data}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
        return update
    }

    fun updateData(list:ArrayList<String>) : MutableLiveData<Boolean> {
        val update= MutableLiveData<Boolean>()

        val data= User(email,list);
        db.collection("users").document(email!!).set(data).addOnSuccessListener {
            update.value=true
        }.addOnFailureListener {
            update.value=false
        }
        return update

    }


}