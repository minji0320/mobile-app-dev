package com.example.kotlintest.ch5

fun main() {
    fun arrayTest() {
        val array1 = arrayOf<Any>(10, true, "hello")
        array1[0] = 30
        array1[1] = false
        println("arr size: ${array1.size}, ${array1[0]}, ${array1.get(1)}")

        intArrayOf(10, 20, 30)  // IntArray
        arrayOf<Int>(10, 20, 30)  // Array<Int>

        // 사이즈 지정, 초기값..
        Array(2, { 0 })
        Array(3, { i -> i * 10 })

        // 배열 선언하면서 사이즈 한정짓고, null로 데이터 초기화
        arrayOfNulls<String>(2)

        // 기초 타입의 데이터인 경우, 사이즈만 주어도 숫자는 0, boolean은 false
        IntArray(2)
    }

    // List : mutable, immutable 구분해서 사용
    val list1 = listOf<Int>(10, 20)
    list1.forEach { println(it) }
//    list1.add(40)   // List -> immutable, 데이터 추가/변경 불가능

    val list2 = mutableListOf<Int>()
    list2.add(10)
    list2.add(20)

    // map : key - value
    val map1 = mapOf<String, String>(Pair("one", "hello"), Pair("two", "world"))
    println("${map1.get("one")}")

    val map2 = mapOf<String, String>("one" to "hello", "one" to "world")
    val iterator: Iterator<Map.Entry<String, String>> = map2.iterator()
    while (iterator.hasNext()) {
        val entry = iterator.next()
        println("${entry.key}, ${entry.value}")
    }
}