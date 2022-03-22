// typedef - type 재정의 : 함수 타입 선언에만 사용되는 것이 아니라 여러 곳에서 쓰임
typedef MyFunctionType<T, A> = T Function(A arg);

main() {
  void myFun(bool data, {String data1 = "hello", int data2 = 0}) {

  }

  myFun(true);
  myFun(true, data1: 'world', data2: 30);

  int some(int no) {
    return no * 10;
  }

  Function testFun(int Function(int a) arg) {
    print(arg(10));
    return some;
  }

  int testFun2(MyFunctionType<int, int> arg) {
    return arg(10);
  }

  print(testFun2(some));
}