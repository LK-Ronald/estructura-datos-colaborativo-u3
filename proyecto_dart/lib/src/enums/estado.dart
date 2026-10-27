enum Estado {
  creado,
  enviado,
  enCamino,
  entregado,
  cancelado;

  @override
  String toString() => name.toUpperCase();
}
