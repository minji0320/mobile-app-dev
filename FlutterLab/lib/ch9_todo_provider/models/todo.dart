import 'package:flutter/material.dart';

// 앱 전역에서 유지하는 데이터, 하나의 to_do 추상화
// 실제 provider에 등록하는 데이터는 이 to_do 객체 배열
class Todo {
  String title;
  bool completed;

  Todo({required this.title, this.completed = false});

  void toggleCompleted() {
    completed = !completed;
  }
}
