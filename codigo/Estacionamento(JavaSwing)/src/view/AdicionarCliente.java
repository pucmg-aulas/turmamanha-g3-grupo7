<<<<<<< HEAD
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdicionarCliente extends JFrame {
    private JTextField nomeTextField;
    private JButton adicionarButton;
    private JButton adicionarAnonimoButton; // Botão para adicionar cliente anônimo

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

        // Botões "Adicionar" e "Adicionar como Anônimo"
        adicionarButton = new JButton("Adicionar");
        adicionarAnonimoButton = new JButton("Adicionar como Anônimo");

        // Painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(adicionarButton);
        buttonPanel.add(adicionarAnonimoButton);

        // Adiciona componentes ao painel principal
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel ao frame
        add(panel);
    }

    // Método para adicionar o listener do botão "Adicionar"
    public void adicionarClienteListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    // Método para adicionar o listener do botão "Adicionar como Anônimo"
    public void adicionarAnonimoListener(ActionListener listener) {
        adicionarAnonimoButton.addActionListener(listener);
    }

    // Obtém o nome digitado no campo de texto
    public String getNome() {
        return nomeTextField.getText();
    }

    // Exibe uma mensagem
    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    // Método para limpar o campo de nome após adicionar
    public void limparCampoNome() {
        nomeTextField.setText("");
    }
}
=======
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdicionarCliente extends JFrame {
    private JTextField nomeTextField;
    private JButton adicionarButton;
    private JButton adicionarAnonimoButton; // Botão para adicionar cliente anônimo

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

        // Botões "Adicionar" e "Adicionar como Anônimo"
        adicionarButton = new JButton("Adicionar");
        adicionarAnonimoButton = new JButton("Adicionar como Anônimo");

        // Painel para os botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(adicionarButton);
        buttonPanel.add(adicionarAnonimoButton);

        // Adiciona componentes ao painel principal
        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Adiciona o painel ao frame
        add(panel);
    }

    // Método para adicionar o listener do botão "Adicionar"
    public void adicionarClienteListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    // Método para adicionar o listener do botão "Adicionar como Anônimo"
    public void adicionarAnonimoListener(ActionListener listener) {
        adicionarAnonimoButton.addActionListener(listener);
    }

    // Obtém o nome digitado no campo de texto
    public String getNome() {
        return nomeTextField.getText();
    }

    // Exibe uma mensagem
    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    // Método para limpar o campo de nome após adicionar
    public void limparCampoNome() {
        nomeTextField.setText("");
    }
}
>>>>>>> d9070482504422a0b550f2cc893f54b1a437c91e
