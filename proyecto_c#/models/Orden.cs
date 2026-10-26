using ProyectoCsharp.Enums;
using ProyectoCsharp.Exceptions;

namespace ProyectoCsharp.Entity
{
    public class Orden
    {
        private readonly string id;
        private Estado estado;
        private Prioridad prioridad;
        private readonly Proveedor proveedor;
        private readonly List<Producto> productos;
        private readonly double total;
        private readonly DateTime fecha;

        public string Id { get => id; }
        public Estado Estado { get => estado; }
        public Prioridad Prioridad { get => prioridad; }
        public Proveedor Proveedor { get => proveedor; }
        public List<Producto> Productos { get => productos; }
        public double Total { get => total; }
        public DateTime Fecha { get => fecha; }

        public Orden(Prioridad prioridad, Proveedor proveedor, List<Producto> productos, double total)
        {
            this.id = Guid.NewGuid().ToString();
            this.estado = Estado.Creado;
            this.prioridad = prioridad;
            this.proveedor = proveedor;
            this.productos = productos;
            this.total = total;
            this.fecha = DateTime.Now;
        }

        public void CambiarEstado(Estado estado)
        {
            if (this.estado.Equals(Estado.Cancelado))
            {
                throw EstadoInvalidoException.EstadoCancelado();
            }
            if (this.estado.Equals(Estado.Entregado))
            {
                throw EstadoInvalidoException.EstadoEntregado();
            }
            this.estado = estado;
        }

        public void CambiarPrioridad(Prioridad prioridad)
        {
            this.prioridad = prioridad;
        }

        public override string ToString()
        {
            return "Orden{" +
                    "id='" + id + '\'' +
                    ", estado=" + estado +
                    ", prioridad=" + prioridad +
                    ", proveedor=" + proveedor +
                    ", productos=[" + string.Join(", ", productos) + "]" +
                    ", total=" + total +
                    ", fecha=" + fecha +
                    '}';
        }

        public override bool Equals(object? obj)
        {
            return base.Equals(obj);
        }

        public override int GetHashCode()
        {
            return base.GetHashCode();
        }

    }
}