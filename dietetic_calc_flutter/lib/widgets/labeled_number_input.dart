import 'package:flutter/material.dart';


class LabeledNumberInput extends StatefulWidget {
  final String labelText;
  final Function handleOnChanged;

  const LabeledNumberInput({
    required this.labelText,
    required this.handleOnChanged
  });

  @override
  State<LabeledNumberInput> createState() => _LabeledNumberInputState();
}

class _LabeledNumberInputState extends State<LabeledNumberInput> {
  bool _isValidInput = false;
  final TextEditingController _textController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          widget.labelText,
          style: TextStyle(fontSize: 16, fontWeight: FontWeight.normal),
        ),
        SizedBox(height: 8),
        TextField(
          style: TextStyle(fontSize: 16),
          textAlignVertical: TextAlignVertical.center,
          keyboardType: TextInputType.number,
          controller: _textController,
          decoration: InputDecoration(
            contentPadding: EdgeInsets.symmetric(horizontal: 12, vertical: 12),
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(8),
              borderSide: BorderSide(
                color: _isValidInput ? Colors.grey : Colors.red,
                width: 0.5,
              ),
            ),
            enabledBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(8),
              borderSide: BorderSide(
                color: _isValidInput ? Colors.grey : Colors.red,
                width: 0.5,
              ),
            ),
            focusedBorder: OutlineInputBorder(
              borderRadius: BorderRadius.circular(8),
              borderSide: BorderSide(
                color: _isValidInput ? Colors.blue : Colors.red,
                width: 1.0,
              ),
            ),
          ),
          maxLines: 1,
          onChanged: (value) {
            setState(() {
              if (_isValid(value)) {
                _isValidInput = true;
                widget.handleOnChanged(int.parse(value), true);
              } else {
                _isValidInput = false;
                widget.handleOnChanged(-1, false);
              }
            });
          },
        ),
      ],
    );
  }

  bool _isValid(String value) {
    if (value.isEmpty) return false;
    var parsedValue = int.tryParse(value);
    return parsedValue != null;
  }
}

