import 'package:flutter/material.dart';

class SendDataClass {
  String title;
  String message;
  SendDataClass(this.title, this.message);
}

void main() {
  runApp(MaterialApp(
    routes: {
      '/': (context) => FirstScreen(),
      '/second': (context) => SecondScreen(),
    },
  ));
}

class FirstScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('First'),
      ),
      body: Center(
        child: ElevatedButton(
          child: Text('launch screen'),
          onPressed: () {
            _navigate(context);
          },
        ),
      ),
    );
  }
}

class SecondScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    Map<String, Object> args = ModalRoute.of(context)?.settings.arguments as Map<String, Object>;

    return Scaffold(
      appBar: AppBar(
        title: Text('${args["arg1"]}, ${args["arg2"]}, ${(args["model"] as SendDataClass).title}'),
      ),
      body: Center(
        child: ElevatedButton(
          child: Text('Go Back'),
          onPressed: () {
            Navigator.pop(context, "yes...");
          },
        ),
      ),
    );
  }
}

_navigate(BuildContext context) async {
  final result = await Navigator.pushNamed(context, '/second',
    arguments: {
      "arg1": "hello",
      "arg2": "world",
      "model": SendDataClass('myTitle', 'hello world')
    }
  );
  print('$result');
}