import 'package:flutter/material.dart';

//입력 데이터 추상화 vo
class User {
  String? firstName = '';
  String? lastName = '';
  bool isCheck = false;
}

class FormWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return FormWidgetState();
  }
}

class FormWidgetState extends State<FormWidget>{
  //form 을 이용할때 key 값으로 이용해야 해서..
  final _formKey = GlobalKey<FormState>();
  final _user = User();

  //최종 save 시 호출될 개발자 함수..
  _save() {
    print('save.... ${_user.firstName}, ${_user.lastName}, ${_user.isCheck}');
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      key: _formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          TextFormField(
            decoration: InputDecoration(labelText: 'First Name'),
            //form  의 validate() 함수 호출시 form 에 속한 모든 위젯의 validator() 호출된다..
            //null - valid, message - 에러메시지..
            validator: (value){
              if(value?.isEmpty ?? false){
                return 'please enter first name....';//화면 출력 에러 메시지...
              }
              return null;
            },
            //form 의 save 함수 호출시 자동 호출...
            onSaved: (val) => setState(() {
              _user.firstName = val;
            }),
          ),
          TextFormField(
            decoration: InputDecoration(labelText: 'Last Name'),
            //form  의 validate() 함수 호출시 form 에 속한 모든 위젯의 validator() 호출된다..
            //null - valid, message - 에러메시지..
            validator: (value){
              if(value?.isEmpty ?? false){
                return 'please enter last name....';//화면 출력 에러 메시지...
              }
              return null;
            },
            //form 의 save 함수 호출시 자동 호출...
            onSaved: (val) => setState(() {
              _user.lastName = val;
            }),
          ),
          SwitchListTile(
              title: Text('check'),
              value: _user.isCheck,
              onChanged: (bool val){
                setState(() {
                  _user.isCheck = val;
                });
              }
          ),
          ElevatedButton(
              onPressed: () {
                final form = _formKey.currentState;
                if(form?.validate() ?? false){//이 순간 모든 하위 field 의 validator 에 명시한 함수 호출된다..
                  //모두 null 이면  true...
                  //하나라도 데이터 return 되면 전체 false.. 리턴된 데이터를 에러 메시지로 봐서.. 각 필드 화면
                  //하단에 찍어준다..
                  form?.save();//모든 field 의 onSaved() 함수 호출되어 각각의 데이터가 저장되게 하고..
                  _save();
                }
              },
              child: Text('SAVE')
          )
        ],
      ),
    );
  }
}