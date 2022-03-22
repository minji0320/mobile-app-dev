import '../state/todos_state.dart';

abstract class TodosEvent {}

class AddTodoEvent extends TodosEvent {
  Todo todo;
  AddTodoEvent(this.todo);
}

class DeleteTodoEvent extends TodosEvent {
  Todo todo;
  DeleteTodoEvent(this.todo);
}

class ToggleCompleteTodoEvent extends TodosEvent {
  Todo todo;
  ToggleCompleteTodoEvent(this.todo);
}
