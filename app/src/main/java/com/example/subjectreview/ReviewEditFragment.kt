package com.example.subjectreview

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.subjectreview.databinding.FragmentReviewEditBinding
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [ReviewEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReviewEditFragment : Fragment() {
    private val args: ReviewEditFragmentArgs by navArgs()
    private var _binding: FragmentReviewEditBinding? = null
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
        _binding = FragmentReviewEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(args.reviewId != -1L){
            val review = realm.where<Review>()
                .equalTo("id", args.reviewId).findFirst()

            binding.yearEdit.setText(review?.year.toString())
            binding.teacherEdit.setText(review?.teacher)
            binding.pointEdit.setText(review?.point.toString())
            binding.detailEdit.setText(review?.detail)
        }
        binding.saveBtn.setOnClickListener{
            val dialog = ConfirmDialog("保存しますか?",
            "保存", {saveReview(it)},
                "キャンセル", {
                    Snackbar.make(it, "キャンセルしました", Snackbar.LENGTH_SHORT)
                })
            dialog.show(parentFragmentManager,"save_dialog")
        }
    }

    private fun saveReview(view: View) {
        when (args.reviewId)
        { -1L -> {
            realm.executeTransaction{ db: Realm ->
                val maxId = db.where<Review>().max("id")
                val nextId = (maxId?.toLong() ?: 0L) + 1L
                val review = db.createObject<Review>(nextId)
                review.subjectId = args.subjectId
                review.year = Integer.parseInt(binding.yearEdit.text.toString())
                review.teacher = binding.teacherEdit.text.toString()
                review.point = Integer.parseInt(binding.pointEdit.text.toString())
                review.detail = binding.detailEdit.text.toString()
            }
            Snackbar.make(view, "追加しました", Snackbar.LENGTH_SHORT)
                .setAction("戻る") {findNavController().popBackStack()}
                .setActionTextColor(Color.YELLOW)
                .show()
        }
            else ->{
                realm.executeTransaction{ db: Realm ->
                    val review = db.where<Review>()
                        .equalTo("id", args.reviewId).findFirst()
                    review?.year = Integer.parseInt(binding.yearEdit.text.toString())
                    review?.teacher = binding.teacherEdit.text.toString()
                    review?.point = Integer.parseInt(binding.pointEdit.text.toString())
                    review?.detail = binding.detailEdit.text.toString()
                }
                Snackbar.make(view, "修正しました", Snackbar.LENGTH_SHORT)
                    .setAction("戻る") {findNavController().popBackStack()}
                    .setActionTextColor(Color.YELLOW)
                    .show()
            }
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