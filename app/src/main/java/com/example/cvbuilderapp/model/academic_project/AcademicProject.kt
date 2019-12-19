package com.example.cvbuilderapp.model.academic_project
import java.io.Serializable

class AcademicProject (var name: String) : Serializable {

    var id: Int? = null
    var title: String? = null
    var school: String? = null
    var year: String? = null
    var description: String? = null
    var technology: String? = null


    constructor(id: Int, title: String, school: String,  year: String, description: String, technology: String) : this(title) {

        this.id = id
        this.title = title
        this.school = school
        this.year = year
        this.description = description
        this.technology = technology

    }

    constructor(title: String, school: String,  year: String, description: String, technology: String) : this(title) {

        this.title = title
        this.school = school
        this.year = year
        this.description = description
        this.technology = technology

    }





}