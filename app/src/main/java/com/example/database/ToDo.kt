package com.example.database
class ToDo {
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var started: Long = 0
    var finished: Long = 0
    var category : String? = null


    constructor() {}
    constructor(id: Int, title: String?, description: String?, started: Long, finished: Long,category: String?) {
        this.id = id
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
        this.category = category
    }
    constructor(title: String?, description: String?, started: Long, finished: Long,category: String?) {
        this.title = title
        this.description = description
        this.started = started
        this.finished = finished
        this.category = category
    }
}
