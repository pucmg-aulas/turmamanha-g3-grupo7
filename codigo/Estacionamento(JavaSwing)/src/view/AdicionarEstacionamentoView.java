package view;

import dao.EstacionamentoDAO;
import model.EstacionamentoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarEstacionamentoView extends JFrame {

    private JTextField nomeTextField;
    private JTextField enderecoTextField;
    private JTextField telefoneTextField;
    private JButton adicionarButton;
    private EstacionamentoDAO estacionamentoDAO;

    public AdicionarEstacionamentoView() {
        setTitle("Adicionar Estacionamento");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        estacionamentoDAO = new EstacionamentoDAO();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nomeLabel = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(nomeLabel, gbc);

        nomeTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nomeTextField, gbc);

        JLabel enderecoLabel = new JLabel("Endereço:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(enderecoLabel, gbc);

        enderecoTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(enderecoTextField, gbc);

        JLabel telefoneLabel = new JLabel("Telefone:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(telefoneLabel, gbc);

        telefoneTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(telefoneTextField, gbc);

        adicionarButton = new JButton("Adicionar");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(adicionarButton, gbc);

        add(panel);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = nomeTextField.getText();
                String endereco = enderecoTextField.getText();
                String telefone = telefoneTextField.getText();

                // Cria um novo modelo de estacionamento e salva no arquivo
                EstacionamentoModel estacionamento = new EstacionamentoModel(nome, endereco, telefone);
                estacionamentoDAO.salvarEstacionamento(estacionamento);

                JOptionPane.showMessageDialog(null, "Estacionamento adicionado com sucesso!");
                dispose(); // Fecha a janela após adicionar
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdicionarEstacionamentoView tela = new AdicionarEstacionamentoView();
            tela.setVisible(true);
        });
    }
}
