import '../models/producto.dart';

class CalcularTotalService {
  double calcularTotal(Iterable<Producto>? productos) {
    if (productos == null) return 0;
    return productos.fold<double>(
      0,
      (total, producto) => total + producto.precio,
    );
  }
}
