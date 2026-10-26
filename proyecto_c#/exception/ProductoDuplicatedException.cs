namespace ProyectoCsharp.Exceptions
{

    public class ProductoDuplicatedException : DomainException
    {
        private const string Mensaje = "El producto con id {0} ya existe";

        private ProductoDuplicatedException(string message)
            : base(message)
        {
        }

        public static ProductoDuplicatedException ProductoDuplicated(string id)
        {
            return new ProductoDuplicatedException(string.Format(Mensaje, id));
        }
    }
}
