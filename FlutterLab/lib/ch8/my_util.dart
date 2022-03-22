import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

String _url = 'https://newsapi.org/v2/everything?q=travel&apiKey=079dac74a5f94ebdb990ecf61c8854b7&pageSize=3';

// 서버 데이터 VO
class Article {
  String author;
  String title;
  String description;
  String urlToImage;
  String publishedAt;
  
  Article(this.author, this.title, this.description, this.urlToImage, this.publishedAt);
}

// 데이터 획득 후 List 화면 구성
Widget getWidget(List<Article> datas) {
  return ListView.builder(
    itemCount: datas.length,
    itemBuilder: (BuildContext context, int position) {
      Column resultWidget = Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: EdgeInsets.only(left: 10, right: 10, top: 5, bottom: 5),
            child: Text(datas[position].title, style: TextStyle(fontWeight: FontWeight.bold),),
          ),
          Padding(
            padding: EdgeInsets.only(left: 10, right: 10, top: 5, bottom: 5),
            child: Text(datas[position].publishedAt, style: TextStyle(color: Colors.green),),
          ),
          Padding(
            padding: EdgeInsets.only(left: 10, right: 10, top: 5, bottom: 5),
            child: Text(datas[position].description,),
          ),
          Padding(
            padding: EdgeInsets.only(left: 10, right: 10, top: 5, bottom: 5),
            child: Image.network(datas[position].urlToImage),
          )
        ],
      );

      if(position != datas.length - 1) {
        resultWidget.children.add(
          Container(
            height: 15,
            color: Colors.green,
          )
        );
      }

      return resultWidget;
    },
  );
}

// 서버 연동에 의해 데이터 발행 함수, Future 타입으로
Future<List<Article>> getServerData(String page) async {
  String url = _url + '&page=${page}';
  print(url);

  http.Response response = await http.get(Uri.parse(url));
  List datas = json.decode(response.body)['articles'];
  List<Article> result = [];
  datas.forEach((element) {
    result.add(Article(
        element['source']['name'],
        element['title'],
        element['description'],
        element['urlToImage'],
        element['publishedAt'])
    );
  });

  return result;
}
