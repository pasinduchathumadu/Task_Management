
package com.example.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    //Store These Data
    companion object {

        private const val VERSION = 1 
        private const val DB_NAME = "todo"
        private const val TABLE_NAME = "todo"
        private const val USER_TABLE = "users"
    }
    //create object
    private object Columns {
        const val ID = "id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val DATE = "date"
        const val PRIORITY = "priority"
        const val STARTED = "started"
        const val FINISHED = "finished"
        const val CATEGORY = "category"
    }
    private object UserColumns {
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
    //Create Table
    override fun onCreate(db: SQLiteDatabase) {
        //Build SQL-QUERY
        val TABLE_CREATE_QUERY = "CREATE TABLE $TABLE_NAME " +
                "(" +
                "${Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${Columns.TITLE} TEXT," +
                "${Columns.DESCRIPTION} TEXT," +
                "${Columns.DATE} TEXT," +
                "${Columns.PRIORITY} TEXT," +
                "${Columns.STARTED} TEXT," +
                "${Columns.FINISHED} TEXT," +
                "${Columns.CATEGORY} TEXT" +
                ");"
        db.execSQL(TABLE_CREATE_QUERY)
        val USER_TABLE_CREATE_QUERY = "CREATE TABLE $USER_TABLE " +
                "(" +
                "${UserColumns.EMAIL} TEXT PRIMARY KEY," +
                "${UserColumns.PASSWORD} TEXT" +
                ");"
        db.execSQL(USER_TABLE_CREATE_QUERY)
    }
    //Drop and Create New Table once again
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE_QUERY)
        //Calling This Function Once again
        onCreate(db)
    }
    //This is added data into the table
    fun addToDo(toDo: ToDo) {
        val sqLiteDatabase = writableDatabase
        //write statement in the database
        val contentValues = ContentValues()
        //put keyword to store data to object
        contentValues.put(Columns.TITLE, toDo.title)
        contentValues.put(Columns.DESCRIPTION, toDo.description)
        contentValues.put(Columns.DATE, toDo.date)
        contentValues.put(Columns.PRIORITY, toDo.priority)
        contentValues.put(Columns.STARTED, toDo.started)
        contentValues.put(Columns.FINISHED, toDo.finished)
        contentValues.put(Columns.CATEGORY,toDo.category)
        //insert the query for
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues)
        sqLiteDatabase.close()
    }
    // return type in function integer
    fun countToDo(): Int {
        // read operation
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        //count method in cursor
        return cursor.count
    }
    // return type is List
    fun search(input: String): List<ToDo> {
        val toDos = mutableListOf<ToDo>()
        val db = readableDatabase

        // Use placeholders and selectionArgs to avoid SQL injection
        val query = "SELECT * FROM $TABLE_NAME WHERE ${Columns.TITLE} = ?"
        val selectionArgs = arrayOf(input)

        val cursor = db.rawQuery(query, selectionArgs)

        if (cursor.moveToFirst()) {
            do {
                // Create a ToDo object and populate it from the cursor
                val toDo = ToDo()
                val idIndex = cursor.getColumnIndex(Columns.ID)
                val titleIndex = cursor.getColumnIndex(Columns.TITLE)
                val descriptionIndex = cursor.getColumnIndex(Columns.DESCRIPTION)
                val startIndex = cursor.getColumnIndex(Columns.STARTED)
                val finishIndex = cursor.getColumnIndex(Columns.FINISHED)
                val categoryIndex = cursor.getColumnIndex(Columns.CATEGORY)
                if(descriptionIndex != -1){
                    toDo.description = cursor.getString(descriptionIndex)
                }
                if(startIndex!=-1){
                    toDo.started = cursor.getLong(startIndex)
                }
                if(finishIndex !=-1){
                    toDo.finished = cursor.getLong(finishIndex)
                }
                if (idIndex != -1) {
                    toDo.id = cursor.getInt(idIndex)
                }

                if (titleIndex != -1) {
                    toDo.title = cursor.getString(titleIndex)
                }
                if(categoryIndex != -1){
                    toDo.category = cursor.getString(categoryIndex)
                }
                // Add the ToDo object to the list
                toDos.add(toDo)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return toDos
    }

    fun getAllToDos(): List<ToDo> {
        // initialize the List
        val toDos = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        //using do-while loop to get the row by row
        if (cursor.moveToFirst()) {
            do {
                //creating todo object
                val toDo = ToDo()
                val idIndex = cursor.getColumnIndex(Columns.ID)
                val titleIndex = cursor.getColumnIndex(Columns.TITLE)
                val descriptionIndex = cursor.getColumnIndex(Columns.DESCRIPTION)
                val dateIndex = cursor.getColumnIndex(Columns.DATE)
                val priorityIndex = cursor.getColumnIndex(Columns.PRIORITY)
                val startIndex = cursor.getColumnIndex(Columns.STARTED)
                val finishIndex = cursor.getColumnIndex(Columns.FINISHED)
                val categoryIndex = cursor.getColumnIndex(Columns.CATEGORY)
                if(descriptionIndex != -1){
                    toDo.description = cursor.getString(descriptionIndex)
                }
                if(startIndex!=-1){
                    toDo.started = cursor.getLong(startIndex)
                }
                if(finishIndex !=-1){
                    toDo.finished = cursor.getLong(finishIndex)
                }
                if (idIndex != -1) {
                    toDo.id = cursor.getInt(idIndex)
                }

                if (titleIndex != -1) {
                    toDo.title = cursor.getString(titleIndex)
                }
                if(categoryIndex != -1){
                    toDo.category = cursor.getString(categoryIndex)
                }
                //add into the list
                toDos.add(toDo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return toDos
    }
    fun getPersonalToDos(): List<ToDo> {
        // initialize the List
        val toDos = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE ${Columns.CATEGORY} = 'Personal'"
        val cursor = db.rawQuery(query, null)
        //using do-while loop to get the row by row
        if (cursor.moveToFirst()) {
            do {
                //creating todo object
                val toDo = ToDo()
                val idIndex = cursor.getColumnIndex(Columns.ID)
                val titleIndex = cursor.getColumnIndex(Columns.TITLE)
                val descriptionIndex = cursor.getColumnIndex(Columns.DESCRIPTION)
                val startIndex = cursor.getColumnIndex(Columns.STARTED)
                val finishIndex = cursor.getColumnIndex(Columns.FINISHED)
                val categoryIndex = cursor.getColumnIndex(Columns.CATEGORY)
                if(descriptionIndex != -1){
                    toDo.description = cursor.getString(descriptionIndex)
                }
                if(startIndex!=-1){
                    toDo.started = cursor.getLong(startIndex)
                }
                if(finishIndex !=-1){
                    toDo.finished = cursor.getLong(finishIndex)
                }
                if (idIndex != -1) {
                    toDo.id = cursor.getInt(idIndex)
                }

                if (titleIndex != -1) {
                    toDo.title = cursor.getString(titleIndex)
                }
                if(categoryIndex != -1){
                    toDo.category = cursor.getString(categoryIndex)
                }
                //add into the list
                toDos.add(toDo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return toDos
    }

    fun getAccedemicToDos(): List<ToDo> {
        // initialize the List
        val toDos = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE ${Columns.CATEGORY} = 'Academic'"
        val cursor = db.rawQuery(query, null)
        //using do-while loop to get the row by row
        if (cursor.moveToFirst()) {
            do {
                //creating todo object
                val toDo = ToDo()
                val idIndex = cursor.getColumnIndex(Columns.ID)
                val titleIndex = cursor.getColumnIndex(Columns.TITLE)
                val descriptionIndex = cursor.getColumnIndex(Columns.DESCRIPTION)
                val startIndex = cursor.getColumnIndex(Columns.STARTED)
                val finishIndex = cursor.getColumnIndex(Columns.FINISHED)
                val categoryIndex = cursor.getColumnIndex(Columns.CATEGORY)
                if(descriptionIndex != -1){
                    toDo.description = cursor.getString(descriptionIndex)
                }
                if(startIndex!=-1){
                    toDo.started = cursor.getLong(startIndex)
                }
                if(finishIndex !=-1){
                    toDo.finished = cursor.getLong(finishIndex)
                }
                if (idIndex != -1) {
                    toDo.id = cursor.getInt(idIndex)
                }

                if (titleIndex != -1) {
                    toDo.title = cursor.getString(titleIndex)
                }
                if(categoryIndex != -1){
                    toDo.category = cursor.getString(categoryIndex)
                }
                //add into the list
                toDos.add(toDo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return toDos
    }

    fun getOtherToDos(): List<ToDo> {
        // initialize the List
        val toDos = mutableListOf<ToDo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE ${Columns.CATEGORY} = 'Other'"
        val cursor = db.rawQuery(query, null)
        //using do-while loop to get the row by row
        if (cursor.moveToFirst()) {
            do {
                //creating todo object
                val toDo = ToDo()
                val idIndex = cursor.getColumnIndex(Columns.ID)
                val titleIndex = cursor.getColumnIndex(Columns.TITLE)
                val descriptionIndex = cursor.getColumnIndex(Columns.DESCRIPTION)
                val startIndex = cursor.getColumnIndex(Columns.STARTED)
                val finishIndex = cursor.getColumnIndex(Columns.FINISHED)
                val categoryIndex = cursor.getColumnIndex(Columns.CATEGORY)
                if(descriptionIndex != -1){
                    toDo.description = cursor.getString(descriptionIndex)
                }
                if(dateIndex != -1){
                    toDo.date = cursor.getString(dateIndex)
                }
                if(priorityIndex != -1){
                    toDo.priority = cursor.getInt(priorityIndex)
                }
                if(startIndex!=-1){
                    toDo.started = cursor.getLong(startIndex)
                }
                if(finishIndex !=-1){
                    toDo.finished = cursor.getLong(finishIndex)
                }
                if (idIndex != -1) {
                    toDo.id = cursor.getInt(idIndex)
                }

                if (titleIndex != -1) {
                    toDo.title = cursor.getString(titleIndex)
                }
                if(categoryIndex != -1){
                    toDo.category = cursor.getString(categoryIndex)
                }
                //add into the list
                toDos.add(toDo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return toDos
    }


    fun addUser(userData: UserData): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(UserColumns.EMAIL, userData.email)
        contentValues.put(UserColumns.PASSWORD, userData.password)

        // Check if the email already exists in the users table
        val query = "SELECT * FROM $USER_TABLE WHERE ${UserColumns.EMAIL} = ?"
        val cursor = db.rawQuery(query, arrayOf(userData.email))

        if (cursor.count > 0) {
            // The email already exists, return false
            cursor.close()
            db.close()
            return false
        }

        // The email is unique, proceed with insertion
        val newRowId = db.insert(USER_TABLE, null, contentValues)
        cursor.close()
        db.close()

        // Return true if insertion was successful, i.e., newRowId is not equal to -1L
        return newRowId != -1L
    }
    // authentication function
    fun authenticateUser(email: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $USER_TABLE WHERE ${UserColumns.EMAIL} = ? AND ${UserColumns.PASSWORD} = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val result = cursor.count > 0

        cursor.close()
        db.close()

        return result
    }
    // delete task
    fun deleteToDo(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "id =?", arrayOf(id.toString()))
        db.close()
    }

    fun getSingleToDo(id: Int): ToDo? {
        val db = writableDatabase

        val cursor = db.query(
            TABLE_NAME,

            arrayOf(Columns.ID, Columns.TITLE, Columns.DESCRIPTION, Columns.STARTED, Columns.FINISHED,Columns.CATEGORY),

            "${Columns.ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var toDo: ToDo? = null
        cursor?.use {
            if (it.moveToFirst()) {
                toDo = ToDo(
                    it.getInt(0),
                    it.getString(1),
                    it.getString(2),
                    it.getLong(3),
                    it.getLong(4),
                    it.getString(5)

                )
            }
        }
        return toDo
    }

    fun updateSingleToDo(toDo: ToDo): Int {
        val db = writableDatabase

        val contentValues = ContentValues()
        contentValues.put(Columns.TITLE, toDo.title)
        contentValues.put(Columns.DESCRIPTION, toDo.description)
        contentValues.put(Columns.STARTED, toDo.started)
        contentValues.put(Columns.FINISHED, toDo.finished)

        val status = db.update(
            TABLE_NAME, contentValues,
            "${Columns.ID} =?",
            arrayOf(toDo.id.toString())
        )

        db.close()
        return status
    }
}