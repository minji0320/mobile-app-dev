package com.example.kotlintest.ch12

// 일반적인 타입 재정의
typealias MyFunType = (Int) -> Boolean

fun main() {
    // 함수를 프로퍼티에 대입
    // var, val은 일반 프로퍼티와 동일
    var some1 = { no: Int -> println("some1... $no") }
    some1(10)

    // some1 초기화 시 대입된 함수로 타입이 픽스됨.. 타입이 맞지 않아서..
    // some1 = {arg: String -> println("hello")} // error

    // 여러라인 작성 가능.. 람다 함수에는 명시적인 return 사용 불가, 맨 마지막 라인의 결과값이 리턴
    var some2 = { arg1: Int, arg2: Int ->
        println("some2... $arg1, $arg2")
        arg1 * arg2
    }
    println("some2.. result : ${some2(10, 20)}")

    // 매개변수 없으면 -> 왼쪽 생략
    val some3 = {
        println("some4...")
        10 * 10
    }

    // 타입 유추만 된다면 매개변수 타입 생략 가능
    val some4: (Int, Int) -> Boolean = { arg1, arg2 ->
        arg1 > arg2
    }

    val some5: MyFunType = { arg ->
        true
    }


    // it
    val some6: (Int) -> Unit = { it * 10 }
    class User(var name: String, var age: Int)
    val some7: (User) -> Int = {
        it.name = "hello"
        it.age = 10
        10
    }
}