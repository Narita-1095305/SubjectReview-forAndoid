package com.example.subjectreview

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.subjectreview.databinding.FragmentSubjectEditBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where

/**
 * A simple [Fragment] subclass.
 * Use the [SubjectEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubjectEditFragment : Fragment() {
    private var _binding: FragmentSubjectEditBinding? = null
    private val binding get () = _binding!!

    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? MainActivity)?.setFabVisible(View.INVISIBLE)
        binding.save.setOnClickListener{ saveSubject(it) }
    }

    private fun saveSubject(view: View) {
        realm.executeTransaction{ db: Realm ->
            val maxId = db.where<Subject>().max("id")
            val nextId = (maxId?.toLong() ?: 0L) + 1L
            val subject = db.createObject<Subject>(nextId)
            subject.title = binding.subjectEdit.text.toString()
        }
        Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                .setAction("戻る") {findNavController().popBackStack()}
                .setActionTextColor(Color.YELLOW)
                .show()
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