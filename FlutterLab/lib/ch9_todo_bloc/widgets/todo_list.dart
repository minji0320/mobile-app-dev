import 'package:flutter/material.dart';
import '../state/todos_state.dart';
import 'todo_list_item.dart';

class TodoList extends StatelessWidget {
  List<Todo> todos;
  TodoList(this.todos);

  @override
  Widget build(BuildContext context) {
    return ListView(
      children: getChildrenTodos(),
    );
  }

  List<Widget> getChildrenTodos() {
    return todos.map((todo) => TodoListItem(todo)).toList();
  }
}


