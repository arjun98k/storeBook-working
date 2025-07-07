package com.example.mybookstore.ui.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybookstore.data.local.BookEntity
import com.example.mybookstore.databinding.ItemBookBinding

class SavedBookAdapter(
    private val onItemClick: (BookEntity) -> Unit,
    private val onItemLongClick: (BookEntity) -> Unit  // ✅ ADDED: long click callback
) : RecyclerView.Adapter<SavedBookAdapter.BookViewHolder>() {

    private val books = mutableListOf<BookEntity>()

    fun submitList(list: List<BookEntity>) {
        books.clear()
        books.addAll(list)
        notifyDataSetChanged()
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            Glide.with(binding.root).load(book.imageUrl).into(binding.bookImage)

            // ✅ ON CLICK: navigate to detail
            binding.root.setOnClickListener {
                onItemClick.invoke(book)
            }

            // ✅ ON LONG CLICK: confirm delete
            binding.root.setOnLongClickListener {
                onItemLongClick.invoke(book)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}
