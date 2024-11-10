<<<<<<< HEAD
package view;

import javax.swing.*;
import java.awt.*;
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

        panel.add(new JLabel("Número de Linhas:"));
        colunasTextField = new JTextField();
        panel.add(colunasTextField);

        panel.add(new JLabel("Número de Colunas:"));
        linhasTextField = new JTextField();
        panel.add(linhasTextField);

        adicionarButton = new JButton("Adicionar");
        panel.add(adicionarButton);

        add(panel);
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
}
=======
package view;

import javax.swing.*;
import java.awt.*;
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

        panel.add(new JLabel("Número de Linhas:"));
        colunasTextField = new JTextField();
        panel.add(colunasTextField);

        panel.add(new JLabel("Número de Colunas:"));
        linhasTextField = new JTextField();
        panel.add(linhasTextField);

        adicionarButton = new JButton("Adicionar");
        panel.add(adicionarButton);

        add(panel);
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
}
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
