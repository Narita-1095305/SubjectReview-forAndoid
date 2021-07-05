package com.example.subjectreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subjectreview.databinding.FragmentReviewShowBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.realm.Realm
import io.realm.kotlin.where


/**
 * A simple [Fragment] subclass.
 * Use the [ReviewShowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewShowFragment : Fragment() {

    private val args: ReviewShowFragmentArgs by navArgs()
    private var _binding: FragmentReviewShowBinding? = null
    private val binding get() = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(context)

        val reviews = realm.where<Review>().equalTo("subjectId", args.subjectId).findAll()
        val adapter = ReviewAdapter(reviews)
        binding.list.adapter = adapter

        adapter.setOnItemClickListener { id->
            id?.let {
            val action =
                ReviewShowFragmentDirections.actionReviewShowFragmentToReviewDetailFragment(it)
            findNavController().navigate(action)
            }
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val action = ReviewShowFragmentDirections.actionReviewShowFragmentToReviewEditFragment(args.subjectId)
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}