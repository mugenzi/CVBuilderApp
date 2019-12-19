package com.example.cvbuilderapp.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME_CONTACT, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_CONTACT = ("Create table if not exists " + TABLE_NAME_CONTACT +
                "(" + KEY_ROWID + " integer PRIMARY KEY AUTOINCREMENT," +
                FIRSTNAME + " text NOT NULL," +
                LASTNAME + " text NOT NULL," +
                PHONE + " text NOT NULL," +
                STREET + " text NOT NULL," +
                CITY + " text NOT NULL," +
                STATE + " text NOT NULL," +
                ZIPCODE + " number NOT NULL," +
                EMAIL + " text NOT NULL," +
                LINKEDIN + " text NOT NULL)")

        val CREATE_TABLE_EDUCATION = (
                "Create table if not exists " + TABLE_NAME_EDUCATION + "("
                        + COLUMN_ID + " integer PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_SCHOOL + " text NOT NULL,"
                        + COLUMN_DEGREE + " text NOT NULL,"
                        + COLUMN_MAJOR + " text NOT NULL"
                        + ")")

         val CREATE_TABLE_ACADEMIC_PROJECT = (

                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_ACADEMIC_PROJECT + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_TITTLE + " TEXT,"
                        + COLUMN_SCHOOL + " TEXT,"
                        + COLUMN_YEAR + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT,"
                        + COLUMN_TECHNOLOGY + " TEXT"
                        + ")")

        val CREATE_TABLE_EXPERIENCE = (
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_EXPERIENCE + "("
                        + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_COMPANY_NAME + " TEXT,"
                        + COLUMN_COMPANY_DESCRIPTION + " TEXT,"
                        + COLUMN_JOB_TITLE + " TEXT,"
                        + COLUMN_JOB_DESCRIPTION + " TEXT,"
                        + COLUMN_START_DATE + " TEXT,"
                        + COLUMN_END_DATE + " TEXT,"
                        + COLUMN_TASKS + " TEXT,"
                        + COLUMN_ACHIEVEMENTS + " TEXT,"
                        + COLUMN_TECHNOLOGY_USED + " TEXT"
                        + ")")

        db?.execSQL(CREATE_TABLE_EDUCATION)
        db?.execSQL(CREATE_TABLE_CONTACT)
        db?.execSQL(CREATE_TABLE_ACADEMIC_PROJECT)
        db?.execSQL(CREATE_TABLE_EXPERIENCE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CONTACT)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EDUCATION)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ACADEMIC_PROJECT)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EXPERIENCE)
        onCreate(db)
    }

    companion object {
        // Database Information
        val DB_NAME_CONTACT = "CVBUILDERAPPDB"
        val DB_VERSION = 1

        // TABLE INFORMATTION
        val TABLE_NAME_CONTACT = "CONTACT"

        val KEY_ROWID = "ID"
        val FIRSTNAME = "FIRSTNAME"
        val LASTNAME = "LASTNAME"
        val PHONE = "PHONE"
        val STREET = "STREET"
        val CITY = "CITY"
        val STATE = "STATE"
        val ZIPCODE = "ZIPCODE"
        val EMAIL = "EMAIL"
        val LINKEDIN = "LINKEDIN"
        val TABLE_NAME_EDUCATION = "EDUCATION"
        val COLUMN_ID = "id"
        val COLUMN_SCHOOL = "school"
        val COLUMN_DEGREE = "degree"
        val COLUMN_MAJOR = "major"


        val TABLE_NAME_ACADEMIC_PROJECT = "apTable"
        // Attributes for Tables
        val COLUMN_TITTLE = "tittle"
        val COLUMN_YEAR = "year"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_TECHNOLOGY = "technology"

        val TABLE_NAME_EXPERIENCE = "EXPERIENCE"

        val COLUMN_COMPANY_NAME = "companyName"
        val COLUMN_COMPANY_DESCRIPTION = "companyDescription"
        val COLUMN_JOB_TITLE = "jobTitle"
        val COLUMN_JOB_DESCRIPTION = "jobDescription"
        val COLUMN_START_DATE = "startDate"
        val COLUMN_END_DATE = "endDate"
        val COLUMN_TASKS = "tasks"
        val COLUMN_ACHIEVEMENTS = "achievements"
        val COLUMN_TECHNOLOGY_USED = "technologiesUsed"
    }
}
