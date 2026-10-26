using ProyectoCsharp.Entity;

namespace ProyectoCsharp.Comparatores
{

    class OrdenEstadoComparator : IComparer<Orden>
    {
        public int Compare(Orden? x, Orden? y)
        {
            return x.Estado.CompareTo(y.Estado);
        }
    }
}