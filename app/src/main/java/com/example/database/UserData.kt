package com.example.database

import android.provider.ContactsContract.CommonDataKinds.Email

class UserData {
    var email :String? = null
    var password :String? = null
    constructor(){}
    constructor(email: String?,password:String?){
        this.email = email
        this.password = password
    }
}