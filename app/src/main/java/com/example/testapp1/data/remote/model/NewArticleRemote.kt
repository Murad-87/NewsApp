package com.example.testapp1.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewArticleRemote(
    val category: List<String>,
    val content: String,
    val description: String?,
    @SerializedName("image_url")
    val imageUrl: String,
    val language: String,
    val link: String,
    val pubDate: String,
    @SerializedName("source_id")
    val sourceId: String,
    val title: String?,
) : Parcelable {

    override fun hashCode(): Int {
        var result = sourceId.hashCode()
        if (description.isNullOrEmpty()) {
            result = 31 * result + description.hashCode()
        }
        return result
    }
}