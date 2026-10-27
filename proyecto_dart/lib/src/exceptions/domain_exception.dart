class DomainException implements Exception {
  const DomainException(this.message);

  final String message;

  @override
  String toString() => message;
}

class CampoObligatorioException extends DomainException {
  CampoObligatorioException(String campo)
    : super('Este campo es obligatorio: $campo');
}

class PrecioInvalidoException extends DomainException {
  PrecioInvalidoException() : super('El precio debe ser mayor a 0');
}

class PrioridadInvalidaException extends DomainException {
  PrioridadInvalidaException() : super('La prioridad es invalida');
}

class EstadoInvalidoException extends DomainException {
  EstadoInvalidoException.cancelado() : super('La orden fue cancelada');

  EstadoInvalidoException.invalido() : super('El estado es invalido');

  EstadoInvalidoException.entregado()
    : super('El estado no puede ser cambiado');
}

class OrdenDuplicatedException extends DomainException {
  OrdenDuplicatedException() : super('La orden ya existe');
}

class ProductoDuplicatedException extends DomainException {
  ProductoDuplicatedException(String id)
    : super('El producto con id $id ya existe');
}

class ProveedorDuplicatedException extends DomainException {
  ProveedorDuplicatedException(String id)
    : super('El proveedor con id $id ya existe');
}

class ProductoNotExistException extends DomainException {
  ProductoNotExistException(String id)
    : super('El producto con id $id no existe');
}

class ProveedorNotExistException extends DomainException {
  ProveedorNotExistException(String id)
    : super('El proveedor con id $id no existe');
}
