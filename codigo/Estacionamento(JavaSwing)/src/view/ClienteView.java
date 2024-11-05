<<<<<<< HEAD
package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ClienteView {
    private JTextField nomeField;
    private JButton adicionarButton;

    public ClienteView() {
        JFrame frame = new JFrame("Cliente");
        nomeField = new JTextField(20);
        adicionarButton = new JButton("Adicionar Cliente");
        
    }

    public String getNome() {
        return nomeField.getText();
    }

    public void adicionarClienteListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }
}
=======
package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ClienteView {
    private JTextField nomeField;
    private JButton adicionarButton;

    public ClienteView() {
        JFrame frame = new JFrame("Cliente");
        nomeField = new JTextField(20);
        adicionarButton = new JButton("Adicionar Cliente");
        
    }

    public String getNome() {
        return nomeField.getText();
    }

    public void adicionarClienteListener(ActionListener listener) {
        adicionarButton.addActionListener(listener);
    }

    public void mostrarMensagem(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }
}
>>>>>>> d9070482504422a0b550f2cc893f54b1a437c91e
