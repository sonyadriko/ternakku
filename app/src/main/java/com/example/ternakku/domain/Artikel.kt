package com.example.ternakku.domain

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//@Parcelize
@Parcelize
class Artikel(
    @SerializedName("image")
    val image: String = "",
    @SerializedName("judul")
    val judul: String = "",
    @SerializedName("desc")
    val desc: String = "",
) : Serializable, Parcelable {
    fun toMap(): Map<String, Any?> {
        val gson = Gson()
        val json = gson.toJson(this)
        return gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
    }
}