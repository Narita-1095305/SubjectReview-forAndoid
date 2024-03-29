package com.example.subjectreview

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ConfirmDialog(
    private val message: String,
    private val okLabel: String,
    private val okSelected: () -> Unit,
    private val cancelLabel: String,
    private val cancelSelected: () -> Unit)
    : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(message)
        builder.setPositiveButton(okLabel) { dialog, which ->
            okSelected()
        }
        builder.setNegativeButton(cancelLabel) { dialog, which ->
            cancelSelected()
        }
        return builder.create()
    }
}