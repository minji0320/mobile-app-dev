package com.example.kotlintest.ch21

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun normalFun(no: Int): Int {
    var sum = 0
    for (i in 1..no) {
        Thread.sleep(10)
        sum += i
    }
    return sum
}

// coroutine 내에서만 호출 가능
suspend fun suspendFun(no: Int): Int {
    var sum = 0
    for (i in 1..no) {
        delay(10)
        sum += i
    }
    return sum
}

fun main() = runBlocking {
    for (i in 1..5) {
        thread {
            println("thread... $i start... ${Thread.currentThread().name}")
            normalFun(i)
            println("thread... $i end... ${Thread.currentThread().name}")
        }
    }
    delay(2000)
    for (i in 1..5) {
        launch(Dispatchers.Default) {
            println("coroutine... $i start... ${Thread.currentThread().name}")
            suspendFun(i)
            println("coroutine... $i end... ${Thread.currentThread().name}")
        }
    }
    delay(2000)
}

//thread... 1 start... Thread-0
//thread... 3 start... Thread-2
//thread... 2 start... Thread-1
//thread... 4 start... Thread-3
//thread... 5 start... Thread-4
//thread... 1 end... Thread-0
//thread... 2 end... Thread-1
//thread... 3 end... Thread-2
//thread... 4 end... Thread-3
//thread... 5 end... Thread-4
//coroutine... 1 start... DefaultDispatcher-worker-1
//coroutine... 2 start... DefaultDispatcher-worker-3
//coroutine... 3 start... DefaultDispatcher-worker-4
//coroutine... 4 start... DefaultDispatcher-worker-4
//coroutine... 5 start... DefaultDispatcher-worker-2
//coroutine... 1 end... DefaultDispatcher-worker-2
//coroutine... 2 end... DefaultDispatcher-worker-7
//coroutine... 3 end... DefaultDispatcher-worker-10
//coroutine... 4 end... DefaultDispatcher-worker-9
//coroutine... 5 end... DefaultDispatcher-worker-14


// thread는 한 스레드 내에서 함수를 실행하고 종료함
// coroutine의 경우, 실행하는 곳과 종료하는 곳이 다를 수 있음