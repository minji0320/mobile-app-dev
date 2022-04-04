package com.example.kotlintest.ch5

fun testAny(arg: Any) {
    when (arg) {
        1 -> println("arg is 1")
        10, 20 -> println("arg is 10 or 20")
        30 -> {
            val result = arg as Int * 10
            println("arg is 30, result: $result")
        }
        "hello" -> println("arg is hello")
        is Double -> println("arg type is Double")
        else -> println("unknown data")
    }
}

fun main() {
    testAny(1)
    testAny(10)
    testAny(30)
    testAny("hello")
    testAny(12)

    // casting
    val data1 = 10
    // as : 명시적 캐스팅, 상하관계에서만 가능
    val data2: Double = data1.toDouble()
    val data3: Int = data2.toInt()

    // int <-> string
    val data4: String = data1.toString()
    val data5: Int = data4.toInt()

    // nullable - non-null
    val data6: Any = 10
    val data7: Any? = data6
    val data8: Any = data7 as Any // nullable이 non-null의 상위 타능,입 암시적 캐스팅 불가

    val data9: java.lang.Object = Object()
    val data10: Any = data9 // Any가 Object의 상위
}