package com.example.kotlintest.ch11

// enum
enum class Direction1 {
    NORTH, SOUTH, WEST, EAST
}
// 개발자 임의 데이터 추가
// 상수는 enum 클래스의 소브 객체라는 개념
// 상위 클래스의 생성자 연결 기법으로 데이터 추가

enum class Direction2(val no: Int, val email: String) {
    NORTH(10, "a@a.com"),
    SOUTH(20, "b@b.com")
}

enum class Direction3 {
    NORTH {
        // 상위에 선언된 것과 관련없는 이 안에서 임의 프로퍼티, 함수 선언 가능
        // 이 상수 클래스 내부에서만 이용, 외부에서는 접근 불가
        val data1 = 10
        fun some() {}

        override val data2 = 20
        override fun some2() {
            println("Direction3.NORTH.. some2()...")
        }
    },
    SOUTH {
        override val data2 = 30
        override fun some2() {
            println("Direction3.SOUTH.. some2()...")
        }
    };

    abstract val data2: Int
    abstract fun some2()

    // 상수와 관련없는 enum 클래스 자체의 완성된 프로퍼티, 함수 선언 가능
    val data3 = 30
    fun some3() {}
}


// sealed class
sealed class Shape(val x: Int, val y: Int) {
    class Circle(x: Int, y: Int, val radius: Int) : Shape(x, y)
}

class Triangle(x: Int, y: Int, val bottom: Int, val height: Int) : Shape(x, y)

// 기본적으로 아래처럼 선언되어 있는 것과 같음
sealed class SealedClass private constructor()
// private으로 생성자가 선언되어 있지만, sealded에 의해 서브 클래스 선언가능
// 현재의 파일 내에서만 가능
class SealedSub: SealedClass()


abstract class AbstractClass private constructor()
//class AbstractSub: AbstractClass() // error...


fun main() {
    val data1: Direction1 = Direction1.EAST
    println("${data1.name}, ${data1.ordinal}")  // EAST, 3

    val data2: Direction2 = Direction2.NORTH
    println("${data2.no}, ${data2.email}")  // 10, a@a.com

    val data3: Direction3 = Direction3.NORTH
    data3.some2()
    data3.some3()


    val obj: Shape = Shape.Circle(1, 2, 3)
}