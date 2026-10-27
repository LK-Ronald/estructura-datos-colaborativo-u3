import 'dart:io';

class InputFinalizadaException implements Exception {
  const InputFinalizadaException();
}

class InputReader {
  String leerTexto(String prompt) {
    while (true) {
      stdout.write(prompt);
      final input = stdin.readLineSync();
      if (input == null) throw const InputFinalizadaException();
      final texto = input.trim();
      if (texto.isNotEmpty) return texto;
      print('[!] El campo no puede estar vacio.');
    }
  }

  double leerDouble(String prompt) {
    while (true) {
      stdout.write(prompt);
      final input = stdin.readLineSync();
      if (input == null) throw const InputFinalizadaException();
      final value = double.tryParse(input.trim());
      if (value != null) return value;
      print('[!] Ingrese un valor numerico valido.');
    }
  }

  int leerEntero(String prompt) {
    while (true) {
      stdout.write(prompt);
      final input = stdin.readLineSync();
      if (input == null) throw const InputFinalizadaException();
      final value = int.tryParse(input.trim());
      if (value != null) return value;
      print('[!] Ingrese una opcion numerica valida.');
    }
  }

  List<String> leerLista(String prompt) {
    while (true) {
      stdout.write(prompt);
      final input = stdin.readLineSync();
      if (input == null) throw const InputFinalizadaException();
      final texto = input.trim();
      if (texto.isEmpty) {
        print('[!] Debe ingresar al menos un identificador.');
        continue;
      }
      return texto
          .split(',')
          .map((item) => item.trim())
          .where((item) => item.isNotEmpty)
          .toList();
    }
  }
}
