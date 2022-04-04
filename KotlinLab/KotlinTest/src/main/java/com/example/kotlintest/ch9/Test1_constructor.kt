package com.example.kotlintest.ch9

open class Super(no: Int) {
    constructor(no: Int, name: String) : this(no) {
        println("Super... constructor(no: Int, name: String)")
    }

    init {
        println("Super... init")
    }
}

class Sub(no: Int) : Super(no) {
    constructor(no: Int, name: String) : this(no) {
        println("Sub... constructor(no: Int, name: String)")
    }

    init {
        println("Sub... init")
    }
}

fun main() {
    val obj = Sub(10, "kim")
//    Super... init
//    Sub... init
//    Sub... constructor(no: Int, name: String)

    // casting
    val obj2: Super = Sub(10)
    val obj3: Sub = obj as Sub

    val obj4: Super? = null
    val obj5: Sub? = obj4 as? Sub

}