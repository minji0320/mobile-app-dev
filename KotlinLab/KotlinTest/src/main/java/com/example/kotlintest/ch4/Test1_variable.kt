package com.example.kotlintest.ch4

//top level, class member property 는 선언과 동시에 초기화 해야 한다..
//null 도 초기화 시킨거다..
//선별적으로 초기화를 미루고자 한다면.. lateinit, by lazy
val data1: Int = 10
var data2: Int = 10

class MyClass {
    val data3: Int = 10
    var data4: Int = 10

    fun someFun() {
        //local property 만 선언과 동시에 초기화 하지 않아도 된다..
        val data5: Int
        var data6: Int

        //local variable 은 선언과 초기화를 따로 할 수 있지만..
        //사용하기 전에는 꼭 초기화 되어 있어야..
        data5 = 10
        data6 = 10
        val result = data5 + data6

        //null 대입도 초기화 하는 거다...
        val data7: String? = null
        var data8: String? = null
        data8 = "hello"

        //변수..(property) 의 기본은 val, non-null 선언이고...
        //선별적으로 필요한 것만 var, nullable 로...
        println("${data8?.equals("hello")}")



    }

    //val 로 선언... immutable 이다.. 값 변경 불가... 그런데 상수는 아니다...
    //kotlin 에서 top level, class member 변수는 property 이다..
    //값 변경은 불가하지만 동일한 값이 넘어간다고 볼수 없다..
    //property 는 getter , setter 내장변수... customizing 가능..
    var myBool = false
    val myData: Int
        get(){
            if(myBool)
                return 100
            else
                return 0
        }
}

fun main() {
    val obj = MyClass()
    println("${obj.myData}")

    obj.myBool = true

    println("${obj.myData}")
}