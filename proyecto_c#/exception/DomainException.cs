namespace ProyectoCsharp.Exceptions
{
    public class DomainException : Exception
    {
        protected DomainException(string mensaje)
        : base(mensaje)
        {
        }

        protected DomainException(string mensaje, Exception innerException)
        : base(mensaje, innerException)
        {
        }
    }
}