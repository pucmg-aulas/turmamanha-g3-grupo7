package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerenciarView extends JFrame {

    private JPanel panel;
    private JButton gerarVagasButton;
    private JButton editarTipoVagaButton;
    private JButton fecharButton;

    public GerenciarView() {
        setTitle("Gerenciar Vagas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        gerarVagasButton = new JButton("Gerar Vagas");
        gerarVagasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ação para gerar vagas.");
            }
        });
        panel.add(gerarVagasButton);

        editarTipoVagaButton = new JButton("Editar o Tipo da Vaga");
        editarTipoVagaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ação para editar o tipo de vaga.");
            }
        });
        panel.add(editarTipoVagaButton);

        // Botão "Fechar"
        fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(fecharButton);

        add(panel);
    }

    // Método main para testar a interface
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GerenciarView gerenciarVagasView = new GerenciarView();
            gerenciarVagasView.setVisible(true);
        });
    }
}
