package com.example.mybookstore.ui.bookdetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mybookstore.MyBookStoreApp
import com.example.mybookstore.R
import com.example.mybookstore.data.local.BookEntity
import com.example.mybookstore.data.local.BookEntity_
import com.example.mybookstore.databinding.FragmentBookDetailBinding
import io.objectbox.Box
import io.objectbox.Property
import io.objectbox.query.QueryBuilder

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = arguments?.getString("title") ?: "No title"
        val author = arguments?.getString("author") ?: "Unknown author"
        val description = arguments?.getString("description") ?: "No description"
        val thumbnail = arguments?.getString("thumbnail")

        binding.titleText.text = title
        binding.authorText.text = author
        binding.descriptionText.text = description

        Glide.with(requireContext())
            .load(thumbnail)
            .into(binding.bookImage)

        binding.saveButton.setOnClickListener {
            val book = BookEntity(
                title = title,
                author = author,
                description = description,
                imageUrl = thumbnail ?: ""
            )
            saveBookToDB(book)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToCollection() {
        findNavController().navigate(R.id.action_bookDetailFragment_to_collectionFragment)
    }




    private fun saveBookToDB(book: BookEntity) {
        val bookBox: Box<BookEntity> = MyBookStoreApp.boxStore.boxFor(BookEntity::class.java)

        val existing = bookBox.query()
            .equal(BookEntity_.title, book.title, QueryBuilder.StringOrder.CASE_INSENSITIVE)
            .build()
            .findFirst()

        if (existing == null) {
            bookBox.put(book)
            Toast.makeText(requireContext(), "Book saved!", Toast.LENGTH_SHORT).show()

            // Navigate to My Collection
            navigateToCollection()

        } else {
            Toast.makeText(requireContext(), "Book already saved!", Toast.LENGTH_SHORT).show()
        }
    }


}

