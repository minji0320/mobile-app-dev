import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../event/todo_event.dart';
import '../state/todos_state.dart';

class AddTodoScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _AddTodoScreenState();
  }
}

class _AddTodoScreenState extends State<AddTodoScreen> {
  final controller = TextEditingController();
  bool completedStatus = false;

  void onAdd() {
    final String title = controller.text;
    final bool complete = completedStatus;
    if(title.isNotEmpty) {
      final Todo todo = Todo(
        title: title,
        completed: complete
      );

      final TodosBloc todosBloc = BlocProvider.of<TodosBloc>(context);
      todosBloc.add(AddTodoEvent(todo));
      Navigator.pop(context);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Add Todo'),),
      body: ListView(
        children: [
          Padding(
            padding: EdgeInsets.all(15.0),
            child: Container(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.stretch,
                children: [
                  TextField(controller: controller,),
                  CheckboxListTile(
                    value: completedStatus,
                    onChanged: (checked) => setState(() {
                      completedStatus = checked ?? false;
                    }),
                    title: Text('Completed?'),
                  ),
                  ElevatedButton(
                    onPressed: onAdd,
                    child: Text('Add')
                  )
                ],
              ),
            ),
          )
        ],
      ),
    );
  }
}

