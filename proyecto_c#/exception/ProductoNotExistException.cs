namespace ProyectoCsharp.Exceptions
{

    public class ProductoNotExistException : DomainException
    {
        private const string MENSAJE = "El producto con id {0} no existe";

        private ProductoNotExistException(string message)
                : base(message)
        {
        }

        public static ProductoNotExistException ProductoNoExistente(string id)
        {
            return new ProductoNotExistException(string.Format(MENSAJE, id));
        }
    }
}
