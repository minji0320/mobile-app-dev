import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'add_screen.dart';
import '../models/todo.dart';
import '../providers/todos_model.dart';
import '../widgets/todo_list.dart';

// HomeWidget의 한장의 탭 화면
class TabScreen extends StatelessWidget {
  List<Todo> todos;
  TabScreen(this.todos);

  @override
  Widget build(BuildContext context) {
    return Container(
      child: TodoList(this.todos),
    );
  }
}

// home 화면 전체
class HomeScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomeScreenState();
  }
}

// SingleTickerProviderStateMixin : 화면 전환 애니메이션 효과를 위해
class _HomeScreenState extends State<HomeScreen> with SingleTickerProviderStateMixin {
  // 변수 선언 시 초기값을 줄 수 없는 경우
  // 1. nullable로 선언 : null safety 연산자 필요
  // 2. 나중에 초기화 : late
  late TabController controller;

  @override
  void initState() {
    super.initState();
    // vsync : animation 효과
    controller = TabController(length: 3, vsync: this);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Todos'),
        actions: [
          IconButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => AddTodoScreen())
                );
              },
              icon: Icon(Icons.add)
          )
        ],
        bottom: TabBar(
          controller: controller,
          tabs: [
            Tab(text: 'All',),
            Tab(text: 'Active',),
            Tab(text: 'Complete',)
          ],
        ),
      ),
      body: Consumer<TodosModel>(
        builder: (context, model, child) {
          return TabBarView(
            controller: controller,
            children: [
              TabScreen(model.todos),
              TabScreen(model.todos.where((todo) => !todo.completed).toList()),
              TabScreen(model.todos.where((todo) => todo.completed).toList()),
            ],
          );
        },
      ),
    );
  }
}

