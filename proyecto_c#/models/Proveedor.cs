using ProyectoCsharp.Exceptions;

namespace ProyectoCsharp.Entity
{
    public record Proveedor
    {
        public string Nit { get; init; }
        public string Nombre { get; init; }
        public string Direccion { get; init; }
        public string Email { get; init; }

        public Proveedor(string nit, string nombre, string direccion, string email)
        {
            ValidateNotEmpty(nit, nameof(nit));
            ValidateNotEmpty(nombre, nameof(nombre));
            ValidateNotEmpty(direccion, nameof(direccion));
            ValidateNotEmpty(email, nameof(email));

            Nit = nit;
            Nombre = nombre;
            Direccion = direccion;
            Email = email;
        }

        private static void ValidateNotEmpty(string value, string fieldName)
        {
            if (string.IsNullOrWhiteSpace(value))
            {
                CampoObligatorioException.CampoObligatorio(fieldName);
            }
        }
    }
}