package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCliente extends JFrame {

    private JButton adicionarClienteButton;
    private JButton adicionarVeiculoButton;
    private JButton removerVeiculoButton; // Alterado para "Remover Veículo"

    public MenuCliente() {
        setTitle("Menu Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 linhas, 1 coluna, espaçamento de 10px

        // Botão Adicionar Cliente
        adicionarClienteButton = new JButton("Adicionar Cliente");
        panel.add(adicionarClienteButton);

        // Botão Adicionar Veículo
        adicionarVeiculoButton = new JButton("Adicionar Veículo");
        panel.add(adicionarVeiculoButton);

        // Botão Remover Veículo
        removerVeiculoButton = new JButton("Remover Veículo"); // Alterado para "Remover Veículo"
        panel.add(removerVeiculoButton);

        // Adiciona o painel ao frame
        add(panel);

        // Configura os listeners para os botões
        adicionarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Adicionar Cliente
                AdicionarCliente adicionarCliente = new AdicionarCliente();
                adicionarCliente.setVisible(true);
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

        removerVeiculoButton.addActionListener(new ActionListener() { // Alterado para "Remover Veículo"
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre a tela de Remover Veículo
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
