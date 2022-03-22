import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'blocs/todo_bloc.dart';
import 'screens/home_screen.dart';


main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider<TodosBloc>(
      create: (context) => TodosBloc(),
      child: MaterialApp(
        home: HomeScreen(),
      ),
    );
  }
}

