import 'package:bloc/bloc.dart';
import '../event/todo_event.dart';
import '../state/todos_state.dart';

class TodosBloc extends Bloc<TodosEvent, TodosState> {
  TodosBloc() : super(TodosState([])) {
    on<AddTodoEvent>((event, emit) {
      // 기존의 state를 변경한 것이 아니라 새로운 state를 만들고 추가한 것
      // state management f/w에서는 상태를 불변으로 취급함
      // 기존 상태를 변경 하는 것이 아니라 기존 상태를 참조해서 새로운 상태를 만들어야 함
      List<Todo> newTodos = List.from(state.todos)
          ..add(event.todo);
      emit(TodosState(newTodos));
    });

    on<DeleteTodoEvent>((event, emit) {
      List<Todo> newTodos = List.from(state.todos)
        ..remove(event.todo);
      emit(TodosState(newTodos));
    });

    on<ToggleCompleteTodoEvent>((event, emit) {
      List<Todo> newTodos = List.from(state.todos);
      int index = newTodos.indexOf(event.todo);
      newTodos[index].toggleCompleted();
      emit(TodosState(newTodos));
    });
  }

  @override
  void onTransition(Transition<TodosEvent, TodosState> transition) {
    super.onTransition(transition);
    print(transition);
  }

  @override
  void onError(Object error, StackTrace stackTrace) {
    super.onError(error, stackTrace);
    print('error... $error');
  }
}