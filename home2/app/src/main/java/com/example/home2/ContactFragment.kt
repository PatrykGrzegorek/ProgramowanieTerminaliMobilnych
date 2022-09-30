package com.example.home2

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.home2.data.Contacts
import com.example.home2.databinding.FragmentItemListBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A fragment representing a list of Items.
 */
class ContactFragment : Fragment(), MyContactListListener, LongClickFragment.LongClickDialogInteractionListener {

    private lateinit var binding: FragmentItemListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        binding.addButton.setOnClickListener { addButtonClick()}

        with(binding.list)
        {
            layoutManager = LinearLayoutManager(context)
            adapter = MyContactDeleteViewAdapter(activity, Contacts.ITEMS, this@ContactFragment)
        }

        return binding.root
    }

    private fun addButtonClick() {

        findNavController().navigate(R.id.action_contactFragment2_to_addContactFragment)
    }

    override fun onItemClick(position: Int) {
        val actionContactToDisplayContactFragment =
            ContactFragmentDirections.actionContactFragment2ToDisplayContactFragment(Contacts.ITEMS.get(position))
        findNavController().navigate(actionContactToDisplayContactFragment)
    }

    override fun onItemLongClick(position: Int) {
        showLongClickDialog(position)
    }

    private fun showLongClickDialog(position: Int){
        val onLongDialog = LongClickFragment.newInstance(Contacts.ITEMS.get(position).name + " " + Contacts.ITEMS.get(position).surname, position, this)

        onLongDialog.show(requireActivity().supportFragmentManager, "LongClick")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Snackbar.make(binding.root, "Calling...", Snackbar.LENGTH_LONG).show()
    }

    override fun onDialogNegativeClick(pos: Int?) {

    }

}