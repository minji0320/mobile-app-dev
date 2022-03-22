import 'package:flutter/material.dart';
import 'package:flutter_lab/ch5/instagram_widget/content_widget.dart';
import 'package:flutter_lab/ch5/instagram_widget/header_widget.dart';
import 'package:flutter_lab/ch5/instagram_widget/icon_widget.dart';
import 'package:flutter_lab/ch5/instagram_widget/image_widget.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Layout Test'),
        ),
        body: SingleChildScrollView(
          child: Column(
            children: [
              HeaderWidget(),
              ImageWidget(),
              IconWidget(),
              ContentWidget(),
            ],
          ),
        )
      ),
    );
  }
}

