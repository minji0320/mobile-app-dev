import 'package:flutter/material.dart';
import 'package:flutter_lab/ch9_todo_provider/providers/todos_model.dart';
import 'package:flutter_lab/ch9_todo_provider/screens/home_screen.dart';
import 'package:provider/provider.dart';

main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => TodosModel(),
      child: MaterialApp(
        home: HomeScreen(),
      ),
    );
  }
}

