package view;

import model.EstacionamentoModel;
import model.VagaModel;
import model.VeiculoModel;
import model.TicketEstacionamentoModel;
import model.VagaFactoryModel;
import controller.VagaController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.*;
import java.awt.*;


public class GerenciarView extends JFrame {
    private Button panel;
    private EstacionamentoModel estacionamento;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    // Painéis para cada aba
    private JPanel dadosGeraisPanel;
    private JPanel vagasPanel;
    private JPanel veiculosPanel;
    private JPanel ticketsPanel;

    private JButton editarTipoVagaButton;
    private JButton gerarVagasButton;
    private JButton fecharVagasButton;


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
        JButton gerarVagasButton = new JButton("Gerar Vagas");

        // Adiciona ActionListener ao botão de Adicionar Vaga
        btnAdicionarVaga.addActionListener(e -> {
            // Diálogo simples para obter informações da nova vaga
            String idVaga = JOptionPane.showInputDialog(this, "Digite o ID da nova vaga:");
            String[] tipos = { "Padrão", "PCD", "Idoso", "VIP" };
            int tipoSelecionado = JOptionPane.showOptionDialog(this,
                    "Selecione o tipo de vaga:",
                    "Adicionar Vaga",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    tipos,
                    tipos[0]);

            if (idVaga != null && tipoSelecionado >= 0) {
                // Cria a nova vaga usando a fábrica
                VagaModel novaVaga = VagaFactoryModel.criarVaga(tipoSelecionado + 1, idVaga);
                if (novaVaga != null) {
                    // Adiciona a nova vaga ao estacionamento
                    estacionamento.getVagas().add(novaVaga);
                    // Atualiza a tabela de vagas
                    atualizarTabelaVagas();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao criar a nova vaga.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        gerarVagasButton.addActionListener(e -> {
            GerarVagasView gerarVagasView = new GerarVagasView();
            new VagaController(gerarVagasView);
            gerarVagasView.setVisible(true);

            atualizarTabelaVagas();

        });

        botoesVagas.add(btnAdicionarVaga);
        botoesVagas.add(btnRemoverVaga);
        botoesVagas.add(gerarVagasButton);

        btnRemoverVaga.addActionListener(e -> removervaga());

        // Adiciona os componentes ao painel de vagas
        vagasPanel.add(botoesVagas, BorderLayout.NORTH);
        vagasPanel.add(scrollPane, BorderLayout.CENTER);
    }


    private void inicializarVeiculos() {
        veiculosPanel = new JPanel(new BorderLayout());

        // Tabela de veículos
        String[] colunas = { "Placa", "Modelo", "Vaga", "Hora Entrada" };
        tabelaVeiculos = new JTable(new Object[0][4], colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaVeiculos);

        JPanel botoesVeiculo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionarVeiculo = new JButton("Estacionar Veiculo");
        JButton btnRemoverVeiculo = new JButton("Remover Veiculo");

        btnAdicionarVeiculo.addActionListener(e -> estacionarVeiculo());
        btnRemoverVeiculo.addActionListener(e -> removerVeiculo());

        botoesVeiculo.add(btnAdicionarVeiculo);
        botoesVeiculo.add(btnRemoverVeiculo);

        veiculosPanel.add(botoesVeiculo, BorderLayout.NORTH);
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
        List<VagaModel> vagas = estacionamento.getVagas();

        // Cria uma matriz para os dados da tabela de vagas
        String[][] dadosVagas = new String[vagas.size()][4];
        for (int i = 0; i < vagas.size(); i++) {
            VagaModel vaga = vagas.get(i);
            dadosVagas[i][0] = String.valueOf(vaga.getId());                // ID da Vaga
            dadosVagas[i][1] = vaga.getTipo();                              // Tipo da Vaga
            dadosVagas[i][2] = vaga.isOcupada() ? "Ocupada" : "Livre";      // Status
            dadosVagas[i][3] = vaga.getVeiculo() != null ? vaga.getVeiculo().getPlaca() : ""; // Placa do Veículo
        }

        // Atualiza o modelo da tabela com os dados
        tabelaVagas.setModel(new javax.swing.table.DefaultTableModel(
            dadosVagas,
            new String[]{"ID", "Tipo", "Status", "Veículo"}
        ));
    }

    private void atualizarTabelaVeiculos() {
        Map<String, TicketEstacionamentoModel> ticketsAtivos = estacionamento.getTicketsAtivos();
        List<TicketEstacionamentoModel> tickets = new ArrayList<>(ticketsAtivos.values());

        String[][] dadosVeiculos = new String[tickets.size()][4];
        for (int i = 0; i < tickets.size(); i++) {
            TicketEstacionamentoModel ticket = tickets.get(i);
            VeiculoModel veiculo = ticket.getVeiculo();
            dadosVeiculos[i][0] = veiculo.getPlaca();
            dadosVeiculos[i][1] = ticket.getCliente() != null ? ticket.getCliente().getNome() : "";
            dadosVeiculos[i][2] = String.valueOf(ticket.getVaga().getId());
            dadosVeiculos[i][3] = ticket.getDataHoraEntrada().toString();
        }

        tabelaVeiculos.setModel(new javax.swing.table.DefaultTableModel(
                dadosVeiculos,
                new String[]{"Placa", "Cliente", "Vaga", "Hora Entrada"}
        ));
    }

    private void atualizarTabelaTickets() {
        Map<String, TicketEstacionamentoModel> ticketsAtivos = estacionamento.getTicketsAtivos();
        List<TicketEstacionamentoModel> tickets = new ArrayList<>(ticketsAtivos.values());

        String[][] dadosTickets = new String[tickets.size()][4];
        for (int i = 0; i < tickets.size(); i++) {
            TicketEstacionamentoModel ticket = tickets.get(i);
            dadosTickets[i][0] = String.valueOf(ticket.getVaga().getId());
            dadosTickets[i][1] = ticket.getVeiculo().getPlaca();
            dadosTickets[i][2] = ticket.getDataHoraEntrada().toString();
            dadosTickets[i][3] = ticket.getDataHoraSaida() == null
                    ? "Em Aberto"
                    : String.format("R$ %.2f", ticket.getPrecoTotal());
        }

        tabelaTickets.setModel(new javax.swing.table.DefaultTableModel(
                dadosTickets,
                new String[]{"ID Vaga", "Placa", "Entrada", "Valor"}
        ));
    }

    private void estacionarVeiculo() {
        String placa = JOptionPane.showInputDialog(this, "Informe a placa do veículo:");
        if (placa == null || placa.trim().isEmpty()) return;

        Optional<VagaModel> vagaDisponivel = estacionamento.getVagas().stream()
                .filter(vaga -> !vaga.isOcupada())
                .findFirst();

        if (vagaDisponivel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma vaga disponível.");
            return;
        }

        VagaModel vaga = vagaDisponivel.get();
        VeiculoModel veiculo = new VeiculoModel(placa);

        LocalDateTime horaEntrada = LocalDateTime.now();
        TicketEstacionamentoModel ticket = new TicketEstacionamentoModel(vaga, veiculo, horaEntrada, null);

        estacionamento.adicionarTicketAtivo(String.valueOf(vaga.getId()), ticket);
        vaga.setOcupada(true);

        atualizarTabelaVeiculos();
        atualizarTabelaVagas();
        atualizarTabelaTickets();
    }

    private void removerVeiculo() {
        int selectedRow = tabelaVeiculos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela para remover.");
            return;
        }

        String placa = (String) tabelaVeiculos.getValueAt(selectedRow, 0);
        TicketEstacionamentoModel ticket = estacionamento.getTicketsAtivos()
                .values()
                .stream()
                .filter(t -> t.getVeiculo().getPlaca().equals(placa))
                .findFirst()
                .orElse(null);

        if (ticket == null) {
            JOptionPane.showMessageDialog(this, "Ticket não encontrado para o veículo selecionado.");
            return;
        }

        ticket.registrarSaida(LocalDateTime.now());
        estacionamento.incrementarPrecoArrecadado(ticket.getPrecoTotal());

        VagaModel vaga = ticket.getVaga();
        vaga.setOcupada(false);

        estacionamento.removerTicketAtivo(String.valueOf(vaga.getId()));

        atualizarTabelaVeiculos();
        atualizarTabelaVagas();
        atualizarTabelaTickets();
        lblArrecadado.setText(String.format("R$ %.2f", estacionamento.getPrecoArrecadado()));
    }

    private void removervaga() {
        int selectedRow = tabelaVagas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma vaga na tabela para remover.");
            return;
        }

        String idVaga = (String) tabelaVagas.getValueAt(selectedRow, 0);

        // Procura a vaga no estacionamento
        Optional<VagaModel> vagaParaRemover = estacionamento.getVagas().stream()
                .filter(vaga -> String.valueOf(vaga.getId()).equals(idVaga))
                .findFirst();

        if (vagaParaRemover.isPresent()) {
            // Verifica se a vaga está ocupada
            if (vagaParaRemover.get().isOcupada()) {
                JOptionPane.showMessageDialog(this, "Não é possível remover uma vaga ocupada.");
            } else {
                estacionamento.getVagas().remove(vagaParaRemover.get());
                atualizarTabelaVagas();
                JOptionPane.showMessageDialog(this, "Vaga removida com sucesso.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vaga não encontrada.");
        }
    }

}