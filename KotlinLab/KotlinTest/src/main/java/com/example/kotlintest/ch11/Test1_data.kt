package com.example.kotlintest.ch11

class User(val no: Int, val name: String)

data class UserData(val no: Int, val name: String) {
    var email: String? = null

    constructor(no: Int, name: String, email: String) : this(no, name) {
        this.email = email
    }
}

fun main() {
    val user1 = User(1, "kang")
    val user2 = User(1, "kang")

    val user3 = UserData(1, "kang")
    val user4 = UserData(1, "kang")

    // false, true, false
    println("${user1.equals(user2)}, ${user3.equals(user4)}, ${user1.equals(user3)}")

    val user5 = UserData(1, "kang", "a@a.com")
    val user6 = UserData(1, "kang", "b@b.com")

    // true, true -> 주 생성자만 보고 비교함!
    println("${user5.equals(user6)}, ${user5.equals(user4)}")

    // data class의 equals 함수가 주생성자 프로퍼티 비교로 재정의, 값 비교하게 됨
    // data class의 객체라고 하더라도 일반적으로는 값 비교겠지만, 객체 비교가 필요할 때도 있다.
    println("...........................")

    //false, false, true, false
    // data 클래스에 한해서, ==는 값 비교, ===은 객체 비교
    println("${user1 == user2}, ${user1 === user2}, ${user3 == user4}, ${user3 === user4}")

    // 자바로 변형 시 == -> areEqual(), === -> ==, 그래서 ===이 객체 비교가 됨
    //var6 = Intrinsics.areEqual(user1, user2) + ", " + (user1 == user2) + ", " + Intrinsics.areEqual(user3, user4) + ", " + (user3 == user4);
    //public static boolean areEqual(Object first, Object second) {
    //        return first == null ? second == null : first.equals(second);
    //    }

    //== 가 areEqual 로.. areEqual 은.. 객체의 equals() 함수..
    //그래서 일반 객체는 Any 의 equals 로... 객체 비교
    //data 클래스는 값 비교..

    println("toString...............................")
    //user1: com.example.kotlintest.ch11.User@36d64342, user3:UserData(no=1, name=kkang), user5:UserData(no=1, name=kkang)
    println("user1: ${user1}, user3:${user3}, user5:${user5}")

    println("destructing.............................")
    val no1 = user3.component1()
    val name1 = user3.component2()
//    val email = user3.component3()//error... 주생성자에 한해서만..

    //한꺼번에 여러개 획득...
    val (name, no) = user3//이름으로 하는것 아니다.. 선언된 순서로 하는 것이다..
    //프로퍼티가 많아서 그중에.. 일부만...
    val (_, a) = user3//순서로 하는데.. 순서상 획득하고 싶지 않은 프로퍼티는... _ 로 표현... 변수명 아니다.. 순서를 표현하기 위한것이다..

    //1, kkang, kkang, 1, kkang
    println("$no1, $name1, $no, $name, $a")

    //위의 형태로 데이터를 획득하는 것은... data 클래스에서만 되는 것이 아니라...List, map 집합객체도 가능하다..
    val list = listOf(1, 2, 3, 4)
    val (a1, a2) = list

    val (b1, b2) = list.drop(1)
    val (_, c1, _, c2) = list
    val d1 = list.component1()

    //1, 2, 2, 3, 2, 4, 1
    println("$a1, $a2, $b1, $b2, $c1, $c2, $d1")

    println("tuple.......................................")
    //하나의 함수에서 결과를 여러개 리턴하고 싶은경우... tuple 기법.....

    /*
        //코틀린은 tuple 기법 제공하지 않는다..
        fun getDatas(): (Int, Int, Int) {
            return (10, 20, 30)
        }
        val (a1, a2, a3) = getDatas()
         */
    //위의 tuple 기법을 data 클래스를 이용해 비슷하게 구현 가능...
    data class Datas(var data1: Int, var data2: Int, var data3: Int)

    fun getDatas(): Datas {
        return Datas(10, 20, 30)
    }
    val (e1, e2, e3) = getDatas()

    //위의 data 클래스 사용을 아래처럼.. 이용해도 된다...
    fun getDatas2(): Array<Int> {
        return arrayOf(10, 20, 30)
    }
    val (f1, f2, f3) = getDatas2()

    //map... map 자체에서 destructing 을 제공하는 것이다.. map 의 구성요소인 Entry 에서 제공..
    val map = mapOf("one" to 10, "tow" to 20)
    map.mapValues { (_, value: Int) -> println("$value") }
}