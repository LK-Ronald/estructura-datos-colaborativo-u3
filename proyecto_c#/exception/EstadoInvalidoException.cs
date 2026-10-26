namespace ProyectoCsharp.Exceptions
{
    public class EstadoInvalidoException : DomainException
    {
        private const string Mensaje = "La orden fue cancelada";
        private const string MensajeInvalido = "El esto es invalido";
        private const string MensajeEntregado = "El estado no puede ser cambiado";

        private EstadoInvalidoException(string message)
            : base(message)
        {
        }

        public static EstadoInvalidoException EstadoCancelado()
        {
            throw new EstadoInvalidoException(Mensaje);
        }

        public static EstadoInvalidoException EstadoInvalido()
        {
            throw new EstadoInvalidoException(MensajeInvalido);
        }

        public static EstadoInvalidoException EstadoEntregado()
        {
            throw new EstadoInvalidoException(MensajeEntregado);
        }
    }
}