import 'dart:math';

import '../enums/estado.dart';
import '../enums/prioridad.dart';
import '../exceptions/domain_exception.dart';
import 'producto.dart';
import 'proveedor.dart';

class Orden {
  Orden({
    required Prioridad prioridad,
    required this.proveedor,
    required List<Producto> productos,
    required this.total,
  }) : id = _generarId(),
       _estado = Estado.creado,
       // The public constructor name is retained for its callers.
       // ignore: prefer_initializing_formals
       _prioridad = prioridad,
       productos = List.unmodifiable(productos),
       fecha = DateTime.now();

  final String id;
  Estado _estado;
  Prioridad _prioridad;
  final Proveedor proveedor;
  final List<Producto> productos;
  final double total;
  final DateTime fecha;

  Estado get estado => _estado;

  Prioridad get prioridad => _prioridad;

  void cambiarEstado(Estado? nuevoEstado) {
    if (nuevoEstado == null) {
      throw CampoObligatorioException('estado');
    }
    if (_estado == Estado.cancelado) {
      throw EstadoInvalidoException.cancelado();
    }
    if (_estado == Estado.entregado) {
      throw EstadoInvalidoException.entregado();
    }
    _estado = nuevoEstado;
  }

  void cambiarPrioridad(Prioridad? nuevaPrioridad) {
    if (nuevaPrioridad == null) {
      throw CampoObligatorioException('prioridad');
    }
    _prioridad = nuevaPrioridad;
  }

  static String _generarId() {
    final random = Random.secure();
    final bytes = List<int>.generate(16, (_) => random.nextInt(256));
    bytes[6] = (bytes[6] & 0x0f) | 0x40;
    bytes[8] = (bytes[8] & 0x3f) | 0x80;
    final hex = bytes.map((byte) => byte.toRadixString(16).padLeft(2, '0'));
    final value = hex.join();
    return '${value.substring(0, 8)}-${value.substring(8, 12)}-'
        '${value.substring(12, 16)}-${value.substring(16, 20)}-'
        '${value.substring(20)}';
  }

  @override
  bool operator ==(Object other) => other is Orden && other.id == id;

  @override
  int get hashCode => id.hashCode;

  @override
  String toString() =>
      'Orden{id: $id, estado: $estado, prioridad: $prioridad, '
      'proveedor: $proveedor, productos: $productos, total: $total, '
      'fecha: $fecha}';
}
