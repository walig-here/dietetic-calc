import 'package:flutter/material.dart';

class LabeledSelectionMenu extends StatefulWidget {
  final List<String> options;
  final String label;
  final String placeholder;
  final Function handleOnSelected;

  const LabeledSelectionMenu({
    required this.options,
    required this.label,
    required this.placeholder,
    required this.handleOnSelected,
  });

  @override
  State<LabeledSelectionMenu> createState() => _LabeledSelectionMenuState();
}

class _LabeledSelectionMenuState extends State<LabeledSelectionMenu> {
  bool _isValidInput = false;
  String selectedValue = "";

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          widget.label,
          style: TextStyle(fontSize: 16, fontWeight: FontWeight.normal),
        ),
        SizedBox(height: 8),
        DropdownMenu(
          width: double.infinity,
          dropdownMenuEntries: widget.options.map((String e) {
            return DropdownMenuEntry(value: e, label: e);
          }).toList(),
          hintText: widget.placeholder,
          inputDecorationTheme: InputDecorationTheme(
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
          onSelected: (value) {
            setState(() {
              if (_isValid(value)) {
                _isValidInput = true;
                widget.handleOnSelected(value, true);
              } else {
                _isValidInput = false;
                widget.handleOnSelected(value, false);
              }
            });
          },
        ),
      ],
    );
  }

  bool _isValid(String? value) {
    return value != null && value.isNotEmpty;
  }
}
