using ProyectoCsharp.Entity;
using ProyectoCsharp.Enums;

namespace ProyectoCsharp.Comparatores
{
    class OrdenComparator : IComparer<Orden>
    {
        public int Compare(Orden? x, Orden? y)
        {
            int priority = ((int)x.Prioridad).CompareTo((int)y.Prioridad);

            if (priority != 0) return priority;

            return x.Total.CompareTo(y.Total);
        }
    }
}