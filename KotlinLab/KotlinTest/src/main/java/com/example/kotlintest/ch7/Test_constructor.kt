package com.example.kotlintest.ch7

class User(val id: String, val name: String) {
    var email: String? = null

    constructor(id: String, name: String, email: String) : this(id, name) {
        println("constructor: id-$id, name-$name, email-$email")
        this.email = email
    }

    init {
        println("init... id-$id, name-$name")
    }

    fun some() {
        println("some()... id-$id, name-$name, email-$email")
    }
}

fun main() {
    val user1 = User("111", "aaa")
    println("....................")
    val user2 = User("222", "bbb", "bbb@bbb.com")
}