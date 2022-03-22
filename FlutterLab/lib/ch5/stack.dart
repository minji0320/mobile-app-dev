import 'package:flutter/material.dart';
import 'package:page_indicator/page_indicator.dart';

// 각 화면의 데이터
class DataVO {
  String image;
  String title;
  String content;
  String date;
  String location;

  DataVO(this.image, this.title, this.content, this.date, this.location);
}

List<DataVO> datas = [
  DataVO('images/lab_lotte_1.jpg', '롯데웨딩위크', '각 지점 본 매장', '2.14(금) ~ 2.23(일)',
      '백화점 전점'),
  DataVO('images/lab_lotte_2.jpg', 'LG TROMM 스타일러', '각 지점 본 매장',
      '2.14(금) ~ 2.29(토)', '백화점 전점'),
  DataVO(
      'images/lab_lotte_3.jpg', '금양와인 페스티발', '본매장', '2.15(토) ~ 2.20(목)', '잠실점'),
  DataVO('images/lab_lotte_4.jpg', '써스데이 아일랜드', '본 매장', '2.17(월) ~ 2.23(일)',
      '잠실점'),
  DataVO('images/lab_lotte_5.jpg', '듀풍클래식', '본 매장', '2.21(금) ~ 3.8(일)', '잠실점'),
];

// 하나의 card 화면을 위한 widget
class CardADWidget extends StatelessWidget {
  DataVO vo;
  CardADWidget(this.vo);

  @override
  Widget build(BuildContext context) {
    return Stack(
      children: [
        Container(
          color: Colors.pink,
        ),
        Align(
          alignment: Alignment(0.0, 0.0),
          child: Stack(
            children: [
              Column(
                  mainAxisSize: MainAxisSize.min,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Image.asset(vo.image, width: 350,),
                    Container(
                      width: 350,
                      height: 100,
                      color: Colors.white,
                      padding: EdgeInsets.only(left: 16, top: 8, bottom: 8),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            vo.title,
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold
                            ),
                          ),
                          Spacer(),
                          Text(vo.content),
                          Text(vo.date)
                        ],
                      ),
                    )
                  ]
              ),
              Positioned(
                left: 30,
                bottom: 90,
                child: Container(
                  padding: EdgeInsets.all(10),
                  color: Colors.black,
                  child: Text(
                    vo.location,
                    style: TextStyle(
                        color: Colors.white
                    ),
                  ),
                ),
              )
            ],
          ),
        )
      ],
    );
  }
}

// 전체 본문 화면. ViewPager에 의해 화면 전환
class MyWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return MyWidgetState();
  }

}

class MyWidgetState extends State<MyWidget> {
  // 전체 카드를 표현하는 위젯들을 List로 반환하는 개발자 함수
  List<CardADWidget> makeViewPagerWidget() {
    return datas.map((vo) {
      return CardADWidget(vo);
    }).toList();
  }

  PageController _controller = PageController(
    initialPage: 0,
    viewportFraction: 0.9, // 옆에 있는 위젯이 어느 정도 보여야 하는지에 대한 설정
  );

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return PageIndicatorContainer(
      child: PageView(
        controller: _controller,
        children: makeViewPagerWidget(),
      ),
      indicatorColor: Colors.white,
      indicatorSelectorColor: Colors.blue,
      length: datas.length,
      shape: IndicatorShape.roundRectangleShape(size: Size(20, 3)),
      indicatorSpace: 1,
    );
  }
}

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Layout Test'),
        ),
        body: SafeArea( // 미 사용 시, 디바이스에 따라 인디케이터가 나오지 않을 수 있음
          child: Container(
            color: Colors.pink,
            child: MyWidget(),
          ),
        )
      ),
    );
  }
}

