namespace ProyectoCsharp.Exceptions
{

    public class PrecioInvalidoException : DomainException
    {
        private const string Mensaje = "El precio debe ser mayor a 0";

        private PrecioInvalidoException(string message)
                : base(message)
        {
        }

        public static PrecioInvalidoException PrecioInvalido()
        {
            throw new PrecioInvalidoException(Mensaje);
        }

    }
}
