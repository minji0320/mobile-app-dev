package com.example.kotlintest.ch4

fun sum(start: Int = 1, end: Int = 10, result: Int = 0): Int {
    if(start>end) return result
    else return sum(start+1, end, result + start)
}

tailrec fun tailRecSum(start: Int = 1, end: Int = 10, result: Int = 0): Int {
    if(start>end) return result
    else return tailRecSum(start+1, end, result + start)
}

fun main() {
    fun myFun(arg1: Int = 0, arg2: String = "hello") = println("arg1:$arg1, arg2:$arg2")
    myFun()
    myFun(10, "world")
    myFun(arg2 = "world", arg1 = 20)

    //vararg
    fun varargFun(arg1: Int, vararg args: Int){
        //가변인수는 내부적으로 배열이다..
        for(a in args){//in... 범위 연산자...
            print("args: $a")
        }
        if(args.size > 0){
            println(args[0])
        }
    }
    varargFun(10)
    varargFun(10, 20, 30, 40)

    println("result: ${sum(end=10)}")
    println("result: ${tailRecSum(end=10)}")
}