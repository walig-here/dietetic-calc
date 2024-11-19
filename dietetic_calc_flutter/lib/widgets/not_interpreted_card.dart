import 'package:flutter/material.dart';


class NotInterpretedCard extends StatelessWidget {
  final String header;
  final String value;
  final String unit;

  const NotInterpretedCard({
    required this.header,
    required this.value,
    required this.unit,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 4, vertical: 4),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey, width: 0.5),
      ),
      width: double.infinity,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            header,
            style: TextStyle(fontSize: 20),
          ),
          SizedBox(height: 4),
          Text(
            "$value $unit",
            style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)
          )
        ],
      ),
    );
  }
}