package view;

import model.EstacionamentoModel;
import model.VagaModel;
import model.VeiculoModel;
import model.TicketEstacionamentoModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GerenciarView extends JFrame {
    private EstacionamentoModel estacionamento;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    // Painéis para cada aba
    private JPanel dadosGeraisPanel;
    private JPanel vagasPanel;
    private JPanel veiculosPanel;
    private JPanel ticketsPanel;

    // Campos para dados gerais
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JLabel lblArrecadado;

    // Tabelas
    private JTable tabelaVagas;
    private JTable tabelaVeiculos;
    private JTable tabelaTickets;

    public GerenciarView(EstacionamentoModel estacionamento) {
        this.estacionamento = estacionamento;

        setTitle("Gerenciar Estacionamento - " + estacionamento.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        preencherDados();
    }

    private void inicializarComponentes() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Inicializa os painéis das abas
        inicializarDadosGerais();
        inicializarVagas();
        inicializarVeiculos();
        inicializarTickets();

        // Adiciona as abas
        tabbedPane.addTab("Dados Gerais", dadosGeraisPanel);
        tabbedPane.addTab("Vagas", vagasPanel);
        tabbedPane.addTab("Veículos Estacionados", veiculosPanel);
        tabbedPane.addTab("Tickets Ativos", ticketsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnFechar = new JButton("Fechar");

        btnFechar.addActionListener(e -> dispose());

        botoesPanel.add(btnFechar);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void inicializarDadosGerais() {
        dadosGeraisPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        dadosGeraisPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        dadosGeraisPanel.add(txtId, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        dadosGeraisPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(30);
        txtNome.setEditable(false);
        dadosGeraisPanel.add(txtNome, gbc);

        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 2;
        dadosGeraisPanel.add(new JLabel("Endereço:"), gbc);
        gbc.gridx = 1;
        txtEndereco = new JTextField(30);
        txtEndereco.setEditable(false);
        dadosGeraisPanel.add(txtEndereco, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        dadosGeraisPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        txtTelefone = new JTextField(15);
        txtTelefone.setEditable(false);
        dadosGeraisPanel.add(txtTelefone, gbc);

        // Valor Arrecadado
        gbc.gridx = 0;
        gbc.gridy = 4;
        dadosGeraisPanel.add(new JLabel("Valor Arrecadado:"), gbc);
        gbc.gridx = 1;
        lblArrecadado = new JLabel("R$ 0,00");
        dadosGeraisPanel.add(lblArrecadado, gbc);
    }

    private void inicializarVagas() {
        vagasPanel = new JPanel(new BorderLayout());

        // Tabela de vagas
        String[] colunas = { "ID", "Tipo", "Status", "Veículo" };
        tabelaVagas = new JTable(new Object[0][4], colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaVagas);

        // Botões
        JPanel botoesVagas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionarVaga = new JButton("Adicionar Vaga");
        JButton btnRemoverVaga = new JButton("Remover Vaga");

        botoesVagas.add(btnAdicionarVaga);
        botoesVagas.add(btnRemoverVaga);

        vagasPanel.add(botoesVagas, BorderLayout.NORTH);
        vagasPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void inicializarVeiculos() {
        veiculosPanel = new JPanel(new BorderLayout());

        // Tabela de veículos
        String[] colunas = { "Placa", "Modelo", "Vaga", "Hora Entrada" };
        tabelaVeiculos = new JTable(new Object[0][4], colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaVeiculos);

        veiculosPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void inicializarTickets() {
        ticketsPanel = new JPanel(new BorderLayout());

        // Tabela de tickets
        String[] colunas = { "ID Vaga", "Placa", "Entrada", "Valor" };
        tabelaTickets = new JTable(new Object[0][4], colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaTickets);

        ticketsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void preencherDados() {
        // Dados gerais
        txtId.setText(String.valueOf(estacionamento.getId()));
        txtNome.setText(estacionamento.getNome());
        txtEndereco.setText(estacionamento.getEndereco());
        txtTelefone.setText(estacionamento.getTelefone());
        lblArrecadado.setText(String.format("R$ %.2f", estacionamento.getPrecoArrecadado()));

        // Preencher tabela de vagas
        atualizarTabelaVagas();

        // Preencher tabela de veículos
        atualizarTabelaVeiculos();

        // Preencher tabela de tickets
        atualizarTabelaTickets();
    }

    private void atualizarTabelaVagas() {
        // Implementar atualização da tabela de vagas
    }

    private void atualizarTabelaVeiculos() {
        // Implementar atualização da tabela de veículos
    }

    private void atualizarTabelaTickets() {
        // Implementar atualização da tabela de tickets
    }

}