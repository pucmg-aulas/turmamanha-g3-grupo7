<<<<<<< HEAD
package view;

import javax.swing.*;
import java.awt.*;
import controller.VagaController;
import java.awt.event.ActionListener;

public class EditarTipoVagaView extends JFrame {

    private JTextField estacionamentoIdField;
    private JTextField vagaIdField;
    private JComboBox<String> tipoVagaComboBox;
    private JButton salvarButton;

    public EditarTipoVagaView() {
        setTitle("Editar Tipo de Vaga");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Estacionamento ID:"));
        estacionamentoIdField = new JTextField();
        panel.add(estacionamentoIdField);

        panel.add(new JLabel("Vaga ID:"));
        vagaIdField = new JTextField();
        panel.add(vagaIdField);

        panel.add(new JLabel("Novo Tipo de Vaga:"));
        tipoVagaComboBox = new JComboBox<>(new String[]{"Padrão", "VIP", "PCD", "Idoso"});
        panel.add(tipoVagaComboBox);

        salvarButton = new JButton("Salvar");
        panel.add(salvarButton);

        add(panel);

        new VagaController(this);
    }

    public String getEstacionamentoId() {
        return estacionamentoIdField.getText();
    }

    public String getVagaId() {
        return vagaIdField.getText();
    }

    public String getNovoTipo() {
        return (String) tipoVagaComboBox.getSelectedItem();
    }

    public void addAlterarListener(ActionListener listener) {
        salvarButton.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarTipoVagaView editarTipoVagaView = new EditarTipoVagaView();
            editarTipoVagaView.setVisible(true);
        });
    }
}
=======
package view;

import javax.swing.*;
import java.awt.*;
import controller.VagaController;
import java.awt.event.ActionListener;

public class EditarTipoVagaView extends JFrame {

    private JTextField estacionamentoIdField;
    private JTextField vagaIdField;
    private JComboBox<String> tipoVagaComboBox;
    private JButton salvarButton;

    public EditarTipoVagaView() {
        setTitle("Editar Tipo de Vaga");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Estacionamento ID:"));
        estacionamentoIdField = new JTextField();
        panel.add(estacionamentoIdField);

        panel.add(new JLabel("Vaga ID:"));
        vagaIdField = new JTextField();
        panel.add(vagaIdField);

        panel.add(new JLabel("Novo Tipo de Vaga:"));
        tipoVagaComboBox = new JComboBox<>(new String[]{"Padrão", "VIP", "PCD", "Idoso"});
        panel.add(tipoVagaComboBox);

        salvarButton = new JButton("Salvar");
        panel.add(salvarButton);

        add(panel);

        new VagaController(this);
    }

    public String getEstacionamentoId() {
        return estacionamentoIdField.getText();
    }

    public String getVagaId() {
        return vagaIdField.getText();
    }

    public String getNovoTipo() {
        return (String) tipoVagaComboBox.getSelectedItem();
    }

    public void addAlterarListener(ActionListener listener) {
        salvarButton.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarTipoVagaView editarTipoVagaView = new EditarTipoVagaView();
            editarTipoVagaView.setVisible(true);
        });
    }
}
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
