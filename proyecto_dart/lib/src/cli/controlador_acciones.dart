import '../exceptions/domain_exception.dart';
import '../services/gestor_sistema_service.dart';
import 'input_reader.dart';

class ControladorAcciones {
  ControladorAcciones(this.service, this.reader);

  final GestorSistemaService service;
  final InputReader reader;

  void agregarProveedor() => _ejecutar(() {
    service.agregarProveedor(
      reader.leerTexto('nit: '),
      reader.leerTexto('nombre: '),
      reader.leerTexto('direccion: '),
      reader.leerTexto('email: '),
    );
    print('[OK] Proveedor agregado.');
  });

  void agregarProducto() => _ejecutar(() {
    service.agregarProducto(
      reader.leerTexto('id: '),
      reader.leerTexto('nombre: '),
      reader.leerTexto('descripcion: '),
      reader.leerDouble('precio: '),
    );
    print('[OK] Producto agregado.');
  });

  void realizarOrden() => _ejecutar(() {
    service.realizarOrden(
      reader.leerTexto('prioridad (ALTA/MEDIA/BAJA): '),
      reader.leerTexto('nit (proveedor): '),
      reader.leerLista('idsProductos (separados por coma): '),
    );
    print('[OK] Orden realizada.');
  });

  void buscarProveedorPorNit() => _ejecutar(() {
    final proveedor = service.buscarProveedorPorNit(reader.leerTexto('nit: '));
    _mostrarResultado(proveedor, 'Proveedor no encontrado.');
  });

  void buscarProductoPorId() => _ejecutar(() {
    final producto = service.buscarProductoPorId(reader.leerTexto('id: '));
    _mostrarResultado(producto, 'Producto no encontrado.');
  });

  void buscarOrdenPorId() => _ejecutar(() {
    final orden = service.buscarOrdenPorId(reader.leerTexto('id (orden): '));
    _mostrarResultado(orden, 'Orden no encontrada.');
  });

  void mostrarProveedores() =>
      _mostrarLista(service.mostrarProveedores(), 'No hay proveedores.');

  void mostrarProductos() =>
      _mostrarLista(service.mostrarProductos(), 'No hay productos.');

  void mostrarOrdenesPorEstado() =>
      _mostrarLista(service.mostrarOrdenesPorEstado(), 'No hay ordenes.');

  void procesarOrden() => _ejecutar(() {
    service.procesarOrden();
    print('[OK] Orden procesada.');
  });

  void mostrarOrdenesEntregadas() => _mostrarLista(
    service.mostrarOrdenesEntregadas(),
    'No hay ordenes entregadas.',
  );

  void mostrarOrdenesCanceladas() => _mostrarLista(
    service.mostrarOrdenesCanceladas(),
    'No hay ordenes canceladas.',
  );

  void mostrarOrdenesPorPrioridad() => _mostrarLista(
    service.mostrarOrdenesPorPrioridad(),
    'No hay ordenes pendientes.',
  );

  void eliminarProveedor() => _ejecutar(() {
    service.eliminarProveedor(reader.leerTexto('nit: '));
    print('[OK] Proveedor eliminado.');
  });

  void eliminarProducto() => _ejecutar(() {
    service.eliminarProducto(reader.leerTexto('id: '));
    print('[OK] Producto eliminado.');
  });

  void cancelarOrden() => _ejecutar(() {
    service.cancelarOrden(reader.leerTexto('id: '));
    print('[OK] Orden cancelada.');
  });

  void _ejecutar(void Function() accion) {
    try {
      accion();
    } on DomainException catch (error) {
      print('[Error] $error');
    }
  }

  void _mostrarResultado<T>(T? resultado, String mensajeVacio) {
    if (resultado == null) {
      print('[Info] $mensajeVacio');
    } else {
      print('[Encontrado] $resultado');
    }
  }

  void _mostrarLista<T>(List<T> lista, String mensajeVacio) {
    if (lista.isEmpty) {
      print('[Info] $mensajeVacio');
      return;
    }
    for (final elemento in lista) {
      print(' - $elemento');
    }
  }
}
