package main;

import view.MenuCliente;

public class Main {
    public static void main(String[] args) {
        // Cria a visão da interface para o menu de cliente
        MenuCliente menuView = new MenuCliente();

        // Exibe a tela do menu do cliente
        menuView.setVisible(true);
    }
}
