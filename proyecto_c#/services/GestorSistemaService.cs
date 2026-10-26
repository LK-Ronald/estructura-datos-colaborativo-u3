using ProyectoCsharp.Repository;
using ProyectoCsharp.Exceptions;
using ProyectoCsharp.Entity;
using ProyectoCsharp.Enums;

namespace ProyectoCsharp.Sercives
{
    public class GestorSistemaService
    {
        private readonly SistemaRepostitory _repository = new();
        private readonly CalcularTotalService _calcularService = new();

        public void AgregarProveedor(string nit, string nombre, string direccion, string email)
        {
            Proveedor proveedor = new(nit, nombre, direccion, email);
            _repository.AgregarProveedor(proveedor);
        }

        public void AgregarProducto(string id, string nombre, string descripcion, double precio)
        {
            Producto producto = new(id, nombre, descripcion, precio);
            _repository.AgregarProducto(producto);
        }

        public void RealizarOrden(string prioridad, string nit, List<string> idsProductos)
        {
            Proveedor proveedor = _repository.BuscarProveedorPorNit(nit) ?? throw ProveedorNotExistException.ProveedorNotExist(nit);
            Prioridad prioridadOrden = PrioridadExtensions.FromString(prioridad);
            List<Producto> productos = new();
            foreach (string idProducto in idsProductos)
            {
                Producto producto = _repository.BuscarProductoPorId(idProducto) ?? throw ProductoNotExistException.ProductoNoExistente(idProducto);
                productos.Add(producto);
            }
            var orden = new Orden(prioridadOrden, proveedor, productos, _calcularService.CalcularTotal(productos));
            _repository.RealizarOrden(orden);
        }

        public Proveedor BuscarProveedorPorNit(string nit)
        {
            if (nit == null)
            {
                throw CampoObligatorioException.CampoObligatorio("nit");
            }
            Proveedor proveedor = _repository.BuscarProveedorPorNit(nit) ?? throw ProveedorNotExistException.ProveedorNotExist(nit);
            return proveedor;
        }

        public Producto BuscarProductoPorId(string id)
        {
            if (id == null)
            {
                throw CampoObligatorioException.CampoObligatorio("id");
            }
            Producto producto = _repository.BuscarProductoPorId(id) ?? throw ProductoNotExistException.ProductoNoExistente(id);
            return producto;
        }

        public Orden BuscarOrdenPorId(string nit)
        {
            if (nit == null)
            {
                throw CampoObligatorioException.CampoObligatorio("nit");
            }
            Orden orden = _repository.BuscarOrdenPorId(nit) ?? throw OrdenNotExistException.OrdenNotExist(nit);
            return orden;
        }

        public List<Proveedor> MostrarProveedores()
        {
            return _repository.MostrarProveedores();
        }

        public List<Producto> MostrarProductos()
        {
            return _repository.MostrarProductos();
        }

        public List<Orden> MostrarOrdenesPorEstado()
        {
            return _repository.MostrarOrdenesPorEstado();
        }

        public void ProcesarOrden()
        {
            _repository.ProcesarOrden();
        }

        public List<Orden> MostrarOrdenesEntregadas()
        {
            return _repository.MostrarOrdenesEntregadas();
        }

        public List<Orden> MostrarOrdenesCanceladas()
        {
            return _repository.MostrarOrdenesCanceladas();
        }

        public List<Orden> MostrarOrdenesPorPrioridad()
        {
            return _repository.MostrarOrdenesPorPrioridad();
        }

        public void EliminarProveedor(string nit)
        {
            _repository.EliminarProveedor(nit);
        }

        public void EliminarProducto(string id)
        {
            _repository.EliminarProducto(id);
        }

        public void CancelarOrden(string id)
        {
            _repository.CancelarOrden(id);
        }

    }
}