package com.example.kotlintest.ch13

fun some1(fun1: (Int, Int) -> Int, fun2: (Int) -> Boolean) {
    val result1 = fun1(10, 20)
    val result2 = fun2(result1)
    if (result2) {
        println("valid...")
    } else {
        println("invalid...")
    }
}

inline fun some2(fun1: (Int, Int) -> Int, fun2: (Int) -> Boolean) {
    val result1 = fun1(10, 20)
    val result2 = fun2(result1)
    if (result2) {
        println("valid...")
    } else {
        println("invalid...")
    }
}

inline fun some3(argFun: (Int) -> Unit) {
    println("some3... before...")
    argFun(10)
    println("some3... after...")
}

fun main() {
    some1({ arg1, arg2 -> arg1 * arg2 }) { result ->
        result > 100
    }
    println("inline...")
    some2({ arg1, arg2 -> arg1 * arg2 }) { result ->
        result > 100
    }

    println("lambda return...")
    println("main before...")
    some3 {
        println("lambda before...")
        val array = kotlin.arrayOf(10, 20, -1, 30)
        array.forEach {
            if (it < 0) return@some3
            println(it)
        }
        println("lambda after...")
    }
    println("main after...")

    // inline의 경우 return을 구체적으로 지정해줘야 함

    // return
//    main before...
//    some3... before...
//    lambda before...
//    10
//    20
//    30

    // return@forEach
//    main before...
//    some3... before...
//    lambda before...
//    10
//    20
//    30
//    lambda after...
//    some3... after...
//    main after...

    // return@some3
//    main before...
//    some3... before...
//    lambda before...
//    10
//    20
//    some3... after...
//    main after...
}