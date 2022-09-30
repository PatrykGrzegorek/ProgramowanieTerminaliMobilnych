package com.example.home2

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.home2.data.ContactItem
import com.example.home2.data.Contacts
import com.example.home2.databinding.FragmentAddContactBinding
import com.google.android.material.snackbar.Snackbar

class AddContactFragment : Fragment() {

    val args: AddContactFragmentArgs by navArgs()
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveButton.setOnClickListener{ saveContact() }
        binding.addInputName.setText(args.contactToEdit?.name)
        binding.addInputSurname.setText(args.contactToEdit?.surname)
        binding.addInputBirthday.setText(args.contactToEdit?.birthday)
        binding.addInputNumber.setText(args.contactToEdit?.number)

        if(args.contactToEdit != null){
            if(args.contactToEdit?.image?.toInt() == 0)
            {
                binding.image.setImageResource(R.drawable.avatar_1)
            }
            else
            {
                binding.image.setImageResource(R.drawable.avatar_1 + args.contactToEdit?.image?.toInt()!! - 1)
            }
        }
        else
        {
            binding.image.setImageResource(R.drawable.avatar_1 + (Contacts.ITEMS.size)%16)
        }


    }

    @SuppressLint("NewApi")
    private fun saveContact() {
        var name: String = binding.addInputName.text.toString()
        var surname: String = binding.addInputSurname.text.toString()
        var birthday: String = binding.addInputBirthday.text.toString()
        var number: String = binding.addInputNumber.text.toString()

        if(name.isEmpty()){
            Snackbar.make(binding.root, "Wrong Name", Snackbar.LENGTH_LONG).show()
            return
        }
        if(surname.isEmpty()){
            Snackbar.make(binding.root, "Wrong Surname", Snackbar.LENGTH_LONG).show()
            return
        }
        if(!birthday.isEmpty()){
            val delim = "-"
            val list = birthday.split(delim)
            if(list.size != 3){
                Snackbar.make(binding.root, "Wrong format, try X-X-XXXX", Snackbar.LENGTH_LONG).show()
                return
            }
            if(list[0].toInt() < 1 || list[0].toInt() > 31){
                Snackbar.make(binding.root, "Wrong birthday", Snackbar.LENGTH_LONG).show()
                return
            }
            if(list[1].toInt() < 1 || list[1].toInt() > 12){
                Snackbar.make(binding.root, "Wrong birthday", Snackbar.LENGTH_LONG).show()
                return
            }
            val calendar: Calendar = Calendar.getInstance()
            val year: Int = calendar.get(Calendar.YEAR)
            if(list[2].toInt() < 1850 || list[2].toInt() > year){
                Snackbar.make(binding.root, "Wrong birthday", Snackbar.LENGTH_LONG).show()
                return
            }
        } else {
            Snackbar.make(binding.root, "Wrong birthday", Snackbar.LENGTH_LONG).show()
            return
        }
        if(number.isEmpty() || number.length != 9){
            Snackbar.make(binding.root, "Wrong number", Snackbar.LENGTH_LONG).show()
            return
        }

        val contactItem = ContactItem(
            ((Contacts.ITEMS.size + 1)%16).toString(),
            name,
            surname,
            birthday,
            number
        )

        if(!args.edit)
        {
            Contacts.addContact(contactItem)

        }
        else
        {
            Contacts.updateContacts(args.contactToEdit, contactItem)
        }

        val inputMethodManager: InputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)

        findNavController().popBackStack(R.id.contactFragment2, false)

    }


}