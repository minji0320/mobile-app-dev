import 'package:flutter/material.dart';
import 'package:flutter_lab/ch9/bloc_widget.dart';
import 'package:flutter_lab/ch9/cubit_widget.dart';
import 'package:flutter_lab/ch9/inherited_widget.dart';
import 'package:flutter_lab/ch9/provider_widget.dart';
void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('State Test'),
        ),
        body: Column(
          children: <Widget>[
            MyInheritedWidget(TestScreen()),
            ProviderScreen(),
            BlocWidget(),
            CubitWidget(),
          ],
        ),
      )
    );
  }
}

