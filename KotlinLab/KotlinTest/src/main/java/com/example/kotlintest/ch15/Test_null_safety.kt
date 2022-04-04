package com.example.kotlintest.ch15

fun main() {
    var data1: String? = "aaa"

    val result1 = if (data1 != null) {
        data1.uppercase()
    } else {
        null
    }

    val result2 = data1?.uppercase()

    // 여러 라인 실행
    var result3 = data1?.let {
        println("${data1.length}")
    }

    // null이면 null이 아니라 특정 데이터
    val result4 = data1?.uppercase() ?: "unKnown".uppercase()

    val result5 = data1?.run {

    } ?: run {

    }
}