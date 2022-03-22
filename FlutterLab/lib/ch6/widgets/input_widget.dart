import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

enum Platforms { android, ios }

class InputWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return InputWidgetState();
  }
}

class InputWidgetState extends State<InputWidget>{
  //TextField 를 이용하면 유저 입력 순간의 이벤트 처리.. 유저 입력 데이터 획득등 필요하다..
  final idController = TextEditingController();
  final pwController = TextEditingController();

  bool? isChecked = false;
  Platforms? platform = Platforms.android;
  DateTime dateValue = DateTime.now();
  TimeOfDay timeValue = TimeOfDay.now();
  double slideValue = 0.0;
  bool switchOn = false;

  //TextField 에 유저 데이터 입력 순간의 이벤트 핸들러...
  _printLatestValue() {
    print('${idController.text}, ${pwController.text}');
  }

  //유저에게 날짜를 입력받기 위한 다이얼로그 띄우는 개발자 함수..
  //비동기 처리를 위해 async await 로... async 로 처리하는 함수의 리턴 타입은 Future 이어야 한다...
  Future datePicker() async {
    //달력 다이얼로그 띄우고.. 유저가 선택한 값이 리턴...
    DateTime? picked = await showDatePicker(
        context: context,
        initialDate: DateTime.now(),
        firstDate: DateTime(2016),
        lastDate: DateTime(2030)
    );

    if(picked != null) setState(() => dateValue=picked);

  }

  Future timePicker() async {
    TimeOfDay? selectedTime = await showTimePicker(
        context: context ,
        initialTime: TimeOfDay.now()
    );
    if(selectedTime != null) setState(() {
      timeValue = selectedTime;
    });

  }
  //slider 조정시 호출....
  setSliderValue(double value){
    setState(() {
      slideValue = value;
    });
  }
  onSwitchChanged(bool value){
    setState(() {
      switchOn = value;
    });
  }

  @override
  void initState() {
    super.initState();
    //controller 에 이벤트 걸지 않아도 유저가 입력한 데이터는 알아서 저장된다..
    //순간의 이벤트가 필요한 경우에만..
    idController.addListener(_printLatestValue);
    pwController.addListener(_printLatestValue);
  }

  @override
  void dispose() {
    super.dispose();
    idController.dispose();
    pwController.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          TextField(
            style: TextStyle(fontSize: 15.0),
            decoration: InputDecoration(
                labelText: 'id'
            ),
            controller: idController,
          ),
          TextField(
            style: TextStyle(fontSize: 15.0),
            decoration: InputDecoration(
                labelText: 'password'
            ),
            controller: pwController,
            obscureText: true,
          ),
          Checkbox(
              value: isChecked,
              onChanged: (bool? value){
                setState((){
                  isChecked = value;
                });
              }
          ),
          Text('${isChecked}'),
          ListTile(
            title: Text('android'),
            leading: Radio(
              value: Platforms.android,
              groupValue: platform,
              onChanged: (Platforms? value){
                setState(() {
                  platform = value;
                });
              },
            ),
          ),
          ListTile(
            title: Text('ios'),
            leading: Radio(
              value: Platforms.ios,
              groupValue: platform,
              onChanged: (Platforms? value){
                setState(() {
                  platform = value;
                });
              },
            ),
          ),
          Text('${platform}'),
          ElevatedButton(
              onPressed: (){
                datePicker();
              },
              child: Text('DatePicker')
          ),
          Text('date : ${DateFormat('yyyy-MM-dd').format(dateValue)}'),
          ElevatedButton(
              onPressed: (){
                timePicker();
              },
              child: Text('TimePicker')
          ),
          Text('time : ${timeValue.hour}:${timeValue.minute}'),
          Slider(
              min: 0,
              max: 10,
              value: slideValue,
              onChanged: setSliderValue
          ),
          Switch(
              value: switchOn,
              onChanged: onSwitchChanged
          ),
          Text('switch : ${switchOn}'),
          ElevatedButton(
              onPressed: (){
                print('submit : ${idController.text}, ${pwController.text}');
              },
              child: Text('submit')
          )

        ],
      ),
    );
  }
}

