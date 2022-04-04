package com.example.kotlintest.ch24

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

// produce - coroutine builder, 채널 리턴시킴

fun CoroutineScope.one() = produce<String> {
    while (true) {
        delay(300)
        send("one")
    }
}

fun CoroutineScope.two() = produce<String> {
    while (true) {
        delay(300)
        send("two")
    }
}

fun main() = runBlocking {
    val one = one()
    val two = two()

    var count = 0
    while (true) {
        select<Unit> {
            one.onReceive {
                println("receive -> $it")
            }
            two.onReceive {
                println("receive -> $it")
            }
        }
        count++
        if (count > 3) break
    }
    coroutineContext.cancelChildren()
}