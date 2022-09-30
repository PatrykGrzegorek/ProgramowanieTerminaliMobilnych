package com.example.lab3

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TASK_NAME_PARAM = "taks name"
private const val TASK_POS_PARAM = "task pos"

/**
 * A simple [Fragment] subclass.
 * Use the [DeleteDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeleteDialogFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    lateinit var mListener: OnDeleteDialogInteractionListener

    private var taskNameParam: String? = null
    private var taskPosParam: Int? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            taskNameParam = it.getString(TASK_NAME_PARAM)
            taskPosParam = it.getInt(TASK_POS_PARAM)
        }
    }

    interface OnDeleteDialogInteractionListener {
        fun onDialogPositiveClick(pos: Int?)
        fun onDialogNegativeClick(pos: Int?)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Delete this entry?" + "$taskNameParam")
        builder.setPositiveButton("Confirm", DialogInterface.OnClickListener{ dialogInterface, i ->
            mListener?.onDialogPositiveClick(taskPosParam)
        })
        builder.setNegativeButton("Discard", DialogInterface.OnClickListener{ dialogInterface, i ->
            mListener?.onDialogNegativeClick(taskPosParam)
        })
        return builder.create()
    }

    companion object {
        @JvmStatic
        fun newInstance(name: String, pos: Int, interactionListener: OnDeleteDialogInteractionListener) =
            DeleteDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(TASK_NAME_PARAM, name)
                    putInt(TASK_POS_PARAM, pos)
                }
                mListener = interactionListener
            }
    }
}