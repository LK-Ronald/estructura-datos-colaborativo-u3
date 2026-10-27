import '../enums/prioridad.dart';
import '../exceptions/domain_exception.dart';
import '../models/orden.dart';
import '../models/producto.dart';
import '../models/proveedor.dart';
import '../repository/sistema_repository.dart';
import 'calcular_total_service.dart';

class GestorSistemaService {
  final SistemaRepository _sistemaRepository = SistemaRepository();
  final CalcularTotalService _calcularTotalService = CalcularTotalService();

  void agregarProveedor(
    String nit,
    String nombre,
    String direccion,
    String email,
  ) {
    _sistemaRepository.agregarProveedor(
      Proveedor(nit: nit, nombre: nombre, direccion: direccion, email: email),
    );
  }

  void agregarProducto(
    String id,
    String nombre,
    String descripcion,
    double precio,
  ) {
    _sistemaRepository.agregarProducto(
      Producto(
        id: id,
        nombre: nombre,
        descripcion: descripcion,
        precio: precio,
      ),
    );
  }

  void realizarOrden(String prioridad, String nit, List<String> idsProductos) {
    final proveedor = _sistemaRepository.buscarProveedorPorNit(nit);
    if (proveedor == null) throw ProveedorNotExistException(nit);

    final prioridadOrden = Prioridad.fromString(prioridad);
    final productos = idsProductos.map((id) {
      final producto = _sistemaRepository.buscarProductoPorId(id);
      if (producto == null) throw ProductoNotExistException(id);
      return producto;
    }).toList();

    _sistemaRepository.realizarOrden(
      Orden(
        prioridad: prioridadOrden,
        proveedor: proveedor,
        productos: productos,
        total: _calcularTotalService.calcularTotal(productos),
      ),
    );
  }

  Proveedor? buscarProveedorPorNit(String? nit) {
    if (nit == null) throw CampoObligatorioException('nit');
    return _sistemaRepository.buscarProveedorPorNit(nit);
  }

  Producto? buscarProductoPorId(String? id) {
    if (id == null) throw CampoObligatorioException('id');
    return _sistemaRepository.buscarProductoPorId(id);
  }

  Orden? buscarOrdenPorId(String? id) {
    if (id == null) throw CampoObligatorioException('id');
    return _sistemaRepository.buscarOrdenPorId(id);
  }

  List<Proveedor> mostrarProveedores() =>
      _sistemaRepository.mostrarProveedores();

  List<Producto> mostrarProductos() => _sistemaRepository.mostrarProductos();

  List<Orden> mostrarOrdenesPorEstado() =>
      _sistemaRepository.mostrarOrdenesPorEstado();

  void procesarOrden() => _sistemaRepository.procesarOrden();

  List<Orden> mostrarOrdenesEntregadas() =>
      _sistemaRepository.mostrarOrdenesEntregadas();

  List<Orden> mostrarOrdenesCanceladas() =>
      _sistemaRepository.mostrarOrdenesCanceladas();

  List<Orden> mostrarOrdenesPorPrioridad() =>
      _sistemaRepository.mostrarOrdenesPorPrioridad();

  void eliminarProveedor(String? nit) =>
      _sistemaRepository.eliminarProveedor(nit);

  void eliminarProducto(String? id) => _sistemaRepository.eliminarProducto(id);

  void cancelarOrden(String? id) => _sistemaRepository.cancelarOrden(id);
}
