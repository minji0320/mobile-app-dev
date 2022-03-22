import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:async';
import 'dart:convert';
import 'dart:isolate';

main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
          primaryColor: Colors.blue
      ),
      home: IsolateScreen(),
    );
  }
}

class IsolateScreen extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return IsolateScreenState();
  }
}

class IsolateScreenState extends State<IsolateScreen> {
  //서버데이터를 가져와서.. ListView 로 화면을 구성할 거다...
  //한꺼번에 얼마의 데이터를??? 페이지 개념 적용해서.. 데이터를 가져온후에..
  //유저 스크롤에 의해서 데이터가 더 필요하다는 상황 감지해야 한다..
  //ListView 의 Scroll 정보....
  ScrollController controller = ScrollController();

  List datas = [];//서버 데이터...

  //서버에 전달할 데이터...
  int page = 1;//페이지 번호...
  int seed = 1;

  //isolate 에 의해 실행될 함수.. 매개변수는 main thread 가 넘긴 port...
  static dataLoader(SendPort sendPort) async {
    //서버 요청을 할려면.. 데이터가 있어야 한다.. url 등.. 그건.. main 쪽의 데이터이다..
    //main 쪽에서 url 이 동적이다.. (get 방식으로 데이터를 넘겨서..)
    //main 쪽의 데이터를 받기 위한 port 를 연다...
    ReceivePort port = ReceivePort();
    sendPort.send(port.sendPort);

    //main 에서 넘어온 데이터 획득... 서버 연동..
    await for(var msg in port){
      String url = msg[0];
      SendPort replyTo = msg[1];//서버 연동 결과 데이터를 받기 위해서 main 에서 open 한 포트...

      http.Response response = await http.get(Uri.parse(url));
      //main 에 서버 데이터 전달..
      replyTo.send(json.decode(response.body));
    }
  }

  //main thread 에 의해 실행될 함수..
  //네트워킹이 필요한 순간...최초.. 스크롤에 의해.. refresh 가 필요한 순간..
  Future<List> getServerData() async {
    String url = "https://randomuser.me/api/?seed=${seed}&page=${page}&results=20";
    print(url);

    ReceivePort receivePort = ReceivePort();
    Isolate.spawn(dataLoader, receivePort.sendPort);

    SendPort sendPort = await receivePort.first;
    ReceivePort response = ReceivePort();
    sendPort.send([url, response.sendPort]);
    Map<String, dynamic> result = await response.first;
    return result['results'];
  }

  @override
  void initState() {
    super.initState();
    //ListView 스크롤 이벤트 핸들러 등록...
    controller.addListener(_scrollListener);

    //초기 데이터 요청한다..
    getServerData().then((result){
      setState(() {
        datas = result;
      });
    });
  }

  void dispose() {
    super.dispose();
    controller.dispose();
  }

  //ListView top 에서 up scroll 발생시...
  Future<List<dynamic>> _refresh() async {
    page = 1;
    seed++;
    List result = await getServerData();
    setState(() {
      datas = result;
    });
    return result;
  }

  _scrollListener() async {
    //현재 스크롤이 마지막 아이템까지 스크롤 되었다면.. 을 판단...
    //특정 갯수로, 예로 들면.. 20개 가 있는데.. 15번째가 나오는 시점.. 등...
    //이것은 지원 안된다.. 외부 패키지 이용할 수도...
    if(controller.offset >= controller.position.maxScrollExtent &&
        !controller.position.outOfRange){
      page++;
      List result = await getServerData();
      setState(() {
        datas.addAll(result);
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
          title: Text('Isolate Test')
      ),
      body: RefreshIndicator(
        onRefresh: _refresh,
        child: ListView.separated(
          controller: controller,
          itemCount: datas.length,
          itemBuilder: (BuildContext context, int position){
            return ListTile(
              contentPadding: EdgeInsets.all(5),
              title: Text("${datas[position]['name']['first']} ${datas[position]['name']['last']}"),
              subtitle: Text("${datas[position]['email']}"),
              leading: CircleAvatar(
                radius: 25,
                child: ClipOval(
                  child: Image.network(datas[position]['picture']['thumbnail']),
                ),
              ),
            );
          },
          separatorBuilder: (BuildContext context, int position){
            return Divider(
              color: Colors.black,
            );
          },
        ),
      ),
    );
  }
}
