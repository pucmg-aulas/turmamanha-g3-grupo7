package view;

import controller.EstacionamentoController;
import model.*;
import dao.ClienteDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class GerenciarView extends JFrame {
    private EstacionamentoModel estacionamento;
    private ClienteDAO clienteDAO;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;

    // Painéis para cada aba
    private JPanel dadosGeraisPanel;
    private JPanel vagasPanel;
    private JPanel veiculosPanel;
    private JPanel ticketsPanel;
    private JPanel clientesPanel;

    // Botões da aba de Vagas
    private JButton editarTipoVagaButton;
    private JButton gerarVagasButton;
    private JButton fecharVagasButton;

    // Botões da aba de Clientes
    private JButton adicionarClienteButton;
    private JButton removerClienteButton;

    // Campos para dados gerais
    private JTextField txtId;
    private JTextField txtNome;
    private JTextField txtEndereco;
    private JTextField txtTelefone;
    private JLabel lblArrecadado;
    private JLabel lblValorMedio; // Nova variável
    private JComboBox<String> mesComboBox;
    private JComboBox<Integer> anoComboBox;


    // Tabelas
    private JTable tabelaVagas;
    private JTable tabelaVeiculos;
    private JTable tabelaTickets;
    private JTable tabelaClientes;

    public GerenciarView(EstacionamentoModel estacionamento) {
        this.estacionamento = estacionamento;
        this.clienteDAO = new ClienteDAO();

        // Carregar vagas do estacionamento
        estacionamento.carregarVagas();

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
        inicializarClientes();

        // Adiciona as abas
        tabbedPane.addTab("Dados Gerais", dadosGeraisPanel);
        tabbedPane.addTab("Vagas", vagasPanel);
        tabbedPane.addTab("Veículos Estacionados", veiculosPanel);
        tabbedPane.addTab("Tickets Ativos", ticketsPanel);
        tabbedPane.addTab("Clientes", clientesPanel);

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

        // Valor Médio por Utilização
        gbc.gridx = 0;
        gbc.gridy = 5;
        dadosGeraisPanel.add(new JLabel("Valor Médio por Utilização:"), gbc);
        gbc.gridx = 1;
        lblValorMedio = new JLabel("R$ 0,00");
        dadosGeraisPanel.add(lblValorMedio, gbc);

        // Mês e Ano
        gbc.gridx = 0;
        gbc.gridy = 6;
        dadosGeraisPanel.add(new JLabel("Mês e Ano:"), gbc);
        gbc.gridx = 1;

        // Configurar combo boxes
        mesComboBox = new JComboBox<>(new String[]{"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"});
        anoComboBox = new JComboBox<>();
        int anoAtual = LocalDateTime.now().getYear();
        for (int ano = anoAtual - 5; ano <= anoAtual + 5; ano++) {
            anoComboBox.addItem(ano);
        }
        JPanel mesAnoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mesAnoPanel.add(mesComboBox);
        mesAnoPanel.add(anoComboBox);

        dadosGeraisPanel.add(mesAnoPanel, gbc);

        // Botão para calcular arrecadação do mês
        gbc.gridx = 1;
        gbc.gridy = 7;
        JButton btnCalcularArrecadacaoMes = new JButton("Calcular Arrecadação do Mês");
        btnCalcularArrecadacaoMes.addActionListener(e -> calcularArrecadacaoMensal());
        dadosGeraisPanel.add(btnCalcularArrecadacaoMes, gbc);
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
        gerarVagasButton = new JButton("Gerar Vagas");

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

    private void inicializarClientes() {
        clientesPanel = new JPanel(new BorderLayout());

        // Tabela de clientes
        String[] colunas = { "ID", "Nome", "Placa do Veículo" };
        tabelaClientes = new JTable(new Object[0][3], colunas);
        JScrollPane scrollPane = new JScrollPane(tabelaClientes);

        // Botões de Adicionar e Remover Clientes
        JPanel botoesClientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        adicionarClienteButton = new JButton("Adicionar Cliente");
        removerClienteButton = new JButton("Remover Cliente");

        adicionarClienteButton.addActionListener(e -> adicionarCliente());
        removerClienteButton.addActionListener(e -> removerCliente());

        botoesClientes.add(adicionarClienteButton);
        botoesClientes.add(removerClienteButton);

        clientesPanel.add(botoesClientes, BorderLayout.NORTH);
        clientesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void preencherDados() {
        // Dados gerais
        txtId.setText(String.valueOf(estacionamento.getId()));
        txtNome.setText(estacionamento.getNome());
        txtEndereco.setText(estacionamento.getEndereco());
        txtTelefone.setText(estacionamento.getTelefone());

        // Buscar arrecadação e valor médio diretamente do Controller
        EstacionamentoController controller = new EstacionamentoController(estacionamento);
        double arrecadacao = controller.getArrecadacaoTotal();
        double valorMedio = controller.getValorMedioUtilizacao();

        lblArrecadado.setText(String.format("R$ %.2f", arrecadacao));
        lblValorMedio.setText(String.format("R$ %.2f", valorMedio));

        // Preencher tabelas
        atualizarTabelaVagas();
        atualizarTabelaVeiculos();
        atualizarTabelaTickets();
        carregarClientes();
    }

    private void carregarClientes() {
        List<ClienteModel> clientes = clienteDAO.listarTodos();
        Object[][] dadosClientes = new Object[clientes.size()][3];

        for (int i = 0; i < clientes.size(); i++) {
            ClienteModel cliente = clientes.get(i);
            dadosClientes[i][0] = cliente.getId();
            dadosClientes[i][1] = cliente.getNome();
            dadosClientes[i][2] = cliente.getVeiculos().isEmpty() ? "" : cliente.getVeiculos().get(0).getPlaca();
        }

        tabelaClientes.setModel(new javax.swing.table.DefaultTableModel(
                dadosClientes,
                new String[]{"ID", "Nome", "Placa do Veículo"}
        ));
    }

    private void atualizarTabelaVagas() {
        // Filtrar as vagas associadas ao estacionamento selecionado
        List<VagaModel> vagas = estacionamento.getVagas(); // Recupera as vagas do estacionamento atual
        String[][] dadosVagas = new String[vagas.size()][4];

        for (int i = 0; i < vagas.size(); i++) {
            VagaModel vaga = vagas.get(i);
            dadosVagas[i][0] = vaga.getId(); // ID da vaga
            dadosVagas[i][1] = vaga.getTipo(); // Tipo da vaga (Padrão, PCD, VIP)
            dadosVagas[i][2] = vaga.isOcupada() ? "Ocupada" : "Livre"; // Status da vaga
            dadosVagas[i][3] = (vaga.getVeiculo() != null) ? vaga.getVeiculo().getPlaca() : ""; // Placa do veículo, se existir
        }

        // Atualizar a tabela de vagas
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
        String nomeCliente = ticket.getCliente() != null ? ticket.getCliente().getNome() : "N/A";

        dadosVeiculos[i][0] = veiculo.getPlaca();
        dadosVeiculos[i][1] = nomeCliente;
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

    // Métodos de ação para a aba de Vagas
    private void removervaga() {
        int selectedRow = tabelaVagas.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma vaga na tabela para remover.");
            return;
        }

        String idVaga = (String) tabelaVagas.getValueAt(selectedRow, 0);

        Optional<VagaModel> vagaParaRemover = estacionamento.getVagas().stream()
                .filter(vaga -> String.valueOf(vaga.getId()).equals(idVaga))
                .findFirst();

        if (vagaParaRemover.isPresent()) {
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

    // Métodos de ação para a aba de Veículos
    private void estacionarVeiculo() {
        String placa = JOptionPane.showInputDialog(this, "Informe a placa do veículo:");
        if (placa == null || placa.trim().isEmpty()) return;

        ClienteModel cliente = clienteDAO.buscarClientePorPlaca(placa);
        String nomeCliente = cliente != null ? cliente.getNome() : "Cliente não encontrado";

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

    // Métodos de ação para a aba de Clientes
    private void adicionarCliente() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente:");
        if (nome != null && !nome.trim().isEmpty()) {
            String id = clienteDAO.gerarId();
            ClienteModel cliente = new ClienteModel(nome, id);
            clienteDAO.salvarCliente(cliente);
            carregarClientes(); // Atualiza a tabela de clientes
        }
    }

    private void removerCliente() {
        int selectedRow = tabelaClientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela para remover.");
            return;
        }

        String clienteId = (String) tabelaClientes.getValueAt(selectedRow, 0);
        clienteDAO.removerCliente(clienteId);
        carregarClientes(); // Atualiza a tabela de clientes
    }

    private void calcularArrecadacaoMensal() {
        int mesSelecionado = mesComboBox.getSelectedIndex() + 1;
        int anoSelecionado = (int) anoComboBox.getSelectedItem();

        // Chamar o Controller para obter a arrecadação mensal
        EstacionamentoController controller = new EstacionamentoController(estacionamento);
        double arrecadacaoMensal = controller.getArrecadacaoMensal(mesSelecionado, anoSelecionado);

        JOptionPane.showMessageDialog(this,
                String.format("Arrecadação em %s de %d: R$ %.2f",
                        mesComboBox.getSelectedItem(), anoSelecionado, arrecadacaoMensal),
                "Arrecadação Mensal",
                JOptionPane.INFORMATION_MESSAGE);
    }

}
