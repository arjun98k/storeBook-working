package com.example.mybookstore.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybookstore.R
import com.example.mybookstore.data.model.Book
import com.example.mybookstore.databinding.ItemBookBinding

class BookAdapter : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    private var books: List<Book> = emptyList()

    fun submitList(list: List<Book>) {
        books = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun getItemCount() = books.size

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.bookTitle.text = book.volumeInfo.title
            binding.bookAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown"

            val thumbnail = book.volumeInfo.imageLinks?.thumbnail
            if (thumbnail != null) {
                Glide.with(binding.bookImage.context)
                    .load(thumbnail)
                    .into(binding.bookImage)
            } else {
                binding.bookImage.setImageResource(android.R.color.darker_gray)
            }

            binding.bookTag.text = if (book.volumeInfo.description?.contains("fiction", true) == true) {
                "Fiction"
            } else {
                "General"
            }

            // Click listener for navigation
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
    }
}
