<<<<<<< HEAD
package view;

import controller.ClienteController;
import dao.ClienteDAO;
import model.ClienteModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCliente extends JFrame {

    private JButton adicionarClienteButton;
    private JButton adicionarVeiculoButton;
    private JButton removerVeiculoButton;

    public MenuCliente() {
        setTitle("Menu Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Inicialização dos botões
        adicionarClienteButton = new JButton("Adicionar Cliente");
        adicionarVeiculoButton = new JButton("Adicionar Veículo");
        removerVeiculoButton = new JButton("Remover Veículo");

        // Adiciona os botões ao painel
        panel.add(adicionarClienteButton);
        panel.add(adicionarVeiculoButton);
        panel.add(removerVeiculoButton);

        // Adiciona o painel ao frame
        add(panel);

        // Configura o listener do botão "Adicionar Cliente" para abrir a tela com o controlador
        adicionarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria a visão e o modelo do cliente
                AdicionarCliente adicionarClienteView = new AdicionarCliente();
                ClienteModel clienteModel = new ClienteModel("", "");

                // Cria o controlador e associa à visão e ao modelo
                ClienteController clienteController = new ClienteController(clienteModel, adicionarClienteView);

                // Exibe a tela de adicionar cliente
                adicionarClienteView.setVisible(true);
            }
        });

        adicionarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Adicionar Veículo
                AdicionarVeiculo adicionarVeiculo = new AdicionarVeiculo();
                adicionarVeiculo.setVisible(true);
            }
        });

        removerVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Remover Veículo
                RemoverVeiculo removerVeiculo = new RemoverVeiculo();
                removerVeiculo.setVisible(true);
            }
        });
    }

    // Método main para executar o MenuCliente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuCliente menuCliente = new MenuCliente();
            menuCliente.setVisible(true);
        });
    }
}
=======
package view;

import controller.ClienteController;
import dao.ClienteDAO;
import model.ClienteModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCliente extends JFrame {

    private JButton adicionarClienteButton;
    private JButton adicionarVeiculoButton;
    private JButton removerVeiculoButton;

    public MenuCliente() {
        setTitle("Menu Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Inicialização dos botões
        adicionarClienteButton = new JButton("Adicionar Cliente");
        adicionarVeiculoButton = new JButton("Adicionar Veículo");
        removerVeiculoButton = new JButton("Remover Veículo");

        // Adiciona os botões ao painel
        panel.add(adicionarClienteButton);
        panel.add(adicionarVeiculoButton);
        panel.add(removerVeiculoButton);

        // Adiciona o painel ao frame
        add(panel);

        // Configura o listener do botão "Adicionar Cliente" para abrir a tela com o controlador
        adicionarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cria a visão e o modelo do cliente
                AdicionarCliente adicionarClienteView = new AdicionarCliente();
                ClienteModel clienteModel = new ClienteModel("", "");

                // Cria o controlador e associa à visão e ao modelo
                ClienteController clienteController = new ClienteController(clienteModel, adicionarClienteView);

                // Exibe a tela de adicionar cliente
                adicionarClienteView.setVisible(true);
            }
        });

        adicionarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Adicionar Veículo
                AdicionarVeiculo adicionarVeiculo = new AdicionarVeiculo();
                adicionarVeiculo.setVisible(true);
            }
        });

        removerVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Remover Veículo
                RemoverVeiculo removerVeiculo = new RemoverVeiculo();
                removerVeiculo.setVisible(true);
            }
        });
    }

    // Método main para executar o MenuCliente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuCliente menuCliente = new MenuCliente();
            menuCliente.setVisible(true);
        });
    }
}
>>>>>>> d9070482504422a0b550f2cc893f54b1a437c91e
