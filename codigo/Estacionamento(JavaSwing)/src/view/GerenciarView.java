package view;

import javax.swing.*;
import java.awt.*;
import controller.VagaController;

public class GerenciarView extends JFrame {

    private JPanel panel;
    private JButton gerarVagasButton;
    private JButton editarTipoVagaButton;
    private JButton fecharButton;

    public GerenciarView() {
        setTitle("Gerenciar Vagas");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ajuste para fechar apenas esta janela
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        // Botão para abrir a tela de Gerar Vagas
        gerarVagasButton = new JButton("Gerar Vagas");
        gerarVagasButton.addActionListener(e -> {
            GerarVagasView gerarVagasView = new GerarVagasView();
            new VagaController(gerarVagasView); // Configura o controlador para GerarVagasView uma única vez
            gerarVagasView.setVisible(true);
        });
        panel.add(gerarVagasButton);

        // Botão para abrir a tela de Editar Tipo de Vaga
        editarTipoVagaButton = new JButton("Editar o Tipo da Vaga");
        editarTipoVagaButton.addActionListener(e -> {
            EditarTipoVagaView editarTipoVagaView = new EditarTipoVagaView();
            new VagaController(editarTipoVagaView); // Configura o controlador para EditarTipoVagaView
            editarTipoVagaView.setVisible(true);
        });
        panel.add(editarTipoVagaButton);

        // Botão "Fechar"
        fecharButton = new JButton("Fechar");
        fecharButton.addActionListener(e -> dispose()); // Fecha apenas esta janela
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
