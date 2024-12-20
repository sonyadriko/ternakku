package com.example.ternakku.repository

import android.content.Context
import com.example.ternakku.constant.Constant
import com.example.ternakku.constant.Constant.REFERENCE_USER
import com.example.ternakku.domain.User
import com.example.ternakku.util.toRole
import com.google.firebase.database.FirebaseDatabase

class UserRepository constructor(
    context: Context
) {
    private val database = FirebaseDatabase.getInstance().getReference(REFERENCE_USER)

    companion object {
        const val ROLE = "role"
        const val UID = "uid"
        const val NAMA = "nama"

        @Volatile
        private var INSTANCE: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = UserRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val sharedPreferences =
        context.getSharedPreferences("${context.packageName}_pref", Context.MODE_PRIVATE)

    var uid: String? = null
        get() = field ?: getStringFromSharedPref(UID)
        set(value) {
            field = value
            saveStringToSharedPref(UID, value)
        }

    var nama: String? = null
        get() = field ?: getStringFromSharedPref(NAMA)
        set(value) {
            field = value
            saveStringToSharedPref(NAMA, value)
        }

    var role: Constant.Role? = null
        get() = field ?: getStringFromSharedPref(ROLE)?.toRole()
        set(value) {
            field = value
            saveStringToSharedPref(ROLE, value?.toString()?.lowercase())
        }

    fun setUserData(uid: String, user: User) {
        this.role = user.role.toRole()
        this.uid = uid
        this.nama = user.nama
    }

    fun erase() {
        uid = null
        role = null
        nama = null
    }

    fun getRemoteUserData(userUid: String, onComplete: (isSuccess: Boolean, user: User?) -> Unit) {
        database.child(userUid).get()
            .addOnCompleteListener {
                onComplete(it.isSuccessful, it.result.getValue(User::class.java))
            }
    }

    private fun getStringFromSharedPref(key: String): String? {
        return if (sharedPreferences.contains(key))
            sharedPreferences.getString(key, "")
        else
            null
    }

    private fun saveStringToSharedPref(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putString(key, value)
        editor.apply()
    }
}