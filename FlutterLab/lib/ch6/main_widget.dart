import 'package:flutter/material.dart';
import 'package:flutter_lab/ch6/widgets/form_widget.dart';
import 'package:flutter_lab/ch6/widgets/input_widget.dart';
import 'package:flutter_lab/ch6/widgets/list_widget.dart';
import 'package:flutter_lab/ch6/widgets/use_assets_widget.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyAppState();
  }
}

class MyAppState extends State<MyApp> {
  int _selectIndex = 0;

  List<Widget> _widgetOptions = [
    UseAssetWidget(),
    InputWidget(),
    FormWidget(),
    ListWidget()
  ];

  // tab button click 시에 호출
  void _onItemTapped(int index) {
    setState(() {
      _selectIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Widget Test'),
          actions: [
            IconButton(
              onPressed: (){},
              icon: Icon(Icons.add_alert),
            ),
            IconButton(
              onPressed: (){},
              icon: Icon(Icons.navigate_next),
            )
          ],
        ),
        body: Center(
          child: _widgetOptions.elementAt(_selectIndex),
        ),
        bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed,
          items: [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: 'assets'
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.business),
                label: 'input'
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.school),
                label: 'form'
            ),
            BottomNavigationBarItem(
                icon: Icon(Icons.list),
                label: 'list'
            ),
          ],
          currentIndex: _selectIndex,
          selectedItemColor: Colors.amber[800],
          onTap: _onItemTapped,
        ),
        drawer: Drawer(
          child: ListView(
            padding: EdgeInsets.zero,
            children: [
              DrawerHeader(
                child: Text('Drawer Header'),
                decoration: BoxDecoration(
                  color: Colors.blue
                ),
              ),
              ListTile(
                title: Text('item1'),
                onTap: (){},
              ),
              ListTile(
                title: Text('item2'),
                onTap: (){},
              )
            ],
          ),
        ),
      ),
    );
  }
}

