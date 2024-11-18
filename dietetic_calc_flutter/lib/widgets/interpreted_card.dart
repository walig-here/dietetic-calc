import 'package:flutter/material.dart';


class InterpretedCard extends StatelessWidget {
  final String header;
  final String value;
  final List interpretation;

  const InterpretedCard({
    required this.header,
    required this.value,
    required this.interpretation,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: EdgeInsets.symmetric(horizontal: 12, vertical: 2),
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(8),
        border: Border.all(color: Colors.grey, width: 0.5),
      ),
      width: double.infinity,
      child: Padding(
        padding: const EdgeInsets.all(4.0),
        child: Column(
          children: [
            Text(
              header,
              style: TextStyle(fontSize: 20),
            ),
            SizedBox(height: 4),
            Text(
              value,
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold, color: interpretation[0]),
            ),
            SizedBox(height: 4),
            Text(
              interpretation[1],
              style: TextStyle(fontSize: 16),
            )
          ],
        ),
      ),
    );
  }
}