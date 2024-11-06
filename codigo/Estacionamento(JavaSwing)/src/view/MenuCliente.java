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

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        adicionarClienteButton = new JButton("Adicionar Cliente");
        adicionarVeiculoButton = new JButton("Adicionar Veículo");
        removerVeiculoButton = new JButton("Remover Veículo");

        panel.add(adicionarClienteButton);
        panel.add(adicionarVeiculoButton);
        panel.add(removerVeiculoButton);

        add(panel);

        adicionarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarCliente adicionarClienteView = new AdicionarCliente();
                ClienteModel clienteModel = new ClienteModel("", "");
                ClienteController clienteController = new ClienteController(clienteModel, adicionarClienteView);
                adicionarClienteView.setVisible(true);
            }
        });

        adicionarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarVeiculo adicionarVeiculo = new AdicionarVeiculo();
                adicionarVeiculo.setVisible(true);
            }
        });

        removerVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoverVeiculo removerVeiculo = new RemoverVeiculo();
                removerVeiculo.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuCliente menuCliente = new MenuCliente();
            menuCliente.setVisible(true);
        });
    }
}
