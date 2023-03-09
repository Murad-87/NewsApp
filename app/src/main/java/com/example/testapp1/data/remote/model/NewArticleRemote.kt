package com.example.testapp1.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewArticleRemote(
    val category: List<String>,
    val content: String,
    val description: String?,
    val image_url: String,
    val language: String,
    val link: String,
    val pubDate: String,
    val source_id: String,
    val title: String,
) : Parcelable {

    override fun hashCode(): Int {
        var result = source_id.hashCode()
        if (description.isNullOrEmpty()) {
            result = 31 * result + description.hashCode()
        }
        return result
    }
}