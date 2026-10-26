namespace ProyectoCsharp.Exceptions
{
    public class CampoObligatorioException : DomainException
    {
        private const string Mensaje = "Este campo es obligatorio";
        private CampoObligatorioException(string mensaje)
        : base(mensaje)
        {
        }

        public static CampoObligatorioException CampoObligatorio(string campo)
        {
            return new CampoObligatorioException($"{Mensaje} {campo}");
        }
    }
}