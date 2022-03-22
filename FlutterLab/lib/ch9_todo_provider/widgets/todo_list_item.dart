import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/todo.dart';
import '../providers/todos_model.dart';

class TodoListItem extends StatelessWidget {
  Todo todo;

  // 상위 위젯의 데이터를 생성자로 받아옴
  TodoListItem(this.todo);

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Checkbox(
        value: todo.completed,
        onChanged: (bool? checked) {
          // provider의 함수 호출해서 앱 데이터 변경
          // listen: false - 내가 발생시켰지만 나는 rebuild 하지 않아도 됨 (기본값은 true)
          Provider.of<TodosModel>(context, listen: false).toggleTodo(todo);
        },
      ),
      title: Text(todo.title),
      trailing: IconButton(
        icon: Icon(
          Icons.delete,
          color: Colors.red,
        ),
        onPressed: () {
          Provider.of<TodosModel>(context, listen: false).deleteTodo(todo);
        },
      ),
    );
  }
}

