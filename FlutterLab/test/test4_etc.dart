class Super {
  Super(name) {}
  Super.one(name){}
  some() {}
}

class Sub extends Super {
  Sub(name) : super(name) {}
  Sub.one(name):super.one(name) {}
  @override
  some() {
    super.some();
  }
}

// Property
class User {
  String? _name;

  String? get name{
    return _name?.toUpperCase();
  }
  set name(value) {
    _name=name;
  }
}

// Interface
class MyClass {
  int no = 0;
  String name="hello";
  MyClass(this.no, this.name);

  String some() {
    return "hello";
  }
}

class SubClass extends MyClass {
  SubClass(no, name) : super(no, name);
}

class InterfaceClass implements MyClass {
  @override
  int no = 10;

  @override
  set name(String _name) {

  }

  @override
  String get name => "kim";

  @override
  String some() {
    // TODO: implement some
    throw UnimplementedError();
  }
}

// Mixin
class A {}
mixin MyMixin on A {
  int mixinData = 0;
  void mixinSome() {}
}

class MixinClass extends A with MyMixin {
  some() {
    mixinData = 20;
    mixinSome();
  }
}

main() {
  User obj = User();
  obj.name = "hello";
  print(obj.name);
}