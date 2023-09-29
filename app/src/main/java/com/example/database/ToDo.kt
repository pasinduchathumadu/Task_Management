package com.example.database
class ToDo {
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var date: String? = null
    var priority: Int? = null
    var started: Long = 0
    var finished: Long = 0
    var date : String? = null
    var time : String? = null
    var priority : String? = null
    var category : String? = null


    constructor() {}

    constructor(id: Int, title: String?, description: String?, started: Long, finished: Long, date: String?,time: String?,priority: String?, category: String?) {

        this.id = id
        this.title = title
        this.description = description
        this.date = date
        this.priority = priority
        this.started = started
        this.finished = finished
        this.date = date
        this.time = time
        this.priority = priority
        this.category = category
    }

    constructor(title: String?, description: String?, started: Long, finished: Long,date: String?,time: String?,priority: String? ,category: String?) {

        this.title = title
        this.description = description
        this.date = date
        this.priority = priority
        this.started = started
        this.finished = finished
        this.date = date
        this.time = time
        this.priority = priority
        this.category = category

    }
}