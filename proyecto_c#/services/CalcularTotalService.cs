using ProyectoCsharp.Entity;

namespace ProyectoCsharp.Sercives
{
    public class CalcularTotalService
    {
        public CalcularTotalService() { }

        public double CalcularTotal(List<Producto> productos)
        {
            if (productos == null)
            {
                return 0;
            }
            double total = 0;
            foreach (var producto in productos)
            {
                total += producto.Precio;
            }
            return total;
        }

    }
}