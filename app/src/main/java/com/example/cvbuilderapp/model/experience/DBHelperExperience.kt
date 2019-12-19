package com.example.cvbuilderapp.model.experience

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.customersqliteapp.Experience
import com.example.cvbuilderapp.model.DBHelper
import com.example.cvbuilderapp.model.academic_project.DBHelperAP
import java.lang.Exception

class DBHelperExperience(context: Context) {

    lateinit var dbhelper: DBHelper
    lateinit var ourcontext: Context
    lateinit var database: SQLiteDatabase

    init {
        ourcontext = context
    }

    fun open(): DBHelperExperience {
        dbhelper = DBHelper(ourcontext)
        database = dbhelper.getWritableDatabase()
        return this
    }

    fun close() {
        dbhelper.close()
    }


    // 1) Select All Query, 2)looping through all rows and adding to list 3) close db connection, 4) return education list

    lateinit var allExperiences: ArrayList<Experience>

    fun getAllData(): ArrayList<Experience> {

        var experiences = ArrayList<Experience>()
        val selectQuery =
            "SELECT  * FROM " + DBHelper.TABLE_NAME_EXPERIENCE + " ORDER BY " + DBHelper.COLUMN_ID + " DESC"

        try {
            val db = dbhelper.writableDatabase
            open()
            val cursor = db.rawQuery(selectQuery, null)
            while (cursor.moveToNext()) {
                val experience = Experience(
                    cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_TITLE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_START_DATE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_END_DATE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TASKS)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ACHIEVEMENTS)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY_USED))
                )
                experiences.add(experience)
            }
            cursor.close();
        } finally {
            close()
        }
        /*

        if (cursor.moveToFirst()) {

            do {
                val experience = Experience(
                    cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_TITLE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_START_DATE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_END_DATE)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TASKS)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ACHIEVEMENTS)),
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY_USED))
                )

                experience.id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))
                experience.companyName =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME))
                experience.companyDescription =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_DESCRIPTION))
                experience.jobTitle =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_TITLE))
                experience.jobDescription =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_DESCRIPTION))
                experience.startDate =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_START_DATE))
                experience.endDate =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_END_DATE))
                experience.tasks = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TASKS))
                experience.achievements =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ACHIEVEMENTS))
                experience.technologiesUsed =
                    cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY_USED))

                experiences.add(experience)

            } while (cursor.moveToNext())

        }
        db.close()
*/
        return experiences

    }


    fun insertExperience(experience: Experience): Long {

        val db = dbhelper.writableDatabase   // get writable database as we want to write data

        val values = ContentValues()

        values.put(DBHelper.COLUMN_COMPANY_NAME, experience.companyName)
        values.put(DBHelper.COLUMN_COMPANY_DESCRIPTION, experience.companyDescription)
        values.put(DBHelper.COLUMN_JOB_TITLE, experience.jobTitle)
        values.put(DBHelper.COLUMN_JOB_DESCRIPTION, experience.jobDescription)
        values.put(DBHelper.COLUMN_START_DATE, experience.startDate)
        values.put(DBHelper.COLUMN_END_DATE, experience.endDate)
        values.put(DBHelper.COLUMN_TASKS, experience.tasks)
        values.put(DBHelper.COLUMN_ACHIEVEMENTS, experience.achievements)
        values.put(DBHelper.COLUMN_TECHNOLOGY_USED, experience.technologiesUsed)

        val id = db.insert(DBHelper.TABLE_NAME_EXPERIENCE, null, values)  // insert row

        db.close() // close db connection
        return id  // return newly inserted row id

    }


    fun getExperience(id: Long): Experience {

        val db =
            dbhelper.readableDatabase    // get readable database as we are not inserting anything

        val cursor = db.query(
            DBHelper.TABLE_NAME_EXPERIENCE,

            arrayOf(
                DBHelper.COLUMN_ID,
                DBHelper.COLUMN_COMPANY_NAME,
                DBHelper.COLUMN_COMPANY_DESCRIPTION,
                DBHelper.COLUMN_JOB_TITLE,
                DBHelper.COLUMN_JOB_DESCRIPTION,
                DBHelper.COLUMN_START_DATE,
                DBHelper.COLUMN_END_DATE,
                DBHelper.COLUMN_TASKS,
                DBHelper.COLUMN_ACHIEVEMENTS,
                DBHelper.COLUMN_TECHNOLOGY_USED
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

        val experience = Experience(

            cursor!!.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)),

            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_NAME)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COMPANY_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_TITLE)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_JOB_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_START_DATE)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_END_DATE)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TASKS)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ACHIEVEMENTS)),
            cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TECHNOLOGY_USED))
        )



        cursor.close()   // close the db connection

        return experience

    }


    fun updateExperience(experience: Experience): Int {

        val db = dbhelper.writableDatabase

        val values = ContentValues()

        values.put(DBHelper.COLUMN_COMPANY_NAME, experience.companyName)
        values.put(DBHelper.COLUMN_COMPANY_DESCRIPTION, experience.companyDescription)
        values.put(DBHelper.COLUMN_JOB_TITLE, experience.jobTitle)
        values.put(DBHelper.COLUMN_JOB_DESCRIPTION, experience.jobDescription)
        values.put(DBHelper.COLUMN_START_DATE, experience.startDate)
        values.put(DBHelper.COLUMN_END_DATE, experience.endDate)
        values.put(DBHelper.COLUMN_TASKS, experience.tasks)
        values.put(DBHelper.COLUMN_ACHIEVEMENTS, experience.achievements)
        values.put(DBHelper.COLUMN_TECHNOLOGY_USED, experience.technologiesUsed)

        return db.update(
            DBHelper.TABLE_NAME_EXPERIENCE,
            values,
            DBHelper.COLUMN_ID + " = ?",
            arrayOf(experience.id.toString())
        )   // updating row

    }


    fun deleteAcProject(experience: Experience): Boolean {

        val db = dbhelper.writableDatabase   // Gets the data repository in write mode

        db.delete(
            DBHelper.TABLE_NAME_EXPERIENCE,
            DBHelper.COLUMN_ID + " LIKE ?",
            arrayOf(experience.id.toString())
        ) // Issue SQL statement.

        return true

    }


    val experienceCount: Int
        get() {

            val countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_EXPERIENCE
            val db = dbhelper.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            val count = cursor.count

            cursor.close()

            return count

        }

}