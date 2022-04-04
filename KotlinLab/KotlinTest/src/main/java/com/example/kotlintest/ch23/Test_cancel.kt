package com.example.kotlintest.ch23

import kotlinx.coroutines.*

fun main() = runBlocking{
    println("main start.....")

    val job = launch(Dispatchers.Default) {
//        repeat(5){
//            println("coroutine... $it")
//            delay(500)
//        }

        //cancel 이 요청 되었다고 하더라도.. 코루틴 내에서 exception 을 발생시켜야 취소된다..
        try {
            var start = System.currentTimeMillis()
            var i = 0
            while (i < 5) {
                //코루틴 내에서 cancel 된건지를 체크해서.. exception 발생 로직 추가..
                if(isActive) {
                    if (System.currentTimeMillis() >= start) {
                        i++
                        println("coroutine...$i")
                        start += 500
                    }
                }else {
                    throw CancellationException()
                }
            }
        }finally {
            println("coroutine... finally")
            //cancel 에 의해 exception 이 발생했다고 하더라도 할짓 끝까지 하고 싶다면...
            withContext(NonCancellable) {
                repeat(5) {
                    println("coroutine.. finally $it")
                    //cancel 시 마지막 처리를 위해서 finally 를 준비했다고 하더라도..
                    //이 안에서 다시 cancel 에 의한 exception 발생 api 를 사용했다면..
                    //finally 가 다 실행되지 않을 수 있다...
                    delay(100)
                }
            }
        }
    }
    delay(1000)
    println("main.. cancel coroutine...")
    job.cancelAndJoin()
    println("main end.....")
}