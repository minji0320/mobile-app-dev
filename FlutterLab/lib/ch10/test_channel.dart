import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutter/services.dart';

main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: NativeCallWidget(),
    );
  }
}

class NativeCallWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _NativeCallWidgetState();
  }
}

class _NativeCallWidgetState extends State<NativeCallWidget> {
  String? resultMessage;
  String? receiveMessage;

  Future<Null> nativeCallWithMessageChannel() async {
    const channel = BasicMessageChannel<String>('myMessageChannel', StringCodec());

    // dart -> platform
    String? result = await channel.send('Hello, I am flutter message');
    setState(() {
      resultMessage = result;
    });

    // platform -> dart
    channel.setMessageHandler((String? message) async {
      setState(() {
        receiveMessage = message;
      });
      return 'Hi from Dart';
    });
  }

  String? resultMethod;
  Future<Null> nativeCallWithMethodChannel() async {
    const methodChannel = const MethodChannel("myMethodChannel");
    try {
      var details = {'Username': 'minji', 'Password': '1234'};
      final Map result = await methodChannel.invokeMethod("parameterMap", details);
      setState(() {
        resultMethod = '${result["one"]}, ${result["two"]}';
        });
    } on PlatformException catch (e) {
        print("fail... ${e.message}");
      }
  }

  String? resultEvent;
  Future<Null> registerEventChannel() async {
    const channel = EventChannel("eventChannel");
    channel.receiveBroadcastStream().listen((dynamic event) {
      setState(() {
        resultEvent = 'receive event... $event';
      });
    }, onError: (dynamic error) {
      print('${error.message}');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Channel Test'),),
      body: Center(
        child: Column(
          children: [
            Text('resultMessage : ${resultMessage}'),
            Text('receiveMessage : ${receiveMessage}'),
            ElevatedButton(
                onPressed: () {
                  nativeCallWithMessageChannel();
                },
                child: Text('message...')
            ),
            Text('result method : ${resultMethod}'),
            ElevatedButton(
              onPressed: (){
                nativeCallWithMethodChannel();
              },
              child: Text('method'),
            ),
            Text('Event... $resultEvent'),
            ElevatedButton(
                onPressed: (){
                  registerEventChannel();
                },
                child: Text('event')
            )
          ],
        ),
      )
    );
  }
}