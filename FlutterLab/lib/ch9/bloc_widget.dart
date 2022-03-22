import 'package:flutter/material.dart';
import 'package:bloc/bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

// bloc에 등록할 개발자 repository로 가정
// 위젯들의 공통 사항을 클래스로 단일화
// 특별한 작성규칙은 없음
// 위젯에서 직접 생성해서 사용해도 되지만
// bloc을 사용하고 있다면 bloc에 등록해 DI 개념으로 이용될 수 있게 해줌
class MyRepository {
  someFun() {
    print('someFun.....');
  }
}

// bloc : state 설계, event 설계
// bloc에 state 변경 흐름을 표현 하는 것이 event
// event 타입을 식별해서 state를 관리하는 것
// 단순하게는 enum으로 설계
// event 발생 시에 데이터까지 포함해서 발생해야 한다면, 클래스로 설계
abstract class CounterEvent {}

class IncrementEvent extends CounterEvent {}

class DecrementEvent extends CounterEvent {}

// bloc을 이용한다면 Bloc을 상속받을 클래스 작성
// 위젯에서 event 발생 시 실행
// event 발생 전까지 앱에서 유지되던 데이터를 event 발생 시에 참조할 수 있도록
// event에 의해 변경된 데이터를 내부에서 유지, 위젯에 전파

class BlocCounter extends Bloc<CounterEvent, int> {
  BlocCounter() : super(0) {
    // 생성되면서 state의 초기값을 상위 생성자에 전달
    // 과거 버전 : mapEventToState() 함수에서 처리
    // 현재 : 특별 함수를 등록시키지 않고 이벤트 핸들러 등록으로 처리
    on<IncrementEvent>((event, emit) {
      // 로직 실행, state 변경, 변경값 다시 발행
      emit(state + 1);
      // 여기서의 state property가 지칭하는 값은 이벤트 발생 전까지 bloc에서 유지되던 값
      // emit()에서 매개변수로 지칭하는 값이 새로 유지되어야 하는 값
    });
    on<DecrementEvent>((event, emit) {
      emit(state - 1);
    });
  }

  // event 발생 시에 실행됨
  // 이 함수 내에서 state 데이터를 유지할 수는 없고
  // event 타입이 전달됨으로 구체적으로 state 데이터가 변경되기 전의 처리 로직이 있다면, 이곳에서 작성할 수도 있다
  @override
  void onEvent(CounterEvent event) {
    super.onEvent(event);
  }

  @override
  void onTransition(Transition<CounterEvent, int> transition) {
    super.onTransition(transition);
    print('transition... ${transition}');
  }

  @override
  void onError(Object error, StackTrace stackTrace) {
    super.onError(error, stackTrace);
  }
}

class BlocWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return RepositoryProvider(
      create: (context) => MyRepository(),
      child: BlocProvider<BlocCounter>(
        create: (context) => BlocCounter(),
        child: CounterPage(),
      ),
    );
  }
}

class CounterPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final BlocCounter counterBloc = BlocProvider.of<BlocCounter>(context);
    final MyRepository repository =
        RepositoryProvider.of<MyRepository>(context);

    return BlocListener<BlocCounter, int>(
      // 하위 위젯에서 꼭 사용할 필요는 없음, 데이터 전달 시 처리 로직 필요할 경우에만 추가
      listener: (context, state) {
        Scaffold.of(context).showSnackBar(SnackBar(
          content: Text('${state}'),
          backgroundColor: Colors.red,
        ));
      },
      // 화면 구성을 위한 부분이어서 리턴 필요
      child: BlocBuilder<BlocCounter, int>(
        builder: (context, count) {
          return Row(
            children: [
              Text('Bloc : $count'),
              ElevatedButton(
                  onPressed: () {
                    counterBloc.add(IncrementEvent());
                    repository.someFun();
                  },
                  child: Text('increment')
              ),
              ElevatedButton(
                  onPressed: () {
                    counterBloc.add(DecrementEvent());
                    repository.someFun();
                  },
                  child: Text('decrement')
              )
            ],
          );
        },
      ),
    );
  }
}
