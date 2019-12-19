package com.example.cvbuilderapp.model.academic_project

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cvbuilderapp.model.DBHelper
import com.example.cvbuilderapp.model.EducationSQLController

class DBHelperAP(context: Context) {

    lateinit var dbhelper: DBHelper
    lateinit var ourcontext: Context
    lateinit var database: SQLiteDatabase

    init {
        ourcontext = context
    }

    fun open(): DBHelperAP {
        dbhelper = DBHelper(ourcontext)
        database = dbhelper.getWritableDatabase()
        return this
    }

    fun close() {
        dbhelper.close()
    }
    companion object {
        private val DATABASE_VERSION = 1  // Database Version
        private val DATABASE_NAME = "educ_db" // Database Name
    }

    lateinit var allacProjects: ArrayList<AcademicProject>
    fun getAllData(): ArrayList<AcademicProject> {

        val acprojects = ArrayList<AcademicProject>()

        val selectQuery =
            "SELECT  * FROM " + DBHelper.TABLE_NAME_ACADEMIC_PROJECT + " ORDER BY " + DBHelper.COLUMN_ID + " DESC"


        val db = dbhelper.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {

            do {

                val acproject = AcademicProject(
                    cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITTLE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_YEAR)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY))
                )

                acproject.id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))
                acproject.title = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITTLE))
                acproject.school = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL))
                acproject.year = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_YEAR))
                acproject.description =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION))
                acproject.technology =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY))

                acprojects.add(acproject)

            } while (cursor.moveToNext())

        }

        db.close()

        return acprojects

    }


    // return count


    fun insertAcProject(acproject: AcademicProject): Long {

        val db = dbhelper.writableDatabase   // get writable database as we want to write data

        val values = ContentValues()

        values.put(DBHelper.COLUMN_TITTLE, acproject.title)
        values.put(DBHelper.COLUMN_SCHOOL, acproject.school)
        values.put(DBHelper.COLUMN_YEAR, acproject.year)
        values.put(DBHelper.COLUMN_DESCRIPTION, acproject.description)
        values.put(DBHelper.COLUMN_TECHNOLOGY, acproject.technology)

        val id = db.insert(DBHelper.TABLE_NAME_ACADEMIC_PROJECT, null, values)  // insert row

        db.close() // close db connection
        return id  // return newly inserted row id

    }


    fun getAcProject(id: Long): AcademicProject {

        val db = dbhelper.readableDatabase    // get readable database as we are not inserting anything

        val cursor = db.query(
            DBHelper.TABLE_NAME_ACADEMIC_PROJECT,

            arrayOf(
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_TITTLE,
                DBHelper.COLUMN_SCHOOL,
                DBHelper.COLUMN_YEAR,
                DBHelper.COLUMN_DESCRIPTION,
                DBHelper.COLUMN_TECHNOLOGY
            ),
            DBHelper.COLUMN_ID + "=?",

            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )



        cursor?.moveToFirst()

        // prepare acProjects object

        val acproject = AcademicProject(

            cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),

            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITTLE)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SCHOOL)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_YEAR)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY))
        )



        cursor.close()   // close the db connection

        return acproject

    }


    fun updateAcProject(acproject: AcademicProject): Int {

        val db = dbhelper.writableDatabase

        val values = ContentValues()

        values.put(DBHelper.COLUMN_TITTLE, acproject.title)
        values.put(DBHelper.COLUMN_SCHOOL, acproject.school)
        values.put(DBHelper.COLUMN_YEAR, acproject.year)
        values.put(DBHelper.COLUMN_DESCRIPTION, acproject.description)
        values.put(DBHelper.COLUMN_TECHNOLOGY, acproject.technology)

        return db.update(
            DBHelper.TABLE_NAME_ACADEMIC_PROJECT,
            values,
            DBHelper.COLUMN_ID + " = ?",
            arrayOf(acproject.id.toString())
        )   // updating row

    }


    fun deleteAcProject(acproject: AcademicProject): Boolean {

        val db = dbhelper.writableDatabase   // Gets the data repository in write mode

        db.delete(
            DBHelper.TABLE_NAME_ACADEMIC_PROJECT,
            DBHelper.COLUMN_ID + " LIKE ?",
            arrayOf(acproject.id.toString())
        ) // Issue SQL statement.

        return true

    }


    val acprojectsCount: Int
        get() {

            val countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_ACADEMIC_PROJECT

            val db = dbhelper.readableDatabase

            val cursor = db.rawQuery(countQuery, null)

            val count = cursor.count

            cursor.close()

            return count

        }

}