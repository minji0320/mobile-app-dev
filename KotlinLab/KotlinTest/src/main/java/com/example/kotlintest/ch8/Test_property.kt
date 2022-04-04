package com.example.kotlintest.ch8

class User {
    var id: Int = 0
        set(value) {
            if (value < 0) {
                field = 0
            } else {
                field = value
            }
        }

    var name: String? = null
        get() {
            return field?.uppercase()
        }

    // property ==> field, setter, getter
    // java : 변경 시 field 변수가 항상 만들어지는 것은 아니고,
    // accessor 내에서 그 자체의 값을 표현하는 field 예약어가 사용되는 경우에만 생성됨
    // custom accessor를 정의했는데, 그 곳에서 field를 사용하지 않았다면, 자바 변경 시 변수가 없다는 것 -> 에러 발생
    // boxing : 기초 데이터 타입, 객체로 Int => Integer
    // backing field : property에 의해서 만들어지는 자바의 변수

//    val data1: String = "kim"
//        get() = "kang"

    val data1: String
        get() = "kang"

//    var data2 = "kim"
//        get() = "kang"
//        set(value) = value
}

lateinit var data3: String

class MyClass {
    val data1: Int
    val data2: String
    lateinit var data3: String

    init {
        data1 = 10
        data2 = "kim"
    }

    val data4: Int by lazy {
        // 처음 사용될 때 초기화 됨
        println("lazy...")
        10
    }
}

fun main() {
    val obj = User()
    obj.id = 10
    obj.name = "hello"

    val obj2 = MyClass()
    println("before use data4....")
    println("data4: ${obj2.data4}")
}