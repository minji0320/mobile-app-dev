package com.example.kotlintest.ch6

fun main() {
    val array1 = arrayOf(10, 20, 30)
    val array2 = arrayOf(40, 50, 60)

    val array3 = arrayOf(*array1, *array2)
    array3.forEach { println(it) }

    // 일치 연산자 : 일반 객체의 경우는 ==, === 모두 객체 비교, ?에 의한 boxing 효과 없음
    class User

    val user1 = User()
    val user2 = User()
    var user3: User? = User()
    val user4 = user1

    // 기초 데이터 타입의 객체의 경우
    // ? 에 의해 nullable 로 선언되지 않았다면 모두 값 비교
    // 기초 타입 , ? 인 경우만 boxing 효과, == 는 값, === 는 객체

    // false, false, false, true
    println("${user1 == user2}, ${user1 === user2}, ${user1 == user3}, ${user1 == user4}")

    val no1 = 1000
    val no2 = 1000
    var no3: Int? = 1000
    var no4: Int? = 1000

    // true, true, true, true, false, true
    println("${no1 == no2}, ${no1 === no2}, ${no1 == no3}, ${no1 === no3}, ${no4 === no3}, ${no4 == no3}")
}