namespace ProyectoCsharp.Exceptions
{

    public class ProveedorDuplicatedException : DomainException
    {
        private const string Mensaje = "El proveedor con id {0} ya existe";

        private ProveedorDuplicatedException(string message)
            : base(message)
        {
        }

        public static ProveedorDuplicatedException ProveedorDuplicated(string id)
        {
            return new ProveedorDuplicatedException(string.Format(Mensaje, id));
        }
    }
}

