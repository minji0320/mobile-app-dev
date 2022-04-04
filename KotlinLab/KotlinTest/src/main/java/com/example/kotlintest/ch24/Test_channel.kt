package com.example.kotlintest.ch24

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    // 코루틴의 데이터를 여러번 받기 위해서 코루틴을 구동시킨 곳과 데이터를 발생시키는 코루틴이 동일 채널을 공유
    val channel = Channel<Int>()

    launch {
        for (x in 1..5) {
            println("send... $x")
            channel.send(x)
            delay(200)
        }

        // 업무 처리 끝내고 channel을 닫아줌
        channel.close()
    }

    for (y in channel) {
        println("receive..  $y")
    }
    println("Done...")
}