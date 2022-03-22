import 'dart:collection';
import 'package:flutter/material.dart';
import 'package:flutter_lab/ch9_todo_provider/models/todo.dart';

// provider에 의해 관리되는 앱의 상태 데이터
class TodosModel extends ChangeNotifier {
  final List<Todo> todos = [];

  void addTodo(Todo todo) {
    todos.add(todo);
    notifyListeners();
  }

  void toggleTodo(Todo todo) {
    final index = todos.indexOf(todo);
    todos[index].toggleCompleted();
    notifyListeners();
  }

  void deleteTodo(Todo todo) {
    todos.remove(todo);
    notifyListeners();
  }
}
