import 'package:flutter/material.dart';

// 개발자가 만드는 위젯 클래스
// 상위에서 데이터만 유지하고 하위에서 이용하기 위한 위젯
class MyInheritedWidget extends InheritedWidget {
  // 상위에서 유지되는 데이터
  int count = 0;
  // 여기에 대입되는 위젯이 이 데이터를 이용하는 하위 위젯
  Widget child;

  MyInheritedWidget(this.child): super(child: child);

  increment() {
    count++;
  }

  // 자신의 데이터 변경 시 하위 위젯을 rebuild 되어야 하는지를 판단
  @override
  bool updateShouldNotify(covariant InheritedWidget oldWidget) => true;

  // 하위 위젯과 결합 X, 하위가 어떤 위젯이 되든 상관 없음
  // 하위에서 접근하기 위해서는 객체가 있어야 함.
  // 아래의 함수 호출하여 객체를 획득할 것임
  // 이 객체를 하위에서 이용하기는 하지만 생성은 하지 않음
  // 일종의 DI 개념
  // 꼭 자신의 객체를 리턴시키지 않아도 됨
  static MyInheritedWidget? of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<MyInheritedWidget>();
}

class TestScreenSub extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // 객체 생성 X
    int count = MyInheritedWidget.of(context)!.count;
    return Row(
      children: [
        Text('TestScreenSub... ${count}')
      ],
    );
  }
}

class TestScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return StatefulBuilder(
      builder: (BuildContext context, StateSetter setState) {
        // 객체를 획득해 사용해도 됨
        MyInheritedWidget? widget = MyInheritedWidget.of(context);

        return Row(
          children: [
            Text('TestScreen...'),
            Text('${widget!.count}'),
            ElevatedButton(
                onPressed: (){
                  setState(() => widget.increment());
                },
                child: Text('Increment')
            ),
            TestScreenSub()
          ],
        );
      },
    );
  }
}