import '../exceptions/domain_exception.dart';

enum Prioridad {
  alta(1),
  media(2),
  baja(3);

  const Prioridad(this.valor);

  final int valor;

  static Prioridad fromString(String value) {
    for (final prioridad in values) {
      if (value.toUpperCase() == prioridad.name.toUpperCase()) {
        return prioridad;
      }
    }
    throw PrioridadInvalidaException();
  }
}
