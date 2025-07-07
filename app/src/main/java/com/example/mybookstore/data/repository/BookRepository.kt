package com.example.mybookstore.data.repository

import com.example.mybookstore.data.network.RetrofitClient

class BookRepository {
    private val api = RetrofitClient.apiService

    suspend fun searchBooks(query: String) = api.searchBooks(query)
}
