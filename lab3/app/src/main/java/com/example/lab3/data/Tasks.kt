package com.example.lab3.data

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object Tasks {

    /**
     * An array of sample (placeholder) items.
     */
    val ITEMS: MutableList<TaskItem> = ArrayList()

    /**
     * A map of sample (placeholder) items, by ID.
     */
    //val ITEM_MAP: MutableMap<String, PlaceholderItem> = HashMap()

    private val COUNT = 10

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addTask(createTaskItem(i))
        }
    }

    fun addTask(item: TaskItem) {
        ITEMS.add(item)
        //ITEM_MAP.put(item.id, item)
    }

    private fun createTaskItem(position: Int): TaskItem {
        return TaskItem(position.toString(), "Item " + position, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    fun updateTask(taskToEdit: TaskItem?, newTask: TaskItem) {
        taskToEdit?.let { oldTask ->
            val indexOfOldTask = ITEMS.indexOf(oldTask)
            ITEMS.add(indexOfOldTask, newTask)
            ITEMS.removeAt(indexOfOldTask+1)
        }
    }

}

data class TaskItem(val id: String, val title: String, val description: String, val importance: IMPORTANCE = IMPORTANCE.NORMAL) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        IMPORTANCE.values()[parcel.readInt()]
    ) {
    }

    override fun toString(): String = title
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskItem> {
        override fun createFromParcel(parcel: Parcel): TaskItem {
            return TaskItem(parcel)
        }

        override fun newArray(size: Int): Array<TaskItem?> {
            return arrayOfNulls(size)
        }
    }
}

enum class IMPORTANCE {
    LOW, NORMAL, HIGH
}