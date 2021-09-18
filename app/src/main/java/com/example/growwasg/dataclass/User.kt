package com.example.growwasg.dataclass

import android.provider.ContactsContract

data class User (
    val email: String?=null,
    val todos:ArrayList<String>?=null
)