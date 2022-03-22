import 'package:flutter/material.dart';

class ListWidget extends StatelessWidget {
  //목록을 구성하기 위한 데이터...
  var datas = ['AAA','BBB','CCC'];
  var subDatas = ['aaa','bbb','ccc'];

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: datas.length,
      itemBuilder: (context, position){//각 항목을 구성하기 위해서 자동 호출.. 두번째 매개변수가 항목 index...
        return InkWell(//다른 위젯을 항목의 root 로 사용해도 된다..
          //유저의 터치 이벤트 사각형 영역....
          onTap: () {
            print('tap... ${datas[position]}');
          },
          child: Column(
            children: [
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Padding(
                        padding: EdgeInsets.fromLTRB(12.0, 12.0, 12.0, 6.0),
                        child: Text(
                          datas[position],
                          style: TextStyle(fontSize: 22.0, fontWeight: FontWeight.bold),
                        ),
                      ),
                      Padding(
                        padding: EdgeInsets.fromLTRB(12.0, 12.0, 12.0, 6.0),
                        child: Text(
                          subDatas[position],
                          style: TextStyle(fontSize: 18.0),
                        ),
                      )
                    ],
                  ),
                  Padding(
                    padding: EdgeInsets.all(8.0),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Text(
                          "5m",
                          style: TextStyle(color:Colors.grey),
                        ),
                        Padding(
                          padding: EdgeInsets.all(8.0),
                          child:Icon(
                              Icons.star_border,
                              size: 35.0,
                              color: Colors.grey
                          ),
                        )
                      ],
                    ),
                  )
                ],
              ),
              Divider(
                height: 2.0,
                color: Colors.grey,
              )
            ],
          ),

        );

      },
    );
  }
}