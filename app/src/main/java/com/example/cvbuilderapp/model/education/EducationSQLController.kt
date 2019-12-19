package com.example.cvbuilderapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.cvbuilderapp.model.education.Education
import com.example.cvbuilderapp.utils.Utils
import java.lang.Exception

class EducationSQLController(c: Context) // Constructor to set the context
{
    lateinit var dbhelper: DBHelper
    lateinit var ourcontext: Context
    lateinit var database: SQLiteDatabase

    init {
        ourcontext = c
    }

    fun open(): EducationSQLController {
        dbhelper = DBHelper(ourcontext)
        database = dbhelper.getWritableDatabase()
        return this
    }

    fun close() {
        dbhelper.close()
    }


    // 1) Select All Query, 2)looping through all rows and adding to list 3) close db connection, 4) return education list
    lateinit var allEducations: ArrayList<Education>

    fun getAllData(): ArrayList<Education> {
        val notes = ArrayList<Education>()
        try {
            val selectQuery =
                "SELECT * FROM " + DBHelper.TABLE_NAME_EDUCATION + " ORDER BY " + DBHelper.COLUMN_ID + " DESC"
            val db = dbhelper.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val education = Education(
                        cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DEGREE)),
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MAJOR))
                    )
                    education.id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))
                    education.school =
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL))
                    education.degree =
                        cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DEGREE))
                    education.major = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MAJOR))
                    notes.add(education)
                } while (cursor.moveToNext())
            }
            db.close()
        } catch (e: Exception) {
            Utils.showMessage(ourcontext, e.toString())
        }
        return notes
    }

    // return count
    val notesCount: Int
        get() {
            val countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_EDUCATION
            val db = dbhelper.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            val count = cursor.count
            cursor.close()
            return count
        }


    fun insertEducation(education: Education): Long {
        var id: Long = 0
        try {
            val db = dbhelper.writableDatabase   // get writable database as we want to write data
            val values = ContentValues()
            values.put(DBHelper.COLUMN_SCHOOL, education.school)
            values.put(DBHelper.COLUMN_DEGREE, education.degree)
            values.put(DBHelper.COLUMN_MAJOR, education.major)
            id = db.insert(DBHelper.TABLE_NAME_EDUCATION, null, values)  // insert row
            db.close() // close db connection
        } catch (e: Exception) {
            Utils.showMessage(ourcontext, e.toString())
        }
        return id  // return newly inserted row id
    }

    fun getEducation(id: Long): Education {
        val db =
            dbhelper.readableDatabase    // get readable database as we are not inserting anything
        val cursor = db.query(
            DBHelper.TABLE_NAME_EDUCATION,
            arrayOf(
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_SCHOOL,
                DBHelper.COLUMN_DEGREE,
                DBHelper.COLUMN_MAJOR
            ),
            DBHelper.COLUMN_ID + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        // prepare todos object
        val education = Education(
            cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DEGREE)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_MAJOR))
        )
        cursor.close()   // close the db connection
        return education
    }

    fun updateEducation(todo: Education): Int {
        val db = dbhelper.writableDatabase
        val values = ContentValues()
        values.put(DBHelper.COLUMN_SCHOOL, todo.school)
        values.put(DBHelper.COLUMN_DEGREE, todo.degree)
        return db.update(
            DBHelper.TABLE_NAME_CONTACT,
            values,
            DBHelper.COLUMN_ID + " = ?",
            arrayOf(todo.id.toString())
        )   // updating row
    }

    fun deleteTodo(todo: Education): Boolean {
        val db = dbhelper.writableDatabase   // Gets the data repository in write mode
        db.delete(
            DBHelper.TABLE_NAME_EDUCATION,
            DBHelper.COLUMN_ID + " LIKE ?",
            arrayOf(todo.id.toString())
        ) // Issue SQL statement.
        return true
    }
}