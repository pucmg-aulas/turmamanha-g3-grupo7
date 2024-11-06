package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdicionarCliente extends JFrame {
    private JTextField nomeTextField;
    private JButton adicionarButton;
    private JButton adicionarAnonimoButton;

    public AdicionarCliente() {
        setTitle("Adicionar Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuração do layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Campo de texto e rótulo para o nome
        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        inputPanel.add(new JLabel("Nome:"));
        nomeTextField = new JTextField();
        inputPanel.add(nomeTextField);

        adicionarButton = new JButton("Adicionar");
        adicionarAnonimoButton = new JButton("Adicionar como Anônimo");

        // Painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(adicionarButton);
        buttonPanel.add(adicionarAnonimoButton);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    public void adicionarClienteListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    public void adicionarAnonimoListener(ActionListener listener) {
        adicionarAnonimoButton.addActionListener(listener);
    }

    public String getNome() {
        return nomeTextField.getText();
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public void limparCampoNome() {
        nomeTextField.setText("");
    }
}
