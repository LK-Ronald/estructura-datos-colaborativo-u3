import 'package:proyecto_dart/src/enums/estado.dart';
import 'package:proyecto_dart/src/exceptions/domain_exception.dart';
import 'package:proyecto_dart/src/services/gestor_sistema_service.dart';
import 'package:test/test.dart';

void main() {
  late GestorSistemaService service;

  setUp(() {
    service = GestorSistemaService()
      ..agregarProveedor('nit-1', 'Proveedor', 'Calle 1', 'correo@test.com')
      ..agregarProducto('p-1', 'Producto', 'Descripcion', 10)
      ..agregarProducto('p-2', 'Producto economico', 'Descripcion', 5);
  });

  test('ordena las ordenes pendientes por prioridad y luego por total', () {
    service.realizarOrden('BAJA', 'nit-1', ['p-1']);
    service.realizarOrden('ALTA', 'nit-1', ['p-1']);
    service.realizarOrden('ALTA', 'nit-1', ['p-2']);

    final ordenes = service.mostrarOrdenesPorPrioridad();

    expect(ordenes.map((orden) => orden.prioridad.name), [
      'alta',
      'alta',
      'baja',
    ]);
    expect(ordenes.first.total, 5);
    expect(ordenes[1].total, 10);
  });

  test('procesa la orden prioritaria hasta entregarla y la archiva', () {
    service.realizarOrden('BAJA', 'nit-1', ['p-1']);
    service.realizarOrden('ALTA', 'nit-1', ['p-2']);
    final prioritaria = service.mostrarOrdenesPorPrioridad().first;

    service.procesarOrden();
    expect(prioritaria.estado, Estado.enviado);
    service.procesarOrden();
    expect(prioritaria.estado, Estado.enCamino);
    service.procesarOrden();

    expect(prioritaria.estado, Estado.entregado);
    expect(service.mostrarOrdenesEntregadas(), [prioritaria]);
    expect(service.mostrarOrdenesPorPrioridad(), hasLength(1));
  });

  test('rechaza productos duplicados y referencias inexistentes', () {
    expect(
      () => service.agregarProducto('p-1', 'Otro', 'Descripcion', 2),
      throwsA(isA<ProductoDuplicatedException>()),
    );
    expect(
      () => service.realizarOrden('ALTA', 'nit-1', ['no-existe']),
      throwsA(isA<ProductoNotExistException>()),
    );
  });

  test('cancelar y borrar producto limpian ordenes relacionadas', () {
    service.realizarOrden('ALTA', 'nit-1', ['p-1']);
    service.realizarOrden('BAJA', 'nit-1', ['p-2']);
    final ordenes = service.mostrarOrdenesPorPrioridad();

    service.cancelarOrden(ordenes.first.id);
    expect(service.mostrarOrdenesCanceladas(), [ordenes.first]);

    service.eliminarProducto('p-1');
    expect(service.mostrarOrdenesCanceladas(), isEmpty);
    expect(service.mostrarOrdenesPorPrioridad(), hasLength(1));

    service.eliminarProducto('p-2');
    expect(service.mostrarOrdenesPorPrioridad(), isEmpty);
  });
}
