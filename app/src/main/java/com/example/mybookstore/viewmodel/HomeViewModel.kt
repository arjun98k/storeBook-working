package com.example.mybookstore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybookstore.data.model.Book
import com.example.mybookstore.data.repository.BookRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = BookRepository()

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> get() = _books

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun fetchBooks(query: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.searchBooks(query)
                if (response.isSuccessful) {
                    _books.value = response.body()?.items ?: emptyList()
                    _error.value = null
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
