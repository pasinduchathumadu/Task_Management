package com.example.database
class ToDo {
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var date: String? = null
    var priority: Int? = null
    var started: Long = 0
    var finished: Long = 0

    constructor() {}
    constructor(id: Int, title: String?, description: String?,date: String?,priority: Int, started: Long, finished: Long) {
        this.id = id
        this.title = title
        this.description = description
        this.date = date
        this.priority = priority
        this.started = started
        this.finished = finished
    }
    constructor(title: String?, description: String?, date: String?,priority: Int, started: Long, finished: Long) {
        this.title = title
        this.description = description
        this.date = date
        this.priority = priority
        this.started = started
        this.finished = finished
    }
}
