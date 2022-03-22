main() {
  int data1 = 10;
  bool? data2;
  double? data3;

  print(data1.isEven);
  print("$data1, $data2, $data3");

  data3 = 10.0;
  data3 = null;

  data3 = data1.toDouble();

  int a = 10;
  // a = "hello"

  var b = 10;
  // b = "hello";

  dynamic c = 10;
  c = "hello";

  List list = List.filled(3, null); // filled 라는 생성자를 이용하여 사이즈와 초기값 명시
  list[1] = 'hello';
  print('${list[0]}, ${list[1]}, ${list[2]}');

  // 2차원 배열
  List<List<int>> list2 = [[10, 20], [20, 30]];
  print(list2[0][0]);
}