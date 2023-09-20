package com.example.database
class ToDo {
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var started: Long = 0
    var finished: Long = 0


    constructor() {}
    constructor(id: Int, title: String?, description: String?, started: Long, finished: Long) {
        this.id = id
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
    }
    constructor(title: String?, description: String?, started: Long, finished: Long) {
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
    }
}
