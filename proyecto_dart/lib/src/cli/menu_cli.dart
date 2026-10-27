import 'controlador_acciones.dart';
import 'input_reader.dart';
import '../services/gestor_sistema_service.dart';

class MenuCli {
  MenuCli(GestorSistemaService service) : reader = InputReader() {
    controlador = ControladorAcciones(service, reader);
  }

  final InputReader reader;
  late final ControladorAcciones controlador;
  bool _ejecutando = true;

  void iniciar() {
    try {
      while (_ejecutando) {
        _mostrarMenu();
        final opcion = reader.leerEntero('Seleccione una opcion: ');
        _ejecutarOpcion(opcion);
        print('');
      }
    } on InputFinalizadaException {
      print('\n[Info] Sesion finalizada.');
    }
  }

  void _mostrarMenu() {
    print('+----------------------------------------------+');
    print('|           GESTOR DEL SISTEMA - CLI           |');
    print('+----------------------------------------------+');
    print('|  1. agregar Proveedor                         |');
    print('|  2. agregar Producto                          |');
    print('|  3. realizar Orden                            |');
    print('|  4. buscar Proveedor Por Nit                  |');
    print('|  5. buscar Producto Por Id                    |');
    print('|  6. buscar Orden Por Id                       |');
    print('|  7. mostrar Proveedores                       |');
    print('|  8. mostrar Productos                         |');
    print('|  9. mostrar Ordenes Por Estado                |');
    print('| 10. procesar Orden                            |');
    print('| 11. mostrar Ordenes Entregadas                |');
    print('| 12. mostrar Ordenes Canceladas                |');
    print('| 13. mostrar Ordenes Por Prioridad             |');
    print('| 14. eliminar Proveedor                        |');
    print('| 15. eliminar Producto                         |');
    print('| 16. cancelar Orden                            |');
    print('|  0. Salir                                     |');
    print('+----------------------------------------------+');
  }

  void _ejecutarOpcion(int opcion) {
    switch (opcion) {
      case 1:
        controlador.agregarProveedor();
      case 2:
        controlador.agregarProducto();
      case 3:
        controlador.realizarOrden();
      case 4:
        controlador.buscarProveedorPorNit();
      case 5:
        controlador.buscarProductoPorId();
      case 6:
        controlador.buscarOrdenPorId();
      case 7:
        controlador.mostrarProveedores();
      case 8:
        controlador.mostrarProductos();
      case 9:
        controlador.mostrarOrdenesPorEstado();
      case 10:
        controlador.procesarOrden();
      case 11:
        controlador.mostrarOrdenesEntregadas();
      case 12:
        controlador.mostrarOrdenesCanceladas();
      case 13:
        controlador.mostrarOrdenesPorPrioridad();
      case 14:
        controlador.eliminarProveedor();
      case 15:
        controlador.eliminarProducto();
      case 16:
        controlador.cancelarOrden();
      case 0:
        print('[Info] Saliendo del sistema...');
        _ejecutando = false;
      default:
        print('[!] Opcion no valida.');
    }
  }
}
