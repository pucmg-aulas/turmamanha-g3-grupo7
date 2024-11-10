package view;

import controller.ClienteController;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ajuste para fechar apenas esta janela
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
                dispose(); // Fecha o MenuCliente após abrir AdicionarCliente
            }
        });

        adicionarVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarVeiculo adicionarVeiculo = new AdicionarVeiculo();
                adicionarVeiculo.setVisible(true);
                dispose(); // Fecha o MenuCliente após abrir AdicionarVeiculo
            }
        });

        removerVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoverVeiculo removerVeiculo = new RemoverVeiculo();
                removerVeiculo.setVisible(true);
                dispose(); // Fecha o MenuCliente após abrir RemoverVeiculo
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
