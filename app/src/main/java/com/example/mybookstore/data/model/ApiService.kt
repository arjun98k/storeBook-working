package com.example.mybookstore.data.network

import com.example.mybookstore.data.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String
    ): Response<BookResponse>
}
