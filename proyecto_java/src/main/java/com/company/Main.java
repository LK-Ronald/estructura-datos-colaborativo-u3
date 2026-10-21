package com.company;

import com.company.cli.MenuCli;
import com.company.services.GestorSistemaService;

public class Main {
    public static void main(String[] args) {
        GestorSistemaService service = new GestorSistemaService();
        MenuCli menu = new MenuCli(service);
        menu.iniciar();
    }
}
