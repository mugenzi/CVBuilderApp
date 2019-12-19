package com.example.cvbuilderapp.model

class Contact(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val street: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val email: String,
    val linkedIn: String
) {
    override fun toString(): String {
        return "Contact(id=$id, firstName='$firstName', lastName='$lastName', phone='$phone', street='$street', city='$city', state='$state', zipcode='$zipcode', email='$email', linkedIn='$linkedIn')"
    }
}