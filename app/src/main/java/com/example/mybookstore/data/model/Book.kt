package com.example.mybookstore.data.model

data class ImageLinks(
    val thumbnail: String?
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val imageLinks: ImageLinks?
)

data class Book(
    val volumeInfo: VolumeInfo
)
