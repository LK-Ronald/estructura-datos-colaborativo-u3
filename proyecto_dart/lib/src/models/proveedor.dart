import '../exceptions/domain_exception.dart';

class Proveedor {
  Proveedor({
    required this.nit,
    required this.nombre,
    required this.direccion,
    required this.email,
  }) {
    _validarTexto(nit, 'nit');
    _validarTexto(nombre, 'nombre');
    _validarTexto(direccion, 'descripcion');
    _validarTexto(email, 'email');
  }

  final String nit;
  final String nombre;
  final String direccion;
  final String email;

  static void _validarTexto(String valor, String campo) {
    if (valor.trim().isEmpty) {
      throw CampoObligatorioException(campo);
    }
  }

  @override
  bool operator ==(Object other) =>
      other is Proveedor &&
      other.nit == nit &&
      other.nombre == nombre &&
      other.direccion == direccion &&
      other.email == email;

  @override
  int get hashCode => Object.hash(nit, nombre, direccion, email);

  @override
  String toString() =>
      'Proveedor{nit: $nit, nombre: $nombre, direccion: $direccion, '
      'email: $email}';
}
