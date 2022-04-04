package com.example.kotlintest.ch9

// singleton
// 외부에서 객체생성하지 못하게 생성자를 private으로 만듦
// 내부에서 객체를 하나만 생성해서 그 객체가 반복적으로 이용되도록 만듦
// 외부에서 객체를 이용해야 하는데, 어떻게 사용?
// java에서는 객체 생성 없이 이용 가능하게 static 멤버로 객체를 준비함
// kotlin에는 static 예약어 없음
// top-level에 변수 함수 선언 가능하기 때문에 일반적으로 static 멤버로만 구성된 utility 멤버들은 그냥 top-level에 구현
// 특정 클래스 내에서 자바의 static처럼 이용되게 하는 멤버도 필요함 -> companion object 활용
class Singleton private constructor() {
    var data: Int = 0

    companion object {
        // 이 안에 선언된 멤버들은 외부에서 객체 생성 없이 클래스 명으로 바로 접근, java의 static 효과
        val instance: Singleton by lazy {
            Singleton()
        }
    }
}

// Builder 패턴 적용
// 객체는 여러 개 생성될 수 있다
// 직접 생성하지 않고, 객체 생성 대행자를 두겠다는 의미
class BuilderTest private constructor() {
    var data: Int = 0

    companion object Builder {
        fun getInstance(): BuilderTest {
            return BuilderTest()
        }
    }
}

fun main() {
    val obj1 = Singleton.instance
    obj1.data = 10
    val obj2 = Singleton.instance
    obj2.data = 20

    println("obj1: ${obj1.data}, obj2: ${obj2.data}")


    val builder = BuilderTest.Builder
    val obj3 = builder.getInstance()
    val obj4 = builder.getInstance()

    obj3.data = 10
    obj4.data = 20

    println("obj3: ${obj3.data}, obj4: ${obj4.data}")
}