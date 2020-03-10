import 'package:flutter/material.dart';
import 'package:calculator/Homepage.dart';

void main(){
  runApp(MyApp());
}
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Basic Calcutor',
      theme: ThemeData(
        primarySwatch: Colors.orange
      ),
      home: Homepage(),
    );
  }
}
