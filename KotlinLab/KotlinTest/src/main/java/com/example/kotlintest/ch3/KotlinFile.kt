package ch3.one

import java.text.SimpleDateFormat
import java.util.*

class MyFile {
    val data = 10
    fun some() {
        println("MyFile.. class.. some()")
    }
}

val data = 20

fun printData() {
    val date = Date()
    val sdformat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(sdformat.format(date))

    class MyClass {
        val data = 10
        fun some() {
            println("printData()... MyClass... some()")
        }
    }

    fun some() {
        println("printData()... some()")
    }

    some()
    val obj = MyClass()
    obj.some()
}

fun main() {
    val obj = MyFile()
    println("obj data : ${obj.data}")
    println("top level data : ${data}")
    printData()
}