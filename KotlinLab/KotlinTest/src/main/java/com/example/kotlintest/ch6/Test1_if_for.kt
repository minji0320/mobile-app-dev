package com.example.kotlintest.ch6

fun main() {
    fun ifTest(arg: Int) {
        val result = if (arg > 10) {
            println("arg>10")
            arg
        } else if (arg > 100) {
            println("else...")
            100
        } else {
            0
        }

        println(if (result != null) "good.." else "bad...")
        println("result : $result")
    }
    ifTest(10)

    fun forTest() {
        for (i in 10 downTo 1 step 2) {
            println(i)
        }

        val array = arrayOf("hello", "world")
        for ((index, value) in array.withIndex()) {
            println("index:$index, value:$value")
        }

        val map = mapOf<String, String>("one" to "hello", "two" to "world")
        for (item in map) {
            println("key:${item.key}, value:${item.value}")
        }
    }
    forTest()
}