package com.example.customersqliteapp

import android.nfc.tech.TagTechnology

class Experience {
    var id: Int? = null
    var companyName: String? = null
    var companyDescription: String? = null
    var jobTitle: String? = null
    var jobDescription: String? = null
    var startDate: String? = null
    var endDate: String? = null
    var tasks: String? = null
    var achievements: String? = null
    var technologiesUsed: String? = null


    constructor(
        id: Int, companyName: String, companyDescription: String,
        jobTitle: String, jobDescription: String, startDate: String,
        endDate: String, tasks: String, achievements: String, technologiesUsed: String
    ) {

        this.id = id
        this.companyName = companyName
        this.companyDescription = companyDescription
        this.jobTitle = jobTitle
        this.jobDescription = jobDescription
        this.startDate = startDate
        this.endDate = endDate
        this.tasks = tasks
        this.achievements = achievements
        this.technologiesUsed = technologiesUsed

    }

    constructor(
        companyName: String, companyDescription: String,
        jobTitle: String, jobDescription: String, startDate: String,
        endDate: String, tasks: String, achievements: String, technologiesUsed: String
    )  {

        this.companyName = companyName
        this.companyDescription = companyDescription
        this.jobTitle = jobTitle
        this.jobDescription = jobDescription
        this.startDate = startDate
        this.endDate = endDate
        this.tasks = tasks
        this.achievements = achievements
        this.technologiesUsed = technologiesUsed

    }


}