package com.example.testapp1.data.remote.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class SourceRemote(
    val id: Int,
    val name: String
): Parcelable
//TODO: map this and entity in feature so that @Parcelize will be unnecessary