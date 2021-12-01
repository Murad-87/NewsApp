package com.example.testapp1.data.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourceLocal(
    val sourceId: Int?,
    val name: String?
): Parcelable