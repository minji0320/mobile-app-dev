import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            appBar: AppBar(
              title: Text("Hello"),
            ),
            body: Center(
                child: Column(
              children: [MyStatelessWidget(), MyStatefulWidget()],
            )),
            floatingActionButton: FloatingActionButton(
              onPressed: () {},
              child: Icon(Icons.add),
            )));
  }
}

class MyStatefulWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyState();
  }
}

class MyState extends State<MyStatefulWidget> {
  bool isFavorite = false;
  int favoriteCount = 10;

  void toggleFavorite() {
    print("stateful... toggleFavorite.. $favoriteCount");
    setState(() {
      if (isFavorite) {
        favoriteCount -= 1;
        isFavorite = false;
      } else {
        favoriteCount += 1;
        isFavorite = true;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    print("statelful... build.....");
    return Row(
      children: [
        IconButton(
          onPressed: toggleFavorite,
          icon: (isFavorite ? Icon(Icons.star) : Icon(Icons.star_border)),
          color: Colors.red,
        ),
        Container(
          child: Text("$favoriteCount"),
        )
      ],
    );
  }
}

class MyStatelessWidget extends StatelessWidget {
  // 변수 가질 수 있고, 값 변경 가능
  // 하지만 re-rendering은 안됨
  bool isFavorite = false;
  int favoriteCount = 10;

  void toggleFavorite() {
    print("stateless... toggleFavorite.. $favoriteCount");
    if (isFavorite) {
      favoriteCount -= 1;
      isFavorite = false;
    } else {
      favoriteCount += 1;
      isFavorite = true;
    }
  }

  @override
  Widget build(BuildContext context) {
    print("stateless... build.....");
    return Row(
      children: [
        IconButton(
          onPressed: toggleFavorite,
          icon: (isFavorite ? Icon(Icons.star) : Icon(Icons.star_border)),
          color: Colors.red,
        ),
        Container(
          child: Text("$favoriteCount"),
        )
      ],
    );
  }
}
