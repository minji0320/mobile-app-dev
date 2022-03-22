import 'package:flutter/material.dart';
import 'package:flutter_lab/ch8/future_widget.dart';
import 'package:flutter_lab/ch8/my_util.dart';
import 'package:flutter_lab/ch8/stream_widget.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }
}

class MyAppState extends State<MyApp> {
  int _selectedIndex = 0;

  List<Widget> _widget = [
    FutureWidget(),
    StreamWidget()
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    getServerData('1');
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(title: Text('Future, Stream'),),
        body: Center(
          child: _widget.elementAt(_selectedIndex),
        ),
        bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          items: <BottomNavigationBarItem>[
            BottomNavigationBarItem(icon: Icon(Icons.home), label: 'future'),
            BottomNavigationBarItem(icon: Icon(Icons.home), label: 'stream'),
          ],
          currentIndex: _selectedIndex,
          selectedItemColor: Colors.amber[800],
          onTap: _onItemTapped,
        ),
      ),
    );
  }
}