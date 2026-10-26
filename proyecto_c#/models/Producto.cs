using ProyectoCsharp.Exceptions;

namespace ProyectoCsharp.Entity
{
    public record Producto
    {

        public string Id { get; init; }
        public string Nombre { get; init; }
        public string Descripcion { get; init; }
        public double Precio { get; init; }

        public Producto(string id, string nombre, string descripcion, double precio)
        {
            ValidateNotEmpty(id, nameof(id));
            ValidateNotEmpty(nombre, nameof(nombre));
            ValidateNotEmpty(descripcion, nameof(descripcion));
            ValidatePrice(precio);

            Id = id;
            Nombre = nombre;
            Descripcion = descripcion;
            Precio = precio;
        }

        private static void ValidateNotEmpty(string value, string fieldName)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                CampoObligatorioException.CampoObligatorio(fieldName);
            }
        }

        private static void ValidatePrice(double value)
        {
            if (value <= 0)
            {
                PrecioInvalidoException.PrecioInvalido();
            }
        }
    }
}