package com.example.home2.data

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

object Contacts {

    val ITEMS: MutableList<ContactItem> = ArrayList()

    fun addContact(item: ContactItem) {
        ITEMS.add(item)
    }

    fun updateContacts(contactToEdit: ContactItem?, contactItem: ContactItem) {
        contactToEdit?.let { oldContact ->

            val indexOfOldContact = ITEMS.indexOf(oldContact)

            ITEMS.add(indexOfOldContact, contactItem)
            ITEMS.removeAt(indexOfOldContact+1)
        }
    }
}

data class ContactItem(val image: String, val name: String, val surname: String, val birthday: String, val number: String) : Parcelable {

    constructor(parcel: Parcel) : this(

        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(image)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(birthday)
        parcel.writeString(number)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ContactItem> {
        override fun createFromParcel(parcel: Parcel): ContactItem {
            return ContactItem(parcel)
        }

        override fun newArray(size: Int): Array<ContactItem?> {
            return arrayOfNulls(size)
        }
    }

}