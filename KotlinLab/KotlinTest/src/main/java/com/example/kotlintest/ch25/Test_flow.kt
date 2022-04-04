package com.example.kotlintest.ch25

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

val datas = listOf(10, 20, 30, 40, 50)

suspend fun calSum(no: Int): Int {
    var sum = 0
    for (i in 1..no) {
        sum += i
        delay(10)
    }
    return sum
}

fun numFlow(): Flow<Int> = flow {
    datas.forEach {
        delay(1000)
        emit(it)
    }
}

fun colorFlow(): Flow<String> = flow {
    listOf("black", "red",  "blue").forEach {
        delay(500)
        emit(it)
    }
}

fun main() = runBlocking {
    numFlow()
        .transform {
            emit("receive.. $it")
            val result = calSum(it)
            emit("result... $result")
        }
        .combine(colorFlow()) {sum, color -> "sum: $sum, color: $color"}
        .onCompletion { println("Done...") }
        .collect { println(it) }
}