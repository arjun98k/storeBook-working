package com.example.mybookstore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybookstore.R
import com.example.mybookstore.data.local.BookEntity
import com.example.mybookstore.data.model.Book
import com.example.mybookstore.databinding.ItemBookBinding

class BookAdapter(
    private val onSavedBookClick: ((BookEntity) -> Unit)? = null
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var apiBooks: List<Book> = emptyList()
    private var savedBooks: List<BookEntity> = emptyList()
    private var showingSavedBooks = false

    fun submitApiBooks(list: List<Book>) {
        showingSavedBooks = false
        apiBooks = list
        notifyDataSetChanged()
    }

    fun submitSavedBooks(list: List<BookEntity>) {
        showingSavedBooks = true
        savedBooks = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (showingSavedBooks) savedBooks.size else apiBooks.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        if (showingSavedBooks) {
            holder.bindSaved(savedBooks[position])
        } else {
            holder.bindApi(apiBooks[position])
        }
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindApi(book: Book) {
            binding.bookTitle.text = book.volumeInfo.title
            binding.bookAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown"

            val thumbnail = book.volumeInfo.imageLinks?.thumbnail
            if (thumbnail != null) {
                Glide.with(binding.root).load(thumbnail).into(binding.bookImage)
            } else {
                binding.bookImage.setImageResource(android.R.color.darker_gray)
            }

            binding.bookTag.text = if (book.volumeInfo.description?.contains("fiction", true) == true) {
                "Fiction"
            } else {
                "General"
            }

            binding.root.setOnClickListener {
                val bundle = Bundle().apply {
                    putString("title", book.volumeInfo.title)
                    putString("author", book.volumeInfo.authors?.joinToString(", "))
                    putString("description", book.volumeInfo.description)
                    putString("thumbnail", book.volumeInfo.imageLinks?.thumbnail)
                }
                it.findNavController().navigate(R.id.action_homeFragment_to_bookDetailFragment, bundle)
            }
        }

        fun bindSaved(book: BookEntity) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookTag.text = "Saved"

            if (book.imageUrl.isNotEmpty()) {
                Glide.with(binding.root).load(book.imageUrl).into(binding.bookImage)
            } else {
                binding.bookImage.setImageResource(android.R.color.darker_gray)
            }

            binding.root.setOnClickListener {
                onSavedBookClick?.invoke(book)
            }
        }
    }
}
