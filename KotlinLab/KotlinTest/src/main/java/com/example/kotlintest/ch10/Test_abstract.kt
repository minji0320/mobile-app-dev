package com.example.kotlintest.ch10

abstract class Super {
    //자바에서는 추상 변수 지원하지 않는다..
    //추상형은 일반적으로 함수의 오버라이드 목적으로 사용되지만.. 프로퍼티도 가능하다...
    //자바로 변형시 getter/setter 가 추상형이 된다..
    //프로퍼티를 추상형으로 선언하는 경우는.. 하위에서 적절한 값으로 최기화 해서 사용하라는 의미...
    abstract val data1: String

    open fun some1() {
        println("Super...some1...$data1")
    }

    abstract fun some2()

    open fun some3() {
        println("Super...some3...")
    }
}

interface Interface1 {
    //interface 의 프로퍼티의 기본은 추상형...
    val data1: String

    //interface 의 프로퍼티에 값 초기화 시킬 수 없다...
    //프로퍼티가 자바로 변형되면서 데이터를 저장할 수 있는 field 가 선언되지 않기 때문이다...
    //초기값 등을 주고 싶다면.. get, set 함수를 이용,.... 그 안에서 초기화 가능하다...
    //custom accessor 를 정의해서 추상형으로 만들지 않을 수는 있지만... get, set 내에서 field 사용은 불가하다...
    val data2: Int
        get() = 10

    fun some1(){
        println("interface... some1...")
    }
    fun some2()
    fun some3()
}

interface Interface2

class Sub: Interface2, Super(), Interface1 {
    //동일 이름의 추상함수 여러개...
    override fun some2() {
        println("Sub...some2")
    }
    //동일 이름의 구현함수가 여러개이고.. 오버라이드 받는 경우...
    //구현 함수임으로 상위의 함수를 호출할 수도 있다..그런데 여러개다.. 식별해서 이용해야 한다..
    override fun some1(){
        super <Interface1>.some1()
        super <Super>.some1()
    }
    //구현+추상. 중복되는 경우...
    override fun some3(){
        super.some3()
    }

    override val data1: String = "kkang"
}

fun main() {
    val obj = Sub()
    obj.some1()
    obj.some2()
    obj.some3()
}

//interface... some1...
//Super...some1...kkang
//Sub...some2
//Super...some3...
