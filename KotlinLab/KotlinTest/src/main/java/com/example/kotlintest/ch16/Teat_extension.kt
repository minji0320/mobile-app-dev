package com.example.kotlintest.ch16

// 확장 대상이 되는 클래스 가정
// extensionData는 한 파일 내에서만 가능함.. 사용 X 권장
class User {
    val innerData = 10
    fun innerFun() {
        println("User.. innerFun()... $innerData, $extensionData")
    }
}

// 확장 프로퍼티는 직접 초기화 불가능
// get()에 의해서만 초기화 됨
val User.extensionData
    get() = "kim"

fun User.extensionFun() {
    println("User.. extension.. fun.. $innerData, $extensionData")
}


open class Super {
    fun some() {
        println("Super... some()")
    }

    open fun some1() {
        println("Super... some1()")
    }
}

class Sub : Super() {
    override fun some1() {
        println("Sub...some1()")
    }
}

fun Super.some2() {
    println("Super... some2()")
}

fun Sub.some2() {
    println("Sub... some2()")
    some()
    some1()
//    super.some1()//error
}

fun String.indexOfAll(arg: String): List<Int> {
    val list = mutableListOf<Int>()
    var pos = this.indexOf(arg)
    while (pos > -1) {
        list.add(pos)
        pos = this.indexOf(arg, pos + 1)
    }

    return list
}

fun main() {
    val obj = User()
    obj.innerFun()
    obj.extensionFun()

    val obj2: Super = Sub()
    obj2.some1()
    obj2.some2()

    var str = "hello world"
    str.indexOfAll("l")
        .forEach { println(it) }
}