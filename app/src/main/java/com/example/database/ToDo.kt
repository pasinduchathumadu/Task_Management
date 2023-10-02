package com.example.database
import android.os.Parcel
import android.os.Parcelable

class ToDo : Parcelable {
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var started: Long = 0
    var finished: Long = 0
    var date: String? = null
    var time: String? = null
    var priority: String? = null
    var category: String? = null

    constructor() {}

    constructor(
        id: Int,
        title: String?,
        description: String?,
        started: Long,
        finished: Long,
        date: String?,
        time: String?,
        priority: String?,
        category: String?
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
        this.date = date
        this.time = time
        this.priority = priority
        this.category = category
    }

    constructor(
        title: String?,
        description: String?,
        started: Long,
        finished: Long,
        date: String?,
        time: String?,
        priority: String?,
        category: String?
    ) {
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
        this.date = date
        this.time = time
        this.priority = priority
        this.category = category
    }

    // Parcelable implementation
    constructor(parcel: Parcel) {
        id = parcel.readInt()
        title = parcel.readString()
        description = parcel.readString()
        started = parcel.readLong()
        finished = parcel.readLong()
        date = parcel.readString()
        time = parcel.readString()
        priority = parcel.readString()
        category = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeLong(started)
        parcel.writeLong(finished)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(priority)
        parcel.writeString(category)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ToDo> {
        override fun createFromParcel(parcel: Parcel): ToDo {
            return ToDo(parcel)
        }

        override fun newArray(size: Int): Array<ToDo?> {
            return arrayOfNulls(size)
        }
    }
}


//class ToDo {
//    var id: Int = 0
//    var title: String? = null
//    var description: String? = null
//    var started: Long = 0
//    var finished: Long = 0
//    var date : String? = null
//    var time : String? = null
//    var priority : String? = null
//    var category : String? = null
//
//
//    constructor() {}
//    constructor(id: Int, title: String?, description: String?, started: Long, finished: Long, date: String?,time: String?,priority: String?, category: String?) {
//        this.id = id
//        this.title = title
//        this.description = description
//        this.started = started
//        this.finished = finished
//        this.date = date
//        this.time = time
//        this.priority = priority
//        this.category = category
//    }
//    constructor(title: String?, description: String?, started: Long, finished: Long,date: String?,time: String?,priority: String? ,category: String?) {
//        this.title = title
//        this.description = description
//        this.started = started
//        this.finished = finished
//        this.date = date
//        this.time = time
//        this.priority = priority
//        this.category = category
//
//    }
//}