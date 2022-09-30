package com.example.home2

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CONTACT_NAME_PARAM = "contact name"
private const val CONTACT_POS_PARAM = "contact pos"


class DeleteDialogFragment : DialogFragment() {

    lateinit var mListener: OnDeleteDialogInteractionListener

    private var contactNameParam: String? = null
    private var contactPosParam: Int? = null

    interface OnDeleteDialogInteractionListener{
        fun onDialogPositiveClick(pos: Int?)
        fun onDialogNegativeClick(pos: Int?)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contactNameParam = it.getString(CONTACT_NAME_PARAM)
            contactPosParam = it.getInt(CONTACT_POS_PARAM)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Delete this entry?" + " $contactNameParam")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener{ dialogInterface, i ->
            mListener?.onDialogPositiveClick(contactPosParam)
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface, i ->
            mListener?.onDialogNegativeClick(contactPosParam)
        })
        return builder.create()
    }



    companion object {

        @JvmStatic
        fun newInstance(name: String, pos: Int, interactionListener: OnDeleteDialogInteractionListener) =
            DeleteDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTACT_NAME_PARAM, name)
                    putInt(CONTACT_POS_PARAM, pos)
                }
                mListener = interactionListener
            }
    }
}