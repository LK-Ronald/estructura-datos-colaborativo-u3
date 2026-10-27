import '../exceptions/domain_exception.dart';

class Producto {
  Producto({
    required this.id,
    required this.nombre,
    required this.descripcion,
    required this.precio,
  }) {
    _validarTexto(id, 'id');
    _validarTexto(nombre, 'nombre');
    _validarTexto(descripcion, 'descripcion');
    if (!precio.isFinite || precio <= 0) {
      throw PrecioInvalidoException();
    }
  }

  final String id;
  final String nombre;
  final String descripcion;
  final double precio;

  static void _validarTexto(String valor, String campo) {
    if (valor.trim().isEmpty) {
      throw CampoObligatorioException(campo);
    }
  }

  @override
  bool operator ==(Object other) =>
      other is Producto &&
      other.id == id &&
      other.nombre == nombre &&
      other.descripcion == descripcion &&
      other.precio == precio;

  @override
  int get hashCode => Object.hash(id, nombre, descripcion, precio);

  @override
  String toString() =>
      'Producto{id: $id, nombre: $nombre, descripcion: $descripcion, '
      'precio: $precio}';
}
