using ProyectoCsharp.Cli;
using ProyectoCsharp.Sercives;

namespace ProyectoCsharp
{
    public class Program
    {
        public static void Main()
        {
            new MenuCli(new GestorSistemaService()).Iniciar();
        }
    }
}
