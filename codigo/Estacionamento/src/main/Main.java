package main;

import controller.ClienteController;
import view.MenuCliente;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ClienteController controller = new ClienteController();
        MenuCliente menu = new MenuCliente(controller);

        // Cria o JFrame principal
        JFrame frame = new JFrame("MenuCliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setContentPane(menu.getContentPane()); // Adiciona o painel ao JFrame
        frame.setVisible(true);
    }
}
