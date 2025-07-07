package com.example.mybookstore.ui.collection

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mybookstore.MyBookStoreApp
import com.example.mybookstore.R
import com.example.mybookstore.data.local.BookEntity
import com.example.mybookstore.databinding.CollectionFragmentBinding
import io.objectbox.Box

class CollectionFragment : Fragment() {

    private var _binding: CollectionFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedBookAdapter: SavedBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedBookAdapter = SavedBookAdapter(
            onItemClick = { savedBook ->
                // ✅ Navigate to detail on click
                val bundle = Bundle().apply {
                    putString("title", savedBook.title)
                    putString("author", savedBook.author)
                    putString("description", savedBook.description)
                    putString("thumbnail", savedBook.imageUrl)
                }
                findNavController().navigate(R.id.bookDetailFragment, bundle)
            },
            onItemLongClick = { savedBook ->
                // ✅ Show confirmation dialog on long press
                showDeleteConfirmation(savedBook)
            }
        )

        binding.collectionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.collectionRecyclerView.adapter = savedBookAdapter

        loadSavedBooks()
    }

    private fun loadSavedBooks() {
        val bookBox: Box<BookEntity> = MyBookStoreApp.boxStore.boxFor(BookEntity::class.java)
        val savedBooks = bookBox.all
        savedBookAdapter.submitList(savedBooks)
    }

    private fun showDeleteConfirmation(book: BookEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Book")
            .setMessage("Are you sure you want to delete '${book.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                deleteBook(book)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteBook(book: BookEntity) {
        val bookBox: Box<BookEntity> = MyBookStoreApp.boxStore.boxFor(BookEntity::class.java)
        bookBox.remove(book)
        loadSavedBooks() // Refresh list after deletion
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
