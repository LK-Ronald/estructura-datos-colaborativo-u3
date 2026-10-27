import 'package:collection/collection.dart';

import '../enums/estado.dart';
import '../exceptions/domain_exception.dart';
import '../models/orden.dart';
import '../models/producto.dart';
import '../models/proveedor.dart';

class SistemaRepository {
  final Map<String, Proveedor> _proveedores = {};
  final Map<String, Producto> _productos = {};
  final HeapPriorityQueue<Orden> _ordenes = HeapPriorityQueue<Orden>(
    _compararOrdenes,
  );
  final Map<String, Orden> _ordenesEntregadas = {};
  final Map<String, Orden> _ordenesCanceladas = {};

  void agregarProveedor(Proveedor proveedor) {
    if (_proveedores.containsKey(proveedor.nit)) {
      throw ProveedorDuplicatedException(proveedor.nit);
    }
    _proveedores[proveedor.nit] = proveedor;
  }

  void agregarProducto(Producto producto) {
    if (_productos.containsKey(producto.id)) {
      throw ProductoDuplicatedException(producto.id);
    }
    _productos[producto.id] = producto;
  }

  void realizarOrden(Orden orden) {
    if (_ordenes.contains(orden)) {
      throw OrdenDuplicatedException();
    }
    if (!_proveedores.containsKey(orden.proveedor.nit)) {
      throw ProveedorNotExistException(orden.proveedor.nit);
    }
    for (final producto in orden.productos) {
      if (!_productos.containsKey(producto.id)) {
        throw ProductoNotExistException(producto.id);
      }
    }
    _ordenes.add(orden);
  }

  Proveedor? buscarProveedorPorNit(String? nit) =>
      nit == null ? null : _proveedores[nit];

  Producto? buscarProductoPorId(String? id) =>
      id == null ? null : _productos[id];

  Orden? buscarOrdenPorId(String? id) {
    if (id == null) return null;
    for (final orden in _ordenes.unorderedElements) {
      if (orden.id == id) return orden;
    }
    return null;
  }

  List<Proveedor> mostrarProveedores() =>
      List.unmodifiable(_proveedores.values);

  List<Producto> mostrarProductos() => List.unmodifiable(_productos.values);

  List<Orden> mostrarOrdenesPorEstado() {
    final ordenes = _ordenes.unorderedElements.toList();
    ordenes.sort((a, b) => a.estado.index.compareTo(b.estado.index));
    return ordenes;
  }

  void procesarOrden() {
    if (_ordenes.isEmpty) return;

    final orden = _ordenes.first;
    switch (orden.estado) {
      case Estado.creado:
        orden.cambiarEstado(Estado.enviado);
      case Estado.enviado:
        orden.cambiarEstado(Estado.enCamino);
      case Estado.enCamino:
        orden.cambiarEstado(Estado.entregado);
      case Estado.entregado:
      case Estado.cancelado:
        break;
    }

    if (orden.estado == Estado.entregado) {
      _ordenes.remove(orden);
      _ordenesEntregadas[orden.id] = orden;
    }
  }

  List<Orden> mostrarOrdenesEntregadas() =>
      List.unmodifiable(_ordenesEntregadas.values);

  List<Orden> mostrarOrdenesCanceladas() =>
      List.unmodifiable(_ordenesCanceladas.values);

  List<Orden> mostrarOrdenesPorPrioridad() {
    final ordenes = _ordenes.unorderedElements.toList();
    ordenes.sort(_compararOrdenes);
    return ordenes;
  }

  void eliminarProveedor(String? nit) {
    if (nit == null || _proveedores.remove(nit) == null) return;
    _eliminarOrdenes((orden) => orden.proveedor.nit == nit);
    _eliminarOrdenesArchivadas(
      _ordenesEntregadas,
      (orden) => orden.proveedor.nit == nit,
    );
    _eliminarOrdenesArchivadas(
      _ordenesCanceladas,
      (orden) => orden.proveedor.nit == nit,
    );
  }

  void eliminarProducto(String? id) {
    if (id == null || _productos.remove(id) == null) return;
    bool contieneProducto(Orden orden) =>
        orden.productos.any((producto) => producto.id == id);
    _eliminarOrdenes(contieneProducto);
    _eliminarOrdenesArchivadas(_ordenesEntregadas, contieneProducto);
    _eliminarOrdenesArchivadas(_ordenesCanceladas, contieneProducto);
  }

  void cancelarOrden(String? id) {
    if (id == null) return;
    final orden = buscarOrdenPorId(id);
    if (orden == null) return;
    _ordenes.remove(orden);
    orden.cambiarEstado(Estado.cancelado);
    _ordenesCanceladas[orden.id] = orden;
  }

  void _eliminarOrdenes(bool Function(Orden) debeEliminar) {
    final ordenes = _ordenes.unorderedElements.where(debeEliminar).toList();
    for (final orden in ordenes) {
      _ordenes.remove(orden);
    }
  }

  void _eliminarOrdenesArchivadas(
    Map<String, Orden> archivo,
    bool Function(Orden) debeEliminar,
  ) {
    archivo.removeWhere((_, orden) => debeEliminar(orden));
  }

  static int _compararOrdenes(Orden a, Orden b) {
    final prioridad = a.prioridad.valor.compareTo(b.prioridad.valor);
    if (prioridad != 0) return prioridad;
    return a.total.compareTo(b.total);
  }
}
