package com.example.mybookstore.data.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class BookEntity(
    @Id
    var id: Long = 0,  // ObjectBox-managed ID
    var title: String = "",
    var author: String = "",
    var imageUrl: String = "",
    var description: String = "",
    var thumbnail: String = ""
)
