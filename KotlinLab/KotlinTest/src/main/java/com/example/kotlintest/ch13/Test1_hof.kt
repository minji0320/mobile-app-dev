package com.example.kotlintest.ch13

fun main() {

    // 집합 데이터를 필터링 하는 함수를 만든다는 가정
    // 필터링 조건은 함수로, 외부에서 전달
    fun myFilter(list: List<Int>, arg: (Int) -> Boolean): List<Int> {
        var resultList = mutableListOf<Int>()
        val iterator = list.iterator()
        while (iterator.hasNext()) {
            val no = iterator.next()
            val result = arg(no)
            if (result) {
                resultList.add(no)
            }
        }
        return resultList
    }

    val testList = listOf<Int>(10, 13, 6, 20, 3)
    val resultList = myFilter(testList) { it > 10 }
    resultList.forEach { println(it) }

    // 매개변수로 조건을 주면 조건에 맞는 업무를 가지는 함수를 리턴
    fun myRun(arg: String): (Int, Int) -> Unit {
        when (arg) {
            "+" -> return { arg1, arg2 ->
                var result = 0
                for (i in arg1..arg2) {
                    result += i
                    println("result: $result")
                }
                Thread.sleep(1000)
            }
            else -> return { arg1, arg2 ->
                var result = 0
                for (i in arg1..arg2) {
                    result *= i
                    println("result: $result")
                }
                Thread.sleep(1000)
            }
        }
    }

    val resultFun = myRun("+")
    resultFun(1, 3)


    println("...........run..........")
    class User {
        var name = "hello"
        fun sayHello() {}
    }

    val obj = User()
    val result = obj.run {
        name = "world"
        sayHello()
        "Hello World"
    }
    println(result)


    println("...........curring.............")
    // 함수 분해 기법
    // 함수의 매개변수가 많고, 각각의 매개변수로 처리되는 단위 업무가 분리된다면, 분해해서 작성
    // f(a1, a2, a3) => result
    // 위의 함수를 분해해서
    // f1(a1) => f2(a2) => f3(a3) => result

    // 하나의 업무 처리가 된다고 하더라도, 그 안에 작게 분리되는 업무 처리가 여러 개로 나누어 진다면
    // 분리시켜서 함수의 연결 관계로 처리하자는 개념
    // 이점 1 : 비동기 처리가 쉽다. 하나의 업무처리 한 후 두번째 업무 처리하는 경우가 아니라 대기하거나 처리한 후 다음이 처리되는 경우 용이하게 작성 가능
    // 이점 2 : 함수의 재사용에도 용이

    fun f(x: Int, y: Int): Int {
        return x - y
    }
    f(50, 4)

    // 이를 분해한다면
    // 방법 1
    fun f(x: Int): (Int) -> Int {
        return fun(y: Int): Int {
            return x - y
        }
    }
    println(f(50)(8))

    val fResult = f(50)
    println("something...")
    fResult(8)

    // 방법 2
    fun f1(x: Int) = fun(y: Int): Int {
        return x - y
    }
    f1(50)(5)

    // 방법 3
    fun f2(x: Int) = { y: Int -> x - y }
    f2(50)(5)


    // 다른 방법
    fun some1(a1: Int, a2: Int, a3: Int, a4: Int): Int {
        return a1 + a2 + a3 + a4
    }
    some1(10, 20, 30, 40)

    fun some2(a1: Int) = { a2: Int ->
        { a3: Int ->
            { a4: Int ->
                a1 + a2 + a3 + a4
            }
        }
    }
    some2(10)(20)(30)(40)

    val result1 = some2(10)
    println("something...")
    val result2 = result1(20)
}