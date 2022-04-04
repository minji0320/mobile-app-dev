package com.example.kotlintest.ch11

open class SuperClass {
    fun some3() {}
}

class Outer {
    val data1 = 10

    private val obj = object {
        val data2 = 20
        fun some2() {
            println("$data1")
        }
    }

    // private 해야 내부에서 사용 가능
    fun some1() {
        println("${obj.data2}")
        obj.some2()
    }

    val obj2 = object : SuperClass() {
        fun some4() {}
    }

    companion object NamedObjectClass {
        fun some5() {}
    }
}

fun main() {
    val obj = Outer()
//    obj.obj.data2 = 30 // error

    obj.obj2.some3()

    Outer.NamedObjectClass.some5()
//    Outer.some5() // error -> companion 추가 필요
    Outer.some5()
}