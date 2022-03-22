import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class ProviderCounter with ChangeNotifier {
  int _count = 0;
  int get count => _count;

  void increment() {
    _count++;
    notifyListeners();
  }
}

class ProviderScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => ProviderCounter(),)
      ],
      child: Consumer<ProviderCounter>(
        builder: (context, counter, _) {
          return Row(
            children: [
              Text('Provider...'),
              Text('${counter.count}'),
              ElevatedButton(
                  onPressed: () {
                    counter.increment();
                  },
                  child: Text('increment')
              )
            ],
          );
        },
      ),
    );
  }
}