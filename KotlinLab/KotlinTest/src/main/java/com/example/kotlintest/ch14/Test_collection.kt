package com.example.kotlintest.ch14

import com.google.gson.Gson
import java.io.File
import java.io.FileInputStream

// json 파싱한 데이터를 담기 위한 data 클래
data class Data(val id: Int, val name: String, val addr: String, val age: String)

fun main() {
    // java.io의 API 이용해서 file read..
    var file = File("KotlinTest/build/resources/main/test.json")
    val inputStream: FileInputStream = file.inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    println(inputString)

    val gson = Gson()
    // kotlin에서의 클래스 reference 정보를 지칭하려면 A::class 라고 주면 됨
    // 이건 대입되는 곳의 타입이 클래스 reference 타입이고 KClass<*> 로 선언된 경우, 이건 코틀린 타입
    // java에서는 class<*>으로..
    // Class<*> 타입에 reference 정보를 주려면.. A::class.java
    var list: List<Data> = gson.fromJson(inputString, Array<Data>::class.java).toList()

    list.filter { it.addr.equals("seoul")}
        .groupBy { it.age }
        .forEach {
            println("${it.key} - ${it.value.count()}")
            it.value.forEach {
                println("${it.name}, ${it.addr}, ${it.age}")
            }
        }

}