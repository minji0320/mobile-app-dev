import 'package:flutter/material.dart';

void main() {
  runApp(MainApp());
}

class MainApp extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MainAppState();
}

class _MainAppState extends State<MainApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp.router(
      title: 'Navigator 2.0 Test',
      routerDelegate: MyRouterDelegate(),
      routeInformationParser: MyRouteInformationParser(),
    );
  }
}

class MyRoutePath {
  String? id;

  MyRoutePath.home() : this.id = null;

  MyRoutePath.detail(this.id);
}

class MyRouteInformationParser extends RouteInformationParser<MyRoutePath> {
  //필수... override
  //최초 한번 호출
  @override
  Future<MyRoutePath> parseRouteInformation(
      RouteInformation routeInformation) async {
    final uri=Uri.parse(routeInformation.location ?? '/');
    print(
        'parseRouteInformation.. routeInformation.location : ${routeInformation.location}');
    if(uri.pathSegments.length >= 2){
      var remaining = uri.pathSegments[1];
      return MyRoutePath.detail(remaining);
    }else {
      return MyRoutePath.home();
    }
  }

//optional.. override
//모든 navigation 처리시마다 호출되며 맨 마지막에 호출된다.
//화면이 변경된후 현재의 navigation 정보를 앱 내에서 유지하기 위해서 사용된다.
// @override
// RouteInformation restoreRouteInformation(MyRoutePath configuration) {
//   print('restoreRouteInformation.. id : ${configuration.id}');
//   if (configuration.id != null)
//     return RouteInformation(location: '/detail/${configuration.id}');
//   else
//     return RouteInformation(location: '/');
// }
}

class MyRouterDelegate extends RouterDelegate<MyRoutePath>
    with ChangeNotifier, PopNavigatorRouterDelegateMixin<MyRoutePath> {
  String? selectId;

  //PopNavigatorRouterDelegateMixin 에 의해 꼭 정의 되어야 한다. 이 키 값으로 Navigator 가 식별되게 강제된다.
  final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

  //optional...
  //모든 라우팅이 결정된후 자동 호출.. 현재 delegate 에 의해 출력되는 route 정보..
  //이  property 에서 return 되는 path 정보가 있어야.. restoreRouteInformation 호출된다.
  // @override
  // MyRoutePath get currentConfiguration {
  //   print('currentConfiguration....');
  //   if (selectId != null) {
  //     return MyRoutePath.detail(selectId);
  //   } else {
  //     return MyRoutePath.home();
  //   }
  // }

  @override
  Widget build(BuildContext context) {
    print("MyRouterDelegate build...");
    // TODO: implement build
    return Navigator(
      key: navigatorKey,
      pages: [
        //어떻게 화면을 출력할지는 개발자 마음..
        //지금의 경우는 selected 가 있다면 stack 에 두개를 쌓겠다는 의미
        MaterialPage(child: HomeScreen(_handleOnPressed)),
        if(selectId != null) MaterialPage(child: DetailScreen(selectId))
      ],
      onPopPage: (route, result) {
        if (!route.didPop(result)) {
          return false;
        }

        // Update the list of pages by setting _selectedBook to null
        selectId = null;
        print('BookRouterDelegate..pop. notifyListeners call..');
        notifyListeners();
        return true;
      },
    );
  }
  //최초에 한번 호출된다.
  //초기 대응을 위해
  @override
  Future<void> setNewRoutePath(MyRoutePath configuration) async {
    print('MyRouterDelegate... setNewRoutePath ... id : ${configuration.id}');
    if(configuration.id != null){
      selectId=configuration.id;
    }
  }

  void _handleOnPressed(String id){
    selectId=id;
    print('RouterDelegate... notifylistener call..');
    notifyListeners();
  }
}

class HomeScreen extends StatelessWidget {

  final ValueChanged<String> onPressed;

  HomeScreen(this.onPressed);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
            child: Column(
              children: [
                Text('Home Screen'),
                ElevatedButton(
                  child: Text('go detail with 1'),
                  onPressed: () => onPressed('1'),
                ),
                ElevatedButton(
                  child: Text('go detail with 2'),
                  onPressed: () => onPressed('2'),
                )
              ],
            )),
      ),
    );
  }
}

class DetailScreen extends StatelessWidget {
  String? id;

  DetailScreen(this.id);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Center(
          child: Text('Two Screen $id'),
        ),
      ),
    );
  }
}