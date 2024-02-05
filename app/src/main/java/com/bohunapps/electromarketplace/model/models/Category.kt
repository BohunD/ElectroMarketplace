package com.bohunapps.electromarketplace.model.models

data class Category(
    val name: String,
    val imagePath: Int,
    val filters: Map<String, ArrayList<String>>
)