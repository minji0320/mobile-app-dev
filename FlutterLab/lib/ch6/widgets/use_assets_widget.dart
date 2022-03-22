import 'package:flutter/material.dart';
import 'dart:convert'; //json
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class UseAssetWidget extends StatelessWidget {
  Widget makeTextAssetWidget(context) {
    // 비동기 처리 데이터, 미래에 발생하는 데이터를 표현하는 타입이 Future
    // 화면 출력하려면 위젯이 있어야 함
    // 대기하다가 Future에 실제 데이터 발생 순간 감지하다가 화면을 그려주는 위젯이 FutureBuilder
    return FutureBuilder<String>(
      future: DefaultAssetBundle.of(context).loadString('assets/text/my_text.txt'),
      builder: (context, snapshot) {
        if(snapshot.hasData) {
          return Text('text file load... ${snapshot.data}');
        }
        return Text('');
      },
    );
  }

  Widget makeJsonAssetWidget(context) {
    return FutureBuilder(
      future: DefaultAssetBundle.of(context).loadString('assets/text/data.json'),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          var root = json.decode(snapshot.data.toString());
          return Text('json data : ${root[0]['name']}, ${root[0]['age']}');
        }
        return Text('');
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Image(
          image: AssetImage('assets/icon/user.png'),
        ),
        Image.asset('assets/icon/user.png'),
        makeTextAssetWidget(context),
        makeJsonAssetWidget(context),
        Icon(Icons.menu),
        Icon(Icons.add),
        Icon(FontAwesomeIcons.music, size: 30, color: Colors.pink,)
      ],
    );
  }
}