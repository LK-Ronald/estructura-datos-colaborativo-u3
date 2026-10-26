using System.Globalization;
using ProyectoCsharp.Enums;

namespace ProyectoCsharp.Cli
{
    public class InputReader
    {
        public string LeerTexto(string prompt)
        {
            while (true)
            {
                Console.Write(prompt);
                string? input = Console.ReadLine();
                if (input == null)
                {
                    throw new EndOfStreamException("La entrada ha finalizado.");
                }

                input = input.Trim();
                if (!string.IsNullOrWhiteSpace(input))
                {
                    return input;
                }

                Console.WriteLine("[!] El campo no puede estar vacío.");
            }
        }

        public double LeerDouble(string prompt)
        {
            while (true)
            {
                string input = LeerTexto(prompt);
                if (double.TryParse(input, NumberStyles.Float, CultureInfo.CurrentCulture, out double value) ||
                    double.TryParse(input, NumberStyles.Float, CultureInfo.InvariantCulture, out value))
                {
                    return value;
                }

                Console.WriteLine("[!] Ingrese un valor numérico válido.");
            }
        }

        public int LeerEntero(string prompt)
        {
            while (true)
            {
                string input = LeerTexto(prompt);
                if (int.TryParse(input, out int value))
                {
                    return value;
                }

                Console.WriteLine("[!] Ingrese una opción numérica válida.");
            }
        }

        public List<string> LeerLista(string prompt)
        {
            while (true)
            {
                string input = LeerTexto(prompt);
                List<string> items = input
                    .Split(',')
                    .Select(item => item.Trim())
                    .Where(item => !string.IsNullOrWhiteSpace(item))
                    .ToList();

                if (items.Count > 0)
                {
                    return items;
                }

                Console.WriteLine("[!] Debe ingresar al menos un identificador.");
            }
        }

        public string LeerPrioridad(string prompt)
        {
            while (true)
            {
                string input = LeerTexto(prompt);
                if (Enum.TryParse(input, ignoreCase: true, out Prioridad prioridad) &&
                    Enum.IsDefined(prioridad))
                {
                    return prioridad.ToString();
                }

                Console.WriteLine("[!] Ingrese una prioridad válida: ALTA, MEDIA o BAJA.");
            }
        }
    }
}
