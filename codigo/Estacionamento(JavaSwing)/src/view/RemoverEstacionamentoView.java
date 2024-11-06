package view;

import dao.EstacionamentoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoverEstacionamentoView extends JFrame {
    private JTextField idTextField;
    private JButton removerButton;
    private EstacionamentoDAO estacionamentoDAO;

    public RemoverEstacionamentoView() {
        setTitle("Remover Estacionamento");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        estacionamentoDAO = new EstacionamentoDAO();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Margens para espaçamento
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel idLabel = new JLabel("ID do Estacionamento:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);

        idTextField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idTextField, gbc);

        removerButton = new JButton("Remover");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(removerButton, gbc);

        add(panel);

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    boolean removed = estacionamentoDAO.removerEstacionamentoPorId(id);

                    if (removed) {
                        JOptionPane.showMessageDialog(null, "Estacionamento removido com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Estacionamento não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RemoverEstacionamentoView tela = new RemoverEstacionamentoView();
            tela.setVisible(true);
        });
    }
}
