//overflow menu 구성하기 위한 문자열
import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';

const List<String> _choices = <String>['신고', '게시물 알람 설정', '링크 복사', '공유하기'];

class HeaderWidget extends StatelessWidget {
  // overflow menu 선택 시 콜되는 개발자 함수
  void _select(String choice) {
    // toast
    Fluttertoast.showToast(
      msg: choice,
      toastLength: Toast.LENGTH_SHORT,
      gravity: ToastGravity.BOTTOM,
      backgroundColor: Colors.black,
      textColor: Colors.white,
      fontSize: 16.0
    );
  }

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Image.asset('images/lab_instagram_icon_0.jpg'),
        Container(
          padding: EdgeInsets.only(left: 16),
          child: Text(
            'instagram',
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
          )
        ),
        Spacer(),
        PopupMenuButton<String>(
          // overflow menu 구성
          onSelected: _select, // 확장 컨텐츠 항목 선택 시 유저 이벤트
          itemBuilder: (BuildContext context) {
            // 확장 컨텐츠는 어떻게 구성할 것인가
            return _choices.map<PopupMenuItem<String>>((String choice) {
              // 배열의 개수만큼 함수 호출, 리턴되는 widget을 모아서 리턴
              return PopupMenuItem<String>(
                value: choice,
                child: Text(choice),
              );
            }).toList();
          },
        )
      ],
    );
  }
}
