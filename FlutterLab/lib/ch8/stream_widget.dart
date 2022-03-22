import 'package:flutter/material.dart';
import 'dart:async';
import 'my_util.dart';

class StreamWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return StreamWidgetState();
  }
}

class StreamWidgetState extends State<StreamWidget> {
  List<Article> list = [];

  // 반복적으로 받은 stream 데이터를 이 곳에 add
  StreamController<List<Article>> streamController = StreamController();

  void getDate(int page) async {
    await getServerData(page.toString())
        .then((data) {
          streamController.add(data);
    });
  }

  int transform(int i) {
    int page = ++i;
    getDate(page);
    return i;
  }

  _periodicStream() async {
    // 5초에 한번씩
    Duration interval = Duration(seconds: 5);
    Stream<int> stream = Stream<int>.periodic(interval, transform);

    // 5번만 서버 데이터 획득
    stream = stream.take(5);

    // stream에 의해 발행된 데이터를 StreamBuilder가 화면에 출력시키겠지만,
    // 위에 만들어 놓은 것은 Stream 연결만 시킨거고
    // Stream에 의해 데이터 발생 명령을 내린 것은 아님

    await for(int i in stream) {
      print('...$i');

    }
  }

  @override
  void initState() {
    super.initState();
    _periodicStream();
  }

  @override
  Widget build(BuildContext context) {
    return StreamBuilder(
      stream: streamController.stream,
      builder: (BuildContext context, AsyncSnapshot snapshot) {
        if(snapshot.hasData) {
          list.addAll(snapshot.data);
          return getWidget(list);
        } else if(snapshot.hasError) {
          return Text('${snapshot.error}');
        }

        return CircularProgressIndicator();
      },
    );
  }
}