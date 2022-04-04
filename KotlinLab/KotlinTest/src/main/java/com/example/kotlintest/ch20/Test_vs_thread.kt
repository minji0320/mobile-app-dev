package com.example.kotlintest.ch20

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

// runBlocking : coroutine 에서 제공하는 blocking builder
// main 함수로 테스트 하면서 이 안에서 suspend 함수를 이용하기 위해서
// android, spring 등에서 이용한다면 필요 없음
fun main() = runBlocking {
    val count = 10_000

    var time = measureTimeMillis {
        val threadJobs = List(count) {
            thread {
                Thread.sleep(1000)
                print(".")
            }
        }
        threadJobs.forEach { it.join() } // 종료까지 대기
    }
    println("thread $count, total time: $time")

    time = measureTimeMillis {
        val coroutineJobs = List(count) {
            // 가장 많이 사용하는 코루틴 빌더, 만들자마자 실행됨
            launch {
                delay(1000)
                print(".")
            }
        }
        coroutineJobs.forEach { it.join() }
    }
    println("coroutine $count, total time: $time")
}

// thread 10000, total time: 6166
// coroutine 10000, total time: 1127
// => tread 10개 구동하면 10개 thread 만들어져서 실행
// => coroutine은 10개를 구동했다가 10개의 thread가 만들어지는 것은 아님