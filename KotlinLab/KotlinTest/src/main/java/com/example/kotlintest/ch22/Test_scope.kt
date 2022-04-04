package com.example.kotlintest.ch22

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyScope: CoroutineScope {
    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + job

    fun some() {
        val subJob = launch {
            repeat(10) {
                println("in scope... $it")
                delay(100L)
            }
            // 코루틴 내부에서 특정 상황에 자신 취소
            cancel()
        }
        subJob.cancel()
    }
}

fun main() = runBlocking {
    val obj = MyScope()
    obj.some()
    obj.job.cancelAndJoin()

    // dispatcher 지정해서 코루틴 구동
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        repeat(5) {
            delay(800L)
            println("coroutine2...")
        }
    }
    delay(1000L)
    scope.cancel()

    // app의 데몬 스레드처럼 동작하는 업무가 있다면
    GlobalScope.launch {
        launch {
            repeat(10) {
                println("global.. $it")
                delay(500L)
            }
        }
    }

    Thread.sleep(1000)
    print("main end...")
}