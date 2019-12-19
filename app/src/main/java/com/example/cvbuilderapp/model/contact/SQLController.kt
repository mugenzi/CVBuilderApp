package com.example.cvbuilderapp.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.cvbuilderapp.utils.Utils
import java.lang.Exception

class SQLController(c: Context) // Constructor to set the context
{
    lateinit var dbhelper: DBHelper
    lateinit var ourcontext: Context
    lateinit var database: SQLiteDatabase

    init {
        ourcontext = c
    }

    fun open(): SQLController {
        dbhelper = DBHelper(ourcontext)
        database = dbhelper.getWritableDatabase()
        return this
    }

    fun close() {
        dbhelper.close()
    }

    //Inserting Data into table
    fun insertData(newObject: Contact): Long {
        val contact = ContentValues()
        contact.put(DBHelper.FIRSTNAME, newObject.firstName)
        contact.put(DBHelper.LASTNAME, newObject.lastName)
        contact.put(DBHelper.PHONE, newObject.phone)
        contact.put(DBHelper.STREET, newObject.street)
        contact.put(DBHelper.CITY, newObject.city)
        contact.put(DBHelper.STATE, newObject.state)
        contact.put(DBHelper.ZIPCODE, newObject.zipcode)
        contact.put(DBHelper.EMAIL, newObject.email)
        contact.put(DBHelper.LINKEDIN, newObject.linkedIn)
        return database.insert(DBHelper.TABLE_NAME_CONTACT, null, contact)
    }

    //Getting Cursor to read data from table
    fun readData(): Cursor {
        val allColumns =
            arrayOf<String>(
                DBHelper.KEY_ROWID,
                DBHelper.FIRSTNAME,
                DBHelper.LASTNAME,
                DBHelper.PHONE,
                DBHelper.STREET,
                DBHelper.CITY,
                DBHelper.STATE,
                DBHelper.ZIPCODE,
                DBHelper.EMAIL,
                DBHelper.LINKEDIN
            )
        val c =
            database.query(DBHelper.TABLE_NAME_CONTACT, allColumns, null, null, null, null, null)
        c?.moveToFirst()
        return c
    }

    // Update the record in the Table
    fun updateData(newObject: Contact): Int {
        val cvUpdate = ContentValues()
        cvUpdate.put(DBHelper.KEY_ROWID, newObject.id)
        cvUpdate.put(DBHelper.FIRSTNAME, newObject.firstName)
        cvUpdate.put(DBHelper.LASTNAME, newObject.lastName)
        cvUpdate.put(DBHelper.PHONE, newObject.phone)
        cvUpdate.put(DBHelper.STREET, newObject.street)
        cvUpdate.put(DBHelper.CITY, newObject.city)
        cvUpdate.put(DBHelper.STATE, newObject.state)
        cvUpdate.put(DBHelper.ZIPCODE, newObject.zipcode)
        cvUpdate.put(DBHelper.EMAIL, newObject.email)
        cvUpdate.put(DBHelper.LINKEDIN, newObject.linkedIn)
        val i = database.update(
            DBHelper.TABLE_NAME_CONTACT, cvUpdate,
            DBHelper.KEY_ROWID + " = " + newObject.id, null
        )
        return i
    }

    // Deleting record data from table by NAME
    fun deleteData(id: Long) {
        database.delete(
            DBHelper.TABLE_NAME_CONTACT, (DBHelper.KEY_ROWID + "="
                    + id), null
        )
    }

    fun getContactInfo(): Contact? {
        var contact: Contact? = null
        try {

            val cursor = readData()
            if (cursor.count > 0) {
                contact = Contact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9)
                )
            } else {
                Utils.showMessage(ourcontext, "No id found")
            }
        } catch (e: Exception) {
            Utils.showMessage(ourcontext, "Read Data Error")
        }
        return contact
    }
}