using ProyectoCsharp.Entity;
using ProyectoCsharp.Comparatores;
using ProyectoCsharp.Exceptions;
using ProyectoCsharp.Enums;

namespace ProyectoCsharp.Repository
{
    public class SistemaRepostitory
    {
        private readonly OrderedDictionary<string, Proveedor> _proveedores = new();
        private readonly OrderedDictionary<string, Producto> _productos = new();
        private readonly static OrdenComparator _ordenComparator = new OrdenComparator();
        private readonly static OrdenEstadoComparator _estadoComparator = new OrdenEstadoComparator();
        private readonly PriorityQueue<Orden, Orden> _ordenes = new PriorityQueue<Orden, Orden>(_ordenComparator);
        private readonly HashSet<Orden> _ordenesSet = new();
        private readonly OrderedDictionary<string, Orden> _ordenesEntregadas = new();
        private readonly OrderedDictionary<string, Orden> _ordenesCanceladas = new();

        public void AgregarProveedor(Proveedor proveedor)
        {
            if (proveedor == null)
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(proveedor));
            }
            if (_proveedores.ContainsKey(proveedor.Nit))
            {
                throw ProveedorDuplicatedException.ProveedorDuplicated(proveedor.Nit);
            }
            _proveedores.Add(proveedor.Nit, proveedor);
        }

        public void AgregarProducto(Producto producto)
        {
            if (producto == null)
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(producto));
            }
            if (_productos.ContainsKey(producto.Id))
            {
                throw ProductoDuplicatedException.ProductoDuplicated(producto.Id);
            }
            _productos.Add(producto.Id, producto);
        }

        public void RealizarOrden(Orden orden)
        {
            if (orden == null)
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(orden));
            }
            if (_ordenesSet.Contains(orden))
            {
                throw OrdenDuplicatedException.OrdenDuplicate();
            }
            string nitProveedor = orden.Proveedor.Nit;
            if (orden.Proveedor == null || !_proveedores.ContainsKey(nitProveedor))
            {
                throw ProveedorNotExistException.ProveedorNotExist(nitProveedor);
            }
            if (orden.Productos == null)
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(orden.Productos));
            }

            foreach (var producto in orden.Productos)
            {
                if (!_productos.ContainsKey(producto.Id))
                {
                    throw ProductoNotExistException.ProductoNoExistente(producto.Id);
                }
            }

            _ordenes.Enqueue(orden, orden);
            _ordenesSet.Add(orden);
        }

        public Proveedor? BuscarProveedorPorNit(string nit)
        {
            if (string.IsNullOrWhiteSpace(nit))
            {
                return null;
            }

            return _proveedores.TryGetValue(nit, out var proveedor) ? proveedor : null;
        }

        public Producto? BuscarProductoPorId(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                return null;
            }

            return _productos.TryGetValue(id, out var producto) ? producto : null;
        }

        public Orden? BuscarOrdenPorId(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                return null;
            }

            return _ordenes.UnorderedItems
                .Select(item => item.Element)
                .FirstOrDefault(o => o.Id == id);
        }

        public List<Proveedor> MostrarProveedores()
        {
            return _proveedores.Values.ToList();
        }

        public List<Producto> MostrarProductos()
        {
            return _productos.Values.ToList();
        }

        public List<Orden> MostrarOrdenesPorEstado()
        {
            if (_ordenes.Count == 0)
            {
                return new List<Orden>();
            }
            List<Orden> ordens = _ordenes.UnorderedItems.Select(item => item.Element).ToList();
            ordens.Sort(_estadoComparator);
            return ordens;
        }

        public void ProcesarOrden()
        {
            if (_ordenes.Count < 0)
            {
                return;
            }
            Orden orden = _ordenes.Peek();
            switch (orden.Estado)
            {
                case Estado.Creado:
                    orden.CambiarEstado(Estado.Enviado);
                    break;
                case Estado.Enviado:
                    orden.CambiarEstado(Estado.EnCamino);
                    break;
                case Estado.EnCamino:
                    orden.CambiarEstado(Estado.Entregado);
                    break;
                default:
                    { }
                    break;
            }
            if (orden.Estado == Estado.Entregado)
            {
                _ordenes.Dequeue();
                _ordenesEntregadas.Add(orden.Id, orden);
                _ordenesSet.Remove(orden);
            }
        }

        public List<Orden> MostrarOrdenesEntregadas()
        {
            return _ordenesEntregadas.Values.ToList();
        }

        public List<Orden> MostrarOrdenesCanceladas()
        {
            return _ordenesCanceladas.Values.ToList();
        }

        public List<Orden> MostrarOrdenesPorPrioridad()
        {
            if (_ordenes.Count == 0)
            {
                return new List<Orden>();
            }
            List<Orden> ordens = _ordenes.UnorderedItems.Select(item => item.Element).ToList();
            ordens.Sort(_ordenComparator);
            return ordens;
        }

        public void EliminarProveedor(string nit)
        {
            if (string.IsNullOrWhiteSpace(nit))
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(nit));
            }
            if (!_proveedores.ContainsKey(nit))
            {
                throw ProveedorNotExistException.ProveedorNotExist(nit);
            }
            _proveedores.Remove(nit);
            EliminarProveedorEnOrdenes(nit);
            EliminarProveedorEnOrdenesEntregadas(nit);
            EliminarProveedorEnOrdenesCanceladas(nit);
        }

        public void EliminarProducto(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(id));
            }
            if (!_productos.ContainsKey(id))
            {
                throw ProductoNotExistException.ProductoNoExistente(id);
            }
            _productos.Remove(id);
            EliminarProductoEnOrdenes(id);
            EliminarProductoEnOrdenesEntregadas(id);
            EliminarProductoEnOrdenesCanceladas(id);
        }

        public void CancelarOrden(string id)
        {
            if (string.IsNullOrWhiteSpace(id))
            {
                throw CampoObligatorioException.CampoObligatorio(nameof(id));
            }
            Orden orden = BuscarOrdenPorId(id) ?? throw OrdenNotExistException.OrdenNotExist(id);
            if (orden.Estado == Estado.Entregado)
            {
                throw EstadoInvalidoException.EstadoEntregado();
            }

            var ordenesRestantes = _ordenes.UnorderedItems
            .Select(item => item.Element)
            .Where(o => o.Id != id)
            .ToList();

            _ordenes.Clear();
            _ordenesSet.Remove(orden);
            foreach (var o in ordenesRestantes)
            {
                _ordenes.Enqueue(o, o);
            }

            orden.CambiarEstado(Estado.Cancelado);
            _ordenesCanceladas.Add(orden.Id, orden);
        }

        private void EliminarProductoEnOrdenes(string id)
        {
            if (string.IsNullOrWhiteSpace(id)) return;

            var ordenesAEliminar = _ordenes.UnorderedItems
                .Select(item => item.Element)
                .Where(orden => orden.Productos != null &&
                                orden.Productos.Any(p => p.Id == id))
                .ToList();

            foreach (var orden in ordenesAEliminar)
            {
                if (_ordenes.Remove(orden, out _, out _))
                {
                    _ordenesSet.Remove(orden);
                }
            }
        }

        private void EliminarProductoEnOrdenesEntregadas(string id)
        {
            EliminarOrdenesDeDiccionario(_ordenesEntregadas, orden =>
                orden.Productos != null && orden.Productos.Any(p => p.Id == id));
        }

        private void EliminarProductoEnOrdenesCanceladas(string id)
        {
            EliminarOrdenesDeDiccionario(_ordenesCanceladas, orden =>
                orden.Productos != null && orden.Productos.Any(p => p.Id == id));
        }

        private void EliminarProveedorEnOrdenes(string nit)
        {
            if (string.IsNullOrWhiteSpace(nit)) return;

            var ordenesValidas = _ordenes.UnorderedItems
                .Select(item => item.Element)
                .Where(orden => orden.Proveedor == null || orden.Proveedor.Nit != nit)
                .ToList();

            _ordenes.Clear();
            _ordenesSet.Clear();

            foreach (var orden in ordenesValidas)
            {
                _ordenes.Enqueue(orden, orden);
                _ordenesSet.Add(orden);
            }
        }

        private void EliminarProveedorEnOrdenesEntregadas(string nit)
        {
            EliminarOrdenesDeDiccionario(_ordenesEntregadas, orden =>
                orden.Proveedor != null && orden.Proveedor.Nit == nit);
        }

        private void EliminarProveedorEnOrdenesCanceladas(string nit)
        {
            EliminarOrdenesDeDiccionario(_ordenesCanceladas, orden =>
                orden.Proveedor != null && orden.Proveedor.Nit == nit);
        }

        private static void EliminarOrdenesDeDiccionario(
            IDictionary<string, Orden> diccionario,
            Func<Orden, bool> condicion)
        {
            var llavesAEliminar = diccionario
                .Where(kvp => condicion(kvp.Value))
                .Select(kvp => kvp.Key)
                .ToList();

            foreach (var key in llavesAEliminar)
            {
                diccionario.Remove(key);
            }
        }
    }
}