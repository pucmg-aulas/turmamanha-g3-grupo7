package view;

import dao.ClienteDAO;
import model.VeiculoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarVeiculo extends JFrame {

    private JTextField idTextField;
    private JTextField placaTextField;
    private JButton adicionarButton;
    private ClienteDAO clienteDAO;

    public AdicionarVeiculo() {
        clienteDAO = new ClienteDAO(); // Inicializa o DAO

        setTitle("Adicionar Veículo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(idLabel, gbc);

        idTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(idTextField, gbc);

        JLabel placaLabel = new JLabel("Placa:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(placaLabel, gbc);

        placaTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(placaTextField, gbc);

        adicionarButton = new JButton("Adicionar");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(adicionarButton, gbc);

        add(panel);

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String placa = placaTextField.getText();

                // Adiciona o veículo ao cliente com o ID especificado
                clienteDAO.adicionarVeiculoAoCliente(id, new VeiculoModel(placa));
                JOptionPane.showMessageDialog(null, "Veículo adicionado:\nID: " + id + "\nPlaca: " + placa);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdicionarVeiculo tela = new AdicionarVeiculo();
            tela.setVisible(true);
        });
    }
}
