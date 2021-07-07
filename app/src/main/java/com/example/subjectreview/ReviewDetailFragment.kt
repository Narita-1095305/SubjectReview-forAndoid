package com.example.subjectreview

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.subjectreview.databinding.FragmentReviewDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.where

class ReviewDetailFragment : Fragment() {
    private val args: ReviewDetailFragmentArgs by navArgs()

    private var _binding: FragmentReviewDetailBinding? = null
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
        _binding = FragmentReviewDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(args.reviewId != -1L){
            val review = realm  .where<Review>()
                .equalTo("id",args.reviewId).findFirst()

            binding.takenYear.text = review?.year.toString()
            binding.teacherName.text = review?.teacher.toString()
            binding.reviewPoint.text = review?.point.toString()
            binding.reviewDetail.text = review?.detail.toString()

            val subject = realm.where<Subject>().equalTo("id",review?.subjectId).findFirst()
            binding.subjectName.text = subject?.title.toString()
            
        }

        binding.deleteBtn.setOnClickListener{
            val dialog = ConfirmDialog("削除しますか?",
                "削除", {deleteReview(it)},
                "キャンセル", {
                    Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT)
                })
            dialog.show(parentFragmentManager,"delete_dialog")
        }
        view.findViewById<Button>(R.id.editBtn).setOnClickListener {
            val action = ReviewDetailFragmentDirections.actionReviewDetailFragmentToReviewEditFragment(-1,args.reviewId)
            findNavController().navigate(action)
        }
    }

    private fun deleteReview(view: View){
        realm.executeTransaction {db: Realm ->
            db.where<Review>().equalTo("id", args.reviewId)
                ?.findFirst()
                ?.deleteFromRealm()
        }
        Snackbar.make(view, "削除しました", Snackbar.LENGTH_SHORT)
            .setActionTextColor(Color.YELLOW)
            .show()

        findNavController().popBackStack()
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