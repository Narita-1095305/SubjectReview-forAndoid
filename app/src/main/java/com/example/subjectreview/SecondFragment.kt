package com.example.subjectreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subjectreview.databinding.FragmentSecondBinding
import io.realm.Realm
import io.realm.kotlin.where

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.layoutManager = LinearLayoutManager(context)
        val subject = realm.where<Subject>().findAll()
        val adapter = SubjectAdapter(subject)
        binding.list.adapter = adapter

        adapter.setOnItemClickListener {
            id->id?.let {
            val action =
                SecondFragmentDirections.actionSecondFragmentToReviewShowFragment(it)
                findNavController().navigate(action)
            }
        }
        (activity as? MainActivity)?.setFabVisible(View.INVISIBLE)

    }
}