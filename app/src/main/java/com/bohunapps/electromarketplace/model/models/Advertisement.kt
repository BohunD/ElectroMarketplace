package com.bohunapps.electromarketplace.model.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Advertisement(
    val adId: String?,
    val photos: MutableList<String>?,
    val name: String?,
    val price: String?,
    val categoryName: String?,
    val filters: Map<String, String>?,
    val description: String?,
    val city: String?,
    val dateTime: Long?,
    val userId: String?,
    val likedUsers: ArrayList<String>?= arrayListOf(""),
    val nameInArray: ArrayList<String>?= name?.split(" ")?.let { ArrayList(it) } ?: null
): Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null, null, null, null)
}