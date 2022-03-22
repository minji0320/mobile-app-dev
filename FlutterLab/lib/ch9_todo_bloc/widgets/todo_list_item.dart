import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../event/todo_event.dart';
import '../state/todos_state.dart';

class TodoListItem extends StatelessWidget {
  Todo todo;

  // 상위 위젯의 데이터를 생성자로 받아옴
  TodoListItem(this.todo);

  @override
  Widget build(BuildContext context) {
    final TodosBloc todosBloc = BlocProvider.of<TodosBloc>(context);

    return ListTile(
      leading: Checkbox(
        value: todo.completed,
        onChanged: (bool? checked) {
          todosBloc.add(ToggleCompleteTodoEvent(todo));
        },
      ),
      title: Text(todo.title),
      trailing: IconButton(
        icon: Icon(
          Icons.delete,
          color: Colors.red,
        ),
        onPressed: () {
          todosBloc.add(DeleteTodoEvent(todo));
        },
      ),
    );
  }
}

