package view;

import dao.ClienteDAO;
import model.VeiculoModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoverVeiculo extends JFrame {

    private JPanel panel1;
    private JTextField idTextField;
    private JTextField placaTextField;
    private JButton removerButton;
    private ClienteDAO clienteDAO;

    public RemoverVeiculo() {
        clienteDAO = new ClienteDAO(); // Inicializa o DAO

        setTitle("Remover Veículo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel1.add(idLabel, gbc);

        idTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(idTextField, gbc);

        JLabel placaLabel = new JLabel("Placa:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel1.add(placaLabel, gbc);

        placaTextField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(placaTextField, gbc);

        removerButton = new JButton("Remover");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel1.add(removerButton, gbc);

        add(panel1);

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
                String placa = placaTextField.getText();

                // Chama o método para remover o veículo do cliente
                boolean sucesso = clienteDAO.removerVeiculoDoCliente(id, placa);

                if (sucesso) {
                    JOptionPane.showMessageDialog(null, "Veículo removido com sucesso:\nID: " + id + "\nPlaca: " + placa);
                } else {
                    JOptionPane.showMessageDialog(null, "Veículo com a placa " + placa + " não encontrado para o cliente com ID " + id, "Erro", JOptionPane.ERROR_MESSAGE);
                }
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RemoverVeiculo tela = new RemoverVeiculo();
            tela.setVisible(true);
        });
    }
}