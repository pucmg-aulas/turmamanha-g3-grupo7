package view;

import javax.swing.*;
import java.awt.*;
import controller.VagaController;
import java.awt.event.ActionListener;

public class GerarVagasView extends JFrame {
    private JTextField idTextField;
    private JTextField colunasTextField;
    private JTextField linhasTextField;
    private JButton adicionarButton;

    public GerarVagasView() {
        setTitle("Gerar Vagas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("ID do Estacionamento:"));
        idTextField = new JTextField();
        panel.add(idTextField);

        panel.add(new JLabel("Número de colunas:"));
        colunasTextField = new JTextField();
        panel.add(colunasTextField);

        panel.add(new JLabel("Número de linhas:"));
        linhasTextField = new JTextField();
        panel.add(linhasTextField);

        adicionarButton = new JButton("Adicionar");
        panel.add(adicionarButton);

        add(panel);

        new VagaController(this);
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getNumeroColunas() {
        return colunasTextField.getText();
    }

    public String getNumeroLinhas() {
        return linhasTextField.getText();
    }

    public void adicionarListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GerarVagasView gerarVagasView = new GerarVagasView();
            gerarVagasView.setVisible(true);
        });
    }
}
