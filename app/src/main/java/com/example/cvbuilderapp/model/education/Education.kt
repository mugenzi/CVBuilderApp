package com.example.cvbuilderapp.model.education
import java.io.Serializable

class Education (var name: String) : Serializable {

    var id: Int? = null
    var school: String? = null
    var degree: String? = null
    var major: String? = null

    constructor(id: Int, school: String, degree: String, major: String) : this(school) {
        this.id = id
        this.school = school
        this.degree = degree
        this.major = major
    }

    constructor(school: String, degree: String, major: String) : this(school) {
        this.school = school
        this.degree = degree
        this.major = major
    }

    constructor(school: String, degree: String) : this(school) {
        this.school = school
        this.degree = degree

    }




}