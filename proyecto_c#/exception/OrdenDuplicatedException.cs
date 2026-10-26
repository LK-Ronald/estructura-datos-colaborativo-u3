using System.Reflection.Metadata;

namespace ProyectoCsharp.Exceptions
{

    public class OrdenDuplicatedException : DomainException
    {
        private const string Mensaje = "La orden ya existe";

        private OrdenDuplicatedException(string message)
            : base(message)
        {
        }

        public static OrdenDuplicatedException OrdenDuplicate()
        {
            return new OrdenDuplicatedException(Mensaje);
        }
    }
}
