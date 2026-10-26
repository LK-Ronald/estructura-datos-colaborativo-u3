namespace ProyectoCsharp.Exceptions
{
    public class OrdenNotExistException : DomainException
    {
        private const string DefaultMessage = "La orden con id {0}no existe.";

        private OrdenNotExistException(string message) : base(message)
        {
        }

        public static OrdenNotExistException OrdenNotExist(string id)
        {
            return new OrdenNotExistException(string.Format(DefaultMessage, id));
        }
    }
}