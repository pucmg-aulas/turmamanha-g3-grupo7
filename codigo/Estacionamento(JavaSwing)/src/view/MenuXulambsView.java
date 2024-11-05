package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuXulambsView extends JFrame {

    private JButton adicionarEstacionamentoButton;
    private JButton removerEstacionamentoButton;
    private JButton gerenciarButton;
    private JButton menuDeClienteButton;

    public MenuXulambsView() {
        setTitle("Menu Xulambs");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal com layout de grade
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        // Botão "Adicionar Estacionamento"
        adicionarEstacionamentoButton = new JButton("Adicionar Estacionamento");
        adicionarEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir a tela de Adicionar Estacionamento
                new AdicionarEstacionamentoView().setVisible(true);
            }
        });

        // Botão "Remover Estacionamento"
        removerEstacionamentoButton = new JButton("Remover Estacionamento");
        removerEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir a tela de Remover Estacionamento
                new RemoverEstacionamentoView().setVisible(true);
            }
        });

        // Botão "Gerenciar" (Esse botão não foi especificado para abrir uma tela específica)
        gerenciarButton = new JButton("Gerenciar");
        gerenciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir a tela de Gerenciar (placeholder)
                JOptionPane.showMessageDialog(null, "Abrir tela de Gerenciar");
            }
        });

        // Botão "Menu de Cliente"
        menuDeClienteButton = new JButton("Menu de Cliente");
        menuDeClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação para abrir a tela de Menu de Cliente
                new MenuCliente().setVisible(true);
            }
        });

        // Adiciona os botões ao painel
        panel.add(adicionarEstacionamentoButton);
        panel.add(removerEstacionamentoButton);
        panel.add(gerenciarButton);
        panel.add(menuDeClienteButton);

        // Adiciona o painel ao frame
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuXulambsView tela = new MenuXulambsView();
            tela.setVisible(true);
        });
    }
}
