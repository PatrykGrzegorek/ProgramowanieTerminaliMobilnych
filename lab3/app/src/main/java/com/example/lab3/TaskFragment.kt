package com.example.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.lab3.data.Tasks
import com.example.lab3.databinding.FragmentItemListBinding
import com.example.lab3.TaskFragmentDirections
import com.google.android.material.snackbar.Snackbar


class TaskFragment : Fragment(), ToDoListListener,
    DeleteDialogFragment.OnDeleteDialogInteractionListener {
    private lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemListBinding.inflate(inflater, container, false)

        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyTaskRecyclerViewAdapter(Tasks.ITEMS, this@TaskFragment)
        }
        binding.addButton.setOnClickListener { addButtonClick() }
        return binding.root
    }

    private fun addButtonClick() {
        findNavController().navigate(R.id.action_taskFragment_to_addTaskFragment)
    }

    override fun onItemClick(position: Int) {
        val actionTaskFragmentDisplayTaskFragment = TaskFragmentDirections.actionTaskFragmentToDisplayTaskFragment(Tasks.ITEMS.get(position))
        findNavController().navigate(actionTaskFragmentDisplayTaskFragment)
    }

    override fun onItemLongClick(position: Int) {
        showDeleteDialog(position)
    }

    private fun showDeleteDialog(position: Int) {
        val deleteDialog = DeleteDialogFragment.newInstance(Tasks.ITEMS.get(position).title, position, this)
        deleteDialog.show(requireActivity().supportFragmentManager, "DeleteDialog")
    }

    override fun onDialogPositiveClick(pos: Int?) {
        Tasks.ITEMS.removeAt(pos!!)
        Snackbar.make(requireView(), "Task Deleted", Snackbar.LENGTH_LONG).show()
        notifyDataSetChanged()
    }

    private fun notifyDataSetChanged() {
        val rvAdapter = binding.list.adapter
        rvAdapter?.notifyDataSetChanged()
    }

    override fun onDialogNegativeClick(pos: Int?) {
        Snackbar.make(requireView(), "Delete cancelled", Snackbar.LENGTH_LONG)
            .setAction("Redo", View.OnClickListener { showDeleteDialog(pos!!) })
            .show()
    }
}