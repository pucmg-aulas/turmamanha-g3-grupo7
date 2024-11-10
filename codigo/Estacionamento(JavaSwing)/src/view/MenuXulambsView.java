package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuXulambsView extends JFrame {

    private JButton adicionarEstacionamentoButton;
    private JButton removerEstacionamentoButton;
    private JButton menuDeClienteButton;
    private JButton listarEstacionamentosButton;

    public MenuXulambsView() {
        setTitle("Menu Xulambs");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        adicionarEstacionamentoButton = new JButton("Adicionar Estacionamento");
        adicionarEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarEstacionamentoView().setVisible(true);
            }
        });

        removerEstacionamentoButton = new JButton("Remover Estacionamento");
        removerEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RemoverEstacionamentoView().setVisible(true);
            }
        });

        listarEstacionamentosButton = new JButton("Listar Estacionamentos");
        listarEstacionamentosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListarEstacionamentosView().setVisible(true);
            }
        });

        menuDeClienteButton = new JButton("Menu de Cliente");
        menuDeClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MenuCliente().setVisible(true);
            }
        });

        panel.add(adicionarEstacionamentoButton);
        panel.add(removerEstacionamentoButton);
        panel.add(menuDeClienteButton);
        panel.add(listarEstacionamentosButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuXulambsView tela = new MenuXulambsView();
            tela.setVisible(true);
        });
    }
}
