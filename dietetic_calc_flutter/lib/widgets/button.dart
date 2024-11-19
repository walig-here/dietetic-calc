import 'package:flutter/material.dart';

class MyButton extends StatelessWidget {
  final String label;
  final Function handleOnPressed;

  const MyButton({
    required this.label,
    required this.handleOnPressed
  });

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        handleOnPressed();
      },
      style: ElevatedButton.styleFrom(
        padding: EdgeInsets.symmetric(horizontal: 12, vertical: 12),
        backgroundColor: Color(0xff2c2c2c),
        shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(8),
          side: BorderSide(color: Color(0xff2c2c2c), width: 1),
        ),
      ),
      child: Padding(
        padding: const EdgeInsets.all(12),
        child: Text(
          label.toUpperCase(),
          style: TextStyle(
            fontSize: 14,
            color: Color(0xfff5f5f5),
            fontWeight: FontWeight.w500
          )
        ),
      )
    );
  }
}