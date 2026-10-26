namespace ProyectoCsharp.Exceptions
{

    public class ProveedorNotExistException : DomainException
    {
        private const string Mensaje = "El proveedor con id {0} no existe";

        private ProveedorNotExistException(string message)
                : base(message)
        {
        }

        public static ProveedorNotExistException ProveedorNotExist(string id)
        {
            return new ProveedorNotExistException(string.Format(Mensaje, id));
        }
    }
}

