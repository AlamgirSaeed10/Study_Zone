import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class Homepage extends StatefulWidget {

   @override
  _HomepageState createState() => _HomepageState();
}

class _HomepageState extends State<Homepage> {

  int firstNum,secNum;
  String textToDisplay ="";
  String result="";
  String operationToPerform ="";
  void button_clicked(String btnText)
  {
    if(btnText =="C") {
      firstNum = 0;
      secNum = 0;
      textToDisplay = "";
      operationToPerform ="";
      result = "";
    }
     else if(btnText == "+" ||btnText == "-" ||btnText == "X" ||btnText == "/" )
          {
            firstNum = int.parse(textToDisplay);
            result = "";
            operationToPerform = btnText;
          }
      else  if(btnText == "=")
          {
            secNum = int.parse(textToDisplay);
            if(operationToPerform == "+") {
              result = (firstNum + secNum ).toString();
            }
            if(operationToPerform == "-") {
              result = (firstNum - secNum ).toString();
            }
            if(operationToPerform == "X") {
              result = (firstNum * secNum ).toString();
            }
            if(operationToPerform == "/") {
              result = (firstNum / secNum ).toString();
            }
          }
        else{
          result = int.parse(textToDisplay + btnText).toString();
        }
        setState(() {
          textToDisplay = result;
        });

  }
  Widget custom_button(String text)
  {
    return Expanded(
      child: OutlineButton(
        padding: EdgeInsets.all(20.0),
        onPressed: () => button_clicked(text),
        child: Text("$text",
          style: TextStyle(
            fontSize: 25.0,
            fontFamily: "Transformers",
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Calculator"),
      ),
      body: Container(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.end,
          children: <Widget>[
            Expanded(
              child: Container(
                alignment: Alignment.bottomRight,
                padding: EdgeInsets.all(25.0),
                child: Text("$textToDisplay",
                  style: TextStyle(
                    fontSize: 50.0,
                    fontFamily: "Times New Roman",
                  ),
                ),
              ),
            ),

            Row(
              children: <Widget>[
                custom_button("9"),
                custom_button("8"),
                custom_button("7"),
                custom_button("+"),
              ],
            ),
            Row(
              children: <Widget>[
                custom_button("6"),
                custom_button("5"),
                custom_button("4"),
                custom_button("-"),
              ],
            ),
            Row(
              children: <Widget>[
                custom_button("3"),
                custom_button("2"),
                custom_button("1"),
                custom_button("X"),
              ],
            ),
            Row(
              children: <Widget>[
                custom_button("C"),
                custom_button("0"),
                custom_button("="),
                custom_button("/"),
              ],
            )
          ],
        ),
      ),
    );
  }
}
