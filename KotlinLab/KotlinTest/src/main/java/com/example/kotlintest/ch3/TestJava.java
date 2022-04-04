package com.example.kotlintest.ch3;

import ch3.one.KotlinFileKt;
import ch3.one.MyFile;

public class TestJava {
    void some() {
        MyFile obj = new MyFile();
        obj.some();

        KotlinFileKt.printData();
    }
}
