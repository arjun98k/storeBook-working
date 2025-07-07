package com.example.mybookstore.ui.collection

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
import com.example.mybookstore.ui.home.BookAdapter
import io.objectbox.Box

class CollectionFragment : Fragment() {

    private var _binding: CollectionFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: BookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookAdapter = BookAdapter { savedBook ->
            val bundle = Bundle().apply {
                putString("title", savedBook.title)
                putString("author", savedBook.author)
                putString("description", savedBook.description)
                putString("thumbnail", savedBook.imageUrl)
            }
            findNavController().navigate(R.id.bookDetailFragment, bundle)
        }

        binding.collectionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.collectionRecyclerView.adapter = bookAdapter

        loadSavedBooks()
    }

    private fun loadSavedBooks() {
        val bookBox: Box<BookEntity> = MyBookStoreApp.boxStore.boxFor(BookEntity::class.java)
        val savedBooks = bookBox.all
        bookAdapter.submitSavedBooks(savedBooks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
