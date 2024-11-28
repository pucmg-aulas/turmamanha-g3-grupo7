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
        inicializarTickets();
        inicializarClientes();

        // Adiciona as abas
        tabbedPane.addTab("Dados Gerais", dadosGeraisPanel);
        tabbedPane.addTab("Vagas", vagasPanel);
        tabbedPane.addTab("Tickets Ativos", ticketsPanel);
        tabbedPane.addTab("Clientes", clientesPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == ticketsPanel) {
                carregarTicketsAtivos(); // Carregar os tickets ativos ao acessar a aba
            }
        });


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
        JButton btnRemoverVaga = new JButton("Remover Vaga");
        JButton btnEstacionar = new JButton("Estacionar");
        JButton btnLiberarVaga = new JButton("Liberar Vaga"); // Corrigido aqui


        btnEstacionar.addActionListener(e -> estacionarVeiculo());
        btnLiberarVaga.addActionListener(e -> liberarVaga()); // Listener para liberar vaga

        // Adicionar os botões ao painel
        botoesVagas.add(btnEstacionar);
        botoesVagas.add(btnLiberarVaga);

        // Adiciona os componentes ao painel de vagas
        vagasPanel.add(botoesVagas, BorderLayout.NORTH);
        vagasPanel.add(scrollPane, BorderLayout.CENTER);
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
        // Recarrega as vagas do banco para garantir dados atualizados
        estacionamento.carregarVagas(); // Certifique-se de que esse método atualiza as vagas no modelo

        List<VagaModel> vagas = estacionamento.getVagas();
        String[][] dadosVagas = new String[vagas.size()][4];

        // Instancia o Controller fora do loop para evitar múltiplas instâncias
        EstacionamentoController controller = new EstacionamentoController(estacionamento);

        for (int i = 0; i < vagas.size(); i++) {
            VagaModel vaga = vagas.get(i);
            dadosVagas[i][0] = vaga.getId();
            dadosVagas[i][1] = vaga.getTipo();
            dadosVagas[i][2] = vaga.isOcupada() ? "Ocupada" : "Livre";

            // Buscar o veículo associado à vaga, se ocupada
            dadosVagas[i][3] = vaga.isOcupada() ? controller.getVeiculoPorVaga(vaga.getId()) : "";
        }

        // Atualiza a tabela com os novos dados
        tabelaVagas.setModel(new javax.swing.table.DefaultTableModel(
                dadosVagas,
                new String[]{"ID", "Tipo", "Status", "Veículo"}
        ));
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

    private void estacionarVeiculo() {
        int selectedRow = tabelaVagas.getSelectedRow(); // Recupera a linha selecionada
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma vaga para estacionar.");
            return;
        }

        String idVaga = (String) tabelaVagas.getValueAt(selectedRow, 0); // ID da vaga selecionada
        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo:");

        if (placa == null || placa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A placa do veículo não pode estar vazia.");
            return;
        }

        // Criar instância do Controller
        EstacionamentoController controller = new EstacionamentoController(estacionamento);

        // Delegar ao Controller para estacionar o veículo
        boolean sucesso = controller.estacionarVeiculo(idVaga, placa);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Veículo estacionado com sucesso!");
            atualizarTabelaVagas(); // Atualizar a tabela de vagas para refletir o novo status
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao estacionar o veículo. Verifique a vaga selecionada ou a placa.");
        }
    }

    private void carregarTicketsAtivos() {
        EstacionamentoController controller = new EstacionamentoController(estacionamento);
        List<TicketModel> ticketsAtivos = controller.getTicketsAtivos(); // Busca todos os tickets ativos

        Object[][] dadosTickets = new Object[ticketsAtivos.size()][5]; // 5 colunas: ID Ticket, ID Vaga, Placa, Entrada, Valor

        for (int i = 0; i < ticketsAtivos.size(); i++) {
            TicketModel ticket = ticketsAtivos.get(i);
            dadosTickets[i][0] = ticket.getIdTicket();    // ID do Ticket
            dadosTickets[i][1] = ticket.getIdVaga();     // ID da Vaga
            dadosTickets[i][2] = ticket.getPlaca();      // Placa do Veículo
            dadosTickets[i][3] = ticket.getEntrada();    // Hora de Entrada
            dadosTickets[i][4] = ticket.getCusto();      // Valor Atual (geralmente 0 para tickets em aberto)
        }

        tabelaTickets.setModel(new javax.swing.table.DefaultTableModel(
                dadosTickets,
                new String[]{"ID Ticket", "ID Vaga", "Placa", "Entrada", "Valor"} // Cabeçalhos das colunas
        ));
    }

    private void liberarVaga() {
        int selectedRow = tabelaVagas.getSelectedRow(); // Recupera a linha selecionada
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma vaga para liberar.");
            return;
        }

        String idVaga = (String) tabelaVagas.getValueAt(selectedRow, 0); // ID da vaga selecionada

        // Chamar o Controller para liberar a vaga
        EstacionamentoController controller = new EstacionamentoController(estacionamento);
        Double preco = controller.liberarVaga(idVaga);

        if (preco != null) {
            JOptionPane.showMessageDialog(this,
                    String.format("Vaga liberada com sucesso!\nPreço: R$ %.2f", preco),
                    "Vaga Liberada",
                    JOptionPane.INFORMATION_MESSAGE);

            atualizarTabelaVagas(); // Atualizar a tabela de vagas
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erro ao liberar a vaga. Verifique se está ocupada.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
