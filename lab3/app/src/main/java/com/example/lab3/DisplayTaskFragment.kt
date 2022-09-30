package com.example.lab3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab3.data.IMPORTANCE
import com.example.lab3.data.TaskItem
import com.example.lab3.databinding.FragmentAddTaskBinding
import com.example.lab3.databinding.FragmentDisplayTaskBinding
import com.example.lab3.DisplayTaskFragmentArgs
import com.example.lab3.DisplayTaskFragmentDirections


/**
 * A simple [Fragment] subclass.
 * Use the [DisplayTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DisplayTaskFragment : Fragment() {
    val args: DisplayTaskFragmentArgs by navArgs()
    private lateinit var binding: FragmentDisplayTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDisplayTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task: TaskItem = args.task
        binding.displayTitle.text = task.title
        binding.displayDescritpion.text = task.description

        val resource = when(task.importance) {
            IMPORTANCE.LOW -> R.drawable.circle_drawable_green
            IMPORTANCE.NORMAL -> R.drawable.circle_drawable_orange
            IMPORTANCE.HIGH -> R.drawable.circle_drawable_red
        }
        binding.displayImportance.setImageResource(resource)

        binding.displayEdit.setOnClickListener {
            val taskToEdit =
                DisplayTaskFragmentDirections.actionDisplayTaskFragmentToAddTaskFragment(
                    taskToEdit = task,
                    edit = true)
            findNavController().navigate(taskToEdit)
        }
    }
}