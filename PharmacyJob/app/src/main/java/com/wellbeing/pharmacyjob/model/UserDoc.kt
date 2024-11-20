package com.wellbeing.pharmacyjob.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDoc(
    val imageId: String,
    val imageType: String,
) : Parcelable
