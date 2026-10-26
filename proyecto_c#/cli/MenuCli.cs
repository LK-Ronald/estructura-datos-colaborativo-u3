using ProyectoCsharp.Sercives;

namespace ProyectoCsharp.Cli
{
    public class MenuCli
    {
        private readonly ControladorAcciones _controlador;
        private readonly InputReader _reader = new();
        private bool _ejecutando = true;

        public MenuCli(GestorSistemaService service)
        {
            _controlador = new ControladorAcciones(service, _reader);
        }

        public void Iniciar()
        {
            try
            {
                while (_ejecutando)
                {
                    MostrarMenu();
                    EjecutarOpcion(_reader.LeerEntero("Seleccione una opción: "));
                    Console.WriteLine();
                }
            }
            catch (EndOfStreamException)
            {
                Console.WriteLine("\n[Info] Sesión finalizada.");
            }
        }

        private static void MostrarMenu()
        {
            Console.WriteLine("+----------------------------------------------+");
            Console.WriteLine("|           GESTOR DEL SISTEMA - CLI           |");
            Console.WriteLine("+----------------------------------------------+");
            Console.WriteLine("|  1. Agregar proveedor                        |");
            Console.WriteLine("|  2. Agregar producto                         |");
            Console.WriteLine("|  3. Realizar orden                           |");
            Console.WriteLine("|  4. Buscar proveedor por NIT                 |");
            Console.WriteLine("|  5. Buscar producto por ID                   |");
            Console.WriteLine("|  6. Buscar orden por ID                      |");
            Console.WriteLine("|  7. Mostrar proveedores                      |");
            Console.WriteLine("|  8. Mostrar productos                        |");
            Console.WriteLine("|  9. Mostrar órdenes por estado               |");
            Console.WriteLine("| 10. Procesar orden                           |");
            Console.WriteLine("| 11. Mostrar órdenes entregadas               |");
            Console.WriteLine("| 12. Mostrar órdenes canceladas               |");
            Console.WriteLine("| 13. Mostrar órdenes por prioridad            |");
            Console.WriteLine("| 14. Eliminar proveedor                       |");
            Console.WriteLine("| 15. Eliminar producto                        |");
            Console.WriteLine("| 16. Cancelar orden                           |");
            Console.WriteLine("|  0. Salir                                    |");
            Console.WriteLine("+----------------------------------------------+");
        }

        private void EjecutarOpcion(int opcion)
        {
            switch (opcion)
            {
                case 1:
                    _controlador.AgregarProveedor();
                    break;
                case 2:
                    _controlador.AgregarProducto();
                    break;
                case 3:
                    _controlador.RealizarOrden();
                    break;
                case 4:
                    _controlador.BuscarProveedorPorNit();
                    break;
                case 5:
                    _controlador.BuscarProductoPorId();
                    break;
                case 6:
                    _controlador.BuscarOrdenPorId();
                    break;
                case 7:
                    _controlador.MostrarProveedores();
                    break;
                case 8:
                    _controlador.MostrarProductos();
                    break;
                case 9:
                    _controlador.MostrarOrdenesPorEstado();
                    break;
                case 10:
                    _controlador.ProcesarOrden();
                    break;
                case 11:
                    _controlador.MostrarOrdenesEntregadas();
                    break;
                case 12:
                    _controlador.MostrarOrdenesCanceladas();
                    break;
                case 13:
                    _controlador.MostrarOrdenesPorPrioridad();
                    break;
                case 14:
                    _controlador.EliminarProveedor();
                    break;
                case 15:
                    _controlador.EliminarProducto();
                    break;
                case 16:
                    _controlador.CancelarOrden();
                    break;
                case 0:
                    Console.WriteLine("[Info] Saliendo del sistema...");
                    _ejecutando = false;
                    break;
                default:
                    Console.WriteLine("[!] Opción no válida.");
                    break;
            }
        }
    }
}
