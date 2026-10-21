package com.company.cli;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputReader {
    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String leerTexto(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Entrada finalizada.");
            }
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                return input;
            }
            System.out.println("[!] El campo no puede estar vacio.");
        }
    }

    public double leerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Entrada finalizada.");
            }
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese un valor numerico valido.");
            }
        }
    }

    public int leerEntero(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Entrada finalizada.");
            }
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] Ingrese una opcion numerica valida.");
            }
        }
    }

    public List<String> leerLista(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Entrada finalizada.");
            }
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) {
                return Arrays.stream(input.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .toList();
            }
            System.out.println("[!] Debe ingresar al menos un identificador.");
        }
    }
}
