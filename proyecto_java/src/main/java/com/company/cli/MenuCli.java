package com.company.cli;

import com.company.services.GestorSistemaService;

import java.util.Scanner;

public class MenuCli {
    private final ControladorAcciones controlador;
    private final InputReader reader;
    private boolean ejecutando;

    public MenuCli(GestorSistemaService service) {
        Scanner scanner = new Scanner(System.in);
        this.reader = new InputReader(scanner);
        this.controlador = new ControladorAcciones(service, reader);
        this.ejecutando = true;
    }

    public void iniciar() {
        try {
            while (ejecutando) {
                mostrarMenu();
                int opcion = reader.leerEntero("Seleccione una opcion: ");
                ejecutarOpcion(opcion);
                System.out.println();
            }
        } catch (IllegalStateException e) {
            System.out.println("\n[Info] Sesion finalizada.");
        }
    }

    private void mostrarMenu() {
        System.out.println("+----------------------------------------------+");
        System.out.println("|           GESTOR DEL SISTEMA - CLI           |");
        System.out.println("+----------------------------------------------+");
        System.out.println("|  1. agregar Proveedor                         |");
        System.out.println("|  2. agregar Producto                          |");
        System.out.println("|  3. realizar Orden                            |");
        System.out.println("|  4. buscar Proveedor Por Nit                  |");
        System.out.println("|  5. buscar Producto Por Id                    |");
        System.out.println("|  6. buscar Orden Por Id                       |");
        System.out.println("|  7. mostrar Proveedores                       |");
        System.out.println("|  8. mostrar Productos                         |");
        System.out.println("|  9. mostrar Ordenes Por Estado                |");
        System.out.println("| 10. procesar Orden                            |");
        System.out.println("| 11. mostrar Ordenes Entregadas                |");
        System.out.println("| 12. mostrar Ordenes Canceladas                |");
        System.out.println("| 13. mostrar Ordenes Por Prioridad             |");
        System.out.println("| 14. eliminar Proveedor                        |");
        System.out.println("| 15. eliminar Producto                         |");
        System.out.println("| 16. cancelar Orden                            |");
        System.out.println("|  0. Salir                                     |");
        System.out.println("+----------------------------------------------+");
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> controlador.agregarProveedor();
            case 2 -> controlador.agregarProducto();
            case 3 -> controlador.realizarOrden();
            case 4 -> controlador.buscarProveedorPorNit();
            case 5 -> controlador.buscarProductoPorId();
            case 6 -> controlador.buscarOrdenPorId();
            case 7 -> controlador.mostrarProveedores();
            case 8 -> controlador.mostrarProductos();
            case 9 -> controlador.mostrarOrdenesPorEstado();
            case 10 -> controlador.procesarOrden();
            case 11 -> controlador.mostrarOrdenesEntregadas();
            case 12 -> controlador.mostrarOrdenesCanceladas();
            case 13 -> controlador.mostrarOrdenesPorPrioridad();
            case 14 -> controlador.eliminarProveedor();
            case 15 -> controlador.eliminarProducto();
            case 16 -> controlador.cancelarOrden();
            case 0 -> {
                System.out.println("[Info] Saliendo del sistema...");
                ejecutando = false;
            }
            default -> System.out.println("[!] Opcion no valida.");
        }
    }
}
