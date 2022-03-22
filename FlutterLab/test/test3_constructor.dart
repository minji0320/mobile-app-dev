class User {
  String? name;
  int? age;

  // User(String name, int age) {
  //   this.name = name;
  //   this.age = age;
  // }

  // 아래처럼 줄여서 표현하는 게 더 좋음
  User(this.name, this.age);

  User.one() {

  }

  User.two(String name, int age) {

  }

  User.three(this.name, this.age);

  User.four(String name, int age): this.two(name, age);
}

class SingletonClass {
  int? data;
  // _를 붙이면 pirvate : 외부에서 접근 X
  SingletonClass._privateConstructor();
  static final SingletonClass _instance = SingletonClass._privateConstructor();
  factory SingletonClass() => _instance;
}

main() {
  User user1 = User('hello', 90);
  User user2 = User.one();

  SingletonClass obj1 = SingletonClass();
  SingletonClass obj2 = SingletonClass();
  obj1.data = 10;
  obj2.data = 20;
  print("${obj1.data}, ${obj2.data}");
}