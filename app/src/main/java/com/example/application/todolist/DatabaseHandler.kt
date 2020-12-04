package com.example.application.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, ToDoList_Database, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val ToDoList_Database = "ToDoList_Database"
        private val ToDoList_Table = "ToDoList_Table"
        private val id = "id"
        private val value = "value"
        private val tick_value = "tick_value"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE  $ToDoList_Table ($id INTEGER PRIMARY KEY AUTOINCREMENT, $value TEXT, $tick_value TEXT)")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS $ToDoList_Table")
        onCreate(db)
    }


    //    method to insert data
    fun addUsers(a: User): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(value, a.value)
        contentValues.put(tick_value, a.tick_value)
        // Inserting Row
        val success = db.insert(ToDoList_Table, null, contentValues)//2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun getUsers(): ArrayList<User> {
        val userList: ArrayList<User> = ArrayList<User>()
        val selectQuery = "SELECT  * FROM $ToDoList_Table"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            return ArrayList()
        }

        while (cursor.moveToNext()) {
            val emp = User(cursor.getString(0), cursor.getString(1), cursor.getString(2))
            userList.add(emp)
        }
        return userList
    }

    //method to update data
    fun updateValues(a: User): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(value, a.value) // EmpModelClass Name

        // Updating Row
        val success = db.update(ToDoList_Table, contentValues, "id=" + a.id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to delete data
    fun deleteValues(a: User): Int {
        val db = this.writableDatabase
        // Deleting Row
        val success = db.delete(ToDoList_Table, "id=" + a.id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }
}
