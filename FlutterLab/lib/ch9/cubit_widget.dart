import 'package:flutter/material.dart';
import 'package:bloc/bloc.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

//Cubit.. Bloc 에서 제공되는 또다른 방식...
//bloc 의 추상화 개념정도로...상태 변경을 위한 Event Driven 방식을 사용하지 않고.. 함수를 노출... 시켜
//쉽게 state 값 변경...

class CounterCubit extends Cubit<int>{
  CounterCubit(): super(0);//state 초기값...
  void increment() => emit(state + 1);
  void decrement() => emit(state - 1);
}

class CubitWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (_) => CounterCubit(),
      child: CubitCounterPage(),
    );
  }
}

class CubitCounterPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<CounterCubit, int>(
      builder: (context, count){
        return Row(
          children: [
            Text('Cubit : $count'),
            ElevatedButton(
                onPressed: (){
                  context.read<CounterCubit>().increment();
                },
                child: Text('increment')
            ),
            ElevatedButton(
                onPressed: (){
                  context.read<CounterCubit>().decrement();
                },
                child: Text('decrement')
            )
          ],
        );
      },
    );
  }
}