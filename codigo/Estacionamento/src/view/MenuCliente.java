package view;

import controller.ClienteController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuCliente {
    private JPanel contentPane; // Painel principal
    private JList<String> ListaCliente; // Lista para exibir os clientes
    private JButton adicionarClienteButton; // Botão "Adicionar Cliente"
    private JButton adicionarVeiculoButton; // Botão "Adicionar Veículo"

    public MenuCliente(ClienteController controller) {
        // Inicializa os componentes da interface
        initComponents();

        // Configura o modelo da lista no controlador
        ListaCliente.setModel(controller.getClienteListModel());

        // Ações dos botões
        adicionarClienteButton.addActionListener(e -> controller.adicionarCliente((JFrame) SwingUtilities.getWindowAncestor(contentPane)));
        adicionarVeiculoButton.addActionListener(e -> {
            String clienteSelecionado = ListaCliente.getSelectedValue();
            controller.adicionarVeiculo((JFrame) SwingUtilities.getWindowAncestor(contentPane), clienteSelecionado);
        });
    }

    private void initComponents() {
        // Painel principal com BorderLayout
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Lista de clientes com barra de rolagem
        ListaCliente = new JList<>();
        JScrollPane scrollPane = new JScrollPane(ListaCliente);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0)); // Espaçamento superior
        adicionarClienteButton = new JButton("Adicionar Cliente");
        adicionarVeiculoButton = new JButton("Adicionar Veículo");

        // Adiciona os botões ao painel
        buttonPanel.add(adicionarClienteButton);
        buttonPanel.add(adicionarVeiculoButton);

        // Adiciona o painel de botões na parte inferior
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}
