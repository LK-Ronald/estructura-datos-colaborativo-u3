# Gestor de pedidos en Dart

Aplicacion de consola migrada desde `proyecto_java` para administrar proveedores,
productos y ordenes.

## Ejecutar

Desde esta carpeta:

```shell
dart pub get
dart run
```

La aplicacion muestra un menu con opciones para registrar, buscar, listar,
procesar, cancelar y eliminar entidades.

## Colecciones

- Los proveedores y productos se guardan en `LinkedHashMap`, que conserva el
  orden de insercion mostrado por la interfaz.
- Las ordenes pendientes usan `HeapPriorityQueue` del paquete `collection`,
  equivalente adecuado a la cola de prioridad de Java. Se ordenan por prioridad
  y, en caso de empate, por total.
- Las ordenes entregadas y canceladas usan mapas ordenados por insercion para
  producir listados estables y predecibles.

## Pruebas

```shell
dart test
```
