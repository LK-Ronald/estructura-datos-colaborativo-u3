using ProyectoCsharp.Entity;
using ProyectoCsharp.Exceptions;
using ProyectoCsharp.Sercives;

namespace ProyectoCsharp.Cli
{
    public class ControladorAcciones
    {
        private readonly GestorSistemaService _service;
        private readonly InputReader _reader;

        public ControladorAcciones(GestorSistemaService service, InputReader reader)
        {
            _service = service;
            _reader = reader;
        }

        public void AgregarProveedor()
        {
            EjecutarConManejoDeDominio(() =>
            {
                _service.AgregarProveedor(
                    _reader.LeerTexto("nit: "),
                    _reader.LeerTexto("nombre: "),
                    _reader.LeerTexto("dirección: "),
                    _reader.LeerTexto("email: "));
                Console.WriteLine("[OK] Proveedor agregado.");
            });
        }

        public void AgregarProducto()
        {
            EjecutarConManejoDeDominio(() =>
            {
                _service.AgregarProducto(
                    _reader.LeerTexto("id: "),
                    _reader.LeerTexto("nombre: "),
                    _reader.LeerTexto("descripción: "),
                    _reader.LeerDouble("precio: "));
                Console.WriteLine("[OK] Producto agregado.");
            });
        }

        public void RealizarOrden()
        {
            EjecutarConManejoDeDominio(() =>
            {
                string prioridad = _reader.LeerPrioridad("prioridad (ALTA/MEDIA/BAJA): ");
                string nit = _reader.LeerTexto("nit (proveedor): ");
                List<string> idsProductos = _reader.LeerLista("idsProductos (separados por coma): ");
                _service.RealizarOrden(prioridad, nit, idsProductos);
                Console.WriteLine("[OK] Orden realizada.");
            });
        }

        public void BuscarProveedorPorNit()
        {
            EjecutarConManejoDeDominio(() =>
            {
                Proveedor proveedor = _service.BuscarProveedorPorNit(_reader.LeerTexto("nit: "));
                Console.WriteLine("[Encontrado] " + proveedor);
            });
        }

        public void BuscarProductoPorId()
        {
            EjecutarConManejoDeDominio(() =>
            {
                Producto producto = _service.BuscarProductoPorId(_reader.LeerTexto("id: "));
                Console.WriteLine("[Encontrado] " + producto);
            });
        }

        public void BuscarOrdenPorId()
        {
            EjecutarConManejoDeDominio(() =>
            {
                Orden orden = _service.BuscarOrdenPorId(_reader.LeerTexto("id (orden): "));
                Console.WriteLine("[Encontrado] " + orden);
            });
        }

        public void MostrarProveedores()
        {
            MostrarLista(_service.MostrarProveedores(), "[Info] No hay proveedores.");
        }

        public void MostrarProductos()
        {
            MostrarLista(_service.MostrarProductos(), "[Info] No hay productos.");
        }

        public void MostrarOrdenesPorEstado()
        {
            MostrarLista(_service.MostrarOrdenesPorEstado(), "[Info] No hay órdenes.");
        }

        public void ProcesarOrden()
        {
            EjecutarConManejoDeDominio(() =>
            {
                if (_service.MostrarOrdenesPorPrioridad().Count == 0)
                {
                    Console.WriteLine("[Info] No hay órdenes pendientes para procesar.");
                    return;
                }

                _service.ProcesarOrden();
                Console.WriteLine("[OK] Orden procesada.");
            });
        }

        public void MostrarOrdenesEntregadas()
        {
            MostrarLista(_service.MostrarOrdenesEntregadas(), "[Info] No hay órdenes entregadas.");
        }

        public void MostrarOrdenesCanceladas()
        {
            MostrarLista(_service.MostrarOrdenesCanceladas(), "[Info] No hay órdenes canceladas.");
        }

        public void MostrarOrdenesPorPrioridad()
        {
            MostrarLista(_service.MostrarOrdenesPorPrioridad(), "[Info] No hay órdenes pendientes.");
        }

        public void EliminarProveedor()
        {
            EjecutarConManejoDeDominio(() =>
            {
                _service.EliminarProveedor(_reader.LeerTexto("nit: "));
                Console.WriteLine("[OK] Proveedor eliminado.");
            });
        }

        public void EliminarProducto()
        {
            EjecutarConManejoDeDominio(() =>
            {
                _service.EliminarProducto(_reader.LeerTexto("id: "));
                Console.WriteLine("[OK] Producto eliminado.");
            });
        }

        public void CancelarOrden()
        {
            EjecutarConManejoDeDominio(() =>
            {
                _service.CancelarOrden(_reader.LeerTexto("id: "));
                Console.WriteLine("[OK] Orden cancelada.");
            });
        }

        private static void MostrarLista<T>(IEnumerable<T> elementos, string mensajeVacio)
        {
            List<T> lista = elementos.ToList();
            if (lista.Count == 0)
            {
                Console.WriteLine(mensajeVacio);
                return;
            }

            foreach (T elemento in lista)
            {
                Console.WriteLine(" - " + elemento);
            }
        }

        private static void EjecutarConManejoDeDominio(Action accion)
        {
            try
            {
                accion();
            }
            catch (DomainException exception)
            {
                Console.WriteLine("[Error] " + exception.Message);
            }
        }
    }
}
