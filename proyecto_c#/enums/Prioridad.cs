using System;

namespace ProyectoCsharp.Enums
{
    public enum Prioridad
    {
        Alta = 1,
        Media = 2,
        Baja = 3
    }

    public static class PrioridadExtensions
    {
        public static int GetPriority(this Prioridad p)
        {
            return (int)p;
        }

        public static Prioridad FromString(string value)
        {
            if (Enum.TryParse<Prioridad>(value, ignoreCase: true, out var resultado) &&
                Enum.IsDefined(typeof(Prioridad), resultado))
            {
                return resultado;
            }

            throw new Exception("La prioridad especificada no es válida.");
        }
    }
}