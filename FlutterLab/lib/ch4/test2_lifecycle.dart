import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

void main() => runApp(ParentWidget());

class ParentWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _ParentWidgetState();
  }
}

class _ParentWidgetState extends State<ParentWidget> {
  int _counter = 0;
  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Lifecycle Test'),
        ),
        body: Provider.value( // value에 명시한 데이터를 하위에 전파, 이것도 위젯
          value: _counter,
          updateShouldNotify: (oldValue, newValue) => true, // 데이터 변경 시 하위에 전달하기 위한 부분, true : 변경사항 하위 전파, 기본값은 true
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Text('incremente counter...'),
                ChildWidget()
              ],
            ),
          ),
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: _incrementCounter,
          child: Icon(Icons.add),
        ),
      ),
    );
  }
}

class ChildWidget extends StatefulWidget {
  ChildWidget() {
    print('ChildWidget... constructor...');
  }

  @override
  State<StatefulWidget> createState() {
    return _ChildWidgetState();
  }
}

// WidgetsBindingObserver : app의 lifecycle 감지를 위해
class _ChildWidgetState extends State<ChildWidget> with WidgetsBindingObserver {
  int _counter = 0;

  _ChildWidgetState() {
    print('ChildWidgetState... constructor...');
  }

  @override
  void initState() {
    super.initState();
    print('_ChildWidgetState... initState...');
    // 앱의 라이프사이클 감지 시작
    WidgetsBinding.instance!.addObserver(this);
  }

  @override
  void dispose() {
    super.dispose();
    WidgetsBinding.instance!.removeObserver(this);
  }

  @override
  void didChangeDependencies() {
    super.didChangeDependencies();
    // 상위에서 전파한 데이터 받기
    _counter = Provider.of<int>(context);
    print('_ChildWidgetState... didChangeDependencies... $_counter');
  }

  @override
  Widget build(BuildContext context) {
    print('_ChildWidgetState... build... $_counter');
    return Text(
      '$_counter'
    );
  }

  //앱의 라이프사이클 변경 시 호출되는 함수
  @override
  void didChangeAppLifecycleState(AppLifecycleState state) {
    super.didChangeAppLifecycleState(state);
    switch (state) {
      case AppLifecycleState.resumed:
        print('AppLifecycle... resumed...');
        break;
      case AppLifecycleState.paused:
        print('AppLifecycle... paused...');
        break;
    }
  }
}