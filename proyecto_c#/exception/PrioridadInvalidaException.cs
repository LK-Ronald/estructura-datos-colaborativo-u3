namespace ProyectoCsharp.Exceptions
{
    public class PrioridadInvalidaException : DomainException
    {
        private const string MENSAJE = "La prioridad es invalida";

        private PrioridadInvalidaException(string mensaje)
            : base(mensaje)
        {
        }

        public static PrioridadInvalidaException PrioridadInvalida()
        {
            throw new PrioridadInvalidaException(MENSAJE);
        }
    }
}
