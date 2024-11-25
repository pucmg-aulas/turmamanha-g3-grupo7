package view;

import controller.ClienteController;
import model.ClienteModel;
import model.TicketModel;
import model.VeiculoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class MenuCliente extends JFrame {

    private JButton btnAdicionarCliente;
    private JButton btnAdicionarVeiculo;
    private JList<String> listaClientes;
    private JButton historicoButton;
    private DefaultListModel<String> listaClientesModel;
    private ClienteController clienteController;
    private List<ClienteModel> clientesCadastrados;

    public MenuCliente() {
        clienteController = new ClienteController();

        setTitle("Menu de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout());

        btnAdicionarCliente = new JButton("Adicionar Cliente");
        btnAdicionarVeiculo = new JButton("Adicionar Veículo");
        historicoButton = new JButton("Histórico");

        panelBotoes.add(btnAdicionarCliente);
        panelBotoes.add(historicoButton);
        panelBotoes.add(btnAdicionarVeiculo);

        add(panelBotoes, BorderLayout.NORTH);

        // Lista de clientes
        listaClientesModel = new DefaultListModel<>();
        listaClientes = new JList<>(listaClientesModel);
        JScrollPane scrollPane = new JScrollPane(listaClientes);

        add(scrollPane, BorderLayout.CENTER);

        atualizarListaClientes();

        // Configurar ações dos botões
        btnAdicionarCliente.addActionListener(e -> abrirAdicionarCliente());
        btnAdicionarVeiculo.addActionListener(e -> abrirAdicionarVeiculo());
        historicoButton.addActionListener(e -> exibirHistorico());

        setVisible(true);
    }


    private void atualizarListaClientes() {
        listaClientesModel.clear();
        clientesCadastrados = clienteController.listarTodosClientes();
        for (ClienteModel cliente : clientesCadastrados) {
            String clienteInfo = cliente.getId() + " - " + cliente.getNome();
            if (!cliente.getVeiculos().isEmpty()) {
                List<VeiculoModel> veiculos = cliente.getVeiculos();
                String placas = veiculos.stream()
                        .map(VeiculoModel::getPlaca)
                        .reduce((placa1, placa2) -> placa1 + ", " + placa2)
                        .orElse("");
                clienteInfo += " - Veículos: [" + placas + "]";
            }
            listaClientesModel.addElement(clienteInfo);
        }
    }

    private void abrirAdicionarCliente() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do cliente (ou deixe em branco para Anônimo):", "Adicionar Cliente", JOptionPane.PLAIN_MESSAGE);
        if (nome == null) return; // Cancelado

        if (nome.trim().isEmpty()) {
            nome = "Anônimo";
        }

        ClienteModel cliente = clienteController.adicionarCliente(nome);
        atualizarListaClientes();
    }

    private void abrirAdicionarVeiculo() {
        // Verifica se algum cliente está selecionado na lista
        int selectedIndex = listaClientes.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente na lista!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteModel clienteSelecionado = clientesCadastrados.get(selectedIndex);

        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo:", "Adicionar Veículo", JOptionPane.PLAIN_MESSAGE);
        if (placa == null || placa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Placa inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        clienteController.adicionarVeiculoAoCliente(clienteSelecionado.getId(), placa);

        atualizarListaClientes();
    }

    private void exibirHistorico() {
        int selectedIndex = listaClientes.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um cliente na lista!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClienteModel clienteSelecionado = clientesCadastrados.get(selectedIndex);

        List<TicketModel> tickets = clienteController.listarTicketsDoCliente(clienteSelecionado.getId());

        if (tickets.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum histórico encontrado para este cliente.", "Informação", JOptionPane.INFORMATION_MESSAGE);
        } else {
            exibirDialogoHistorico(tickets); // Chama o método para exibir o JDialog
        }
    }

    private void exibirDialogoHistorico(List<TicketModel> tickets) {
        JDialog dialog = new JDialog(this, "Histórico de Tickets", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        String[] colunas = {"ID Ticket", "Estacionamento", "Vaga", "Entrada", "Saída", "Custo", "Placa"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0);

        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Combobox para o mês
        JComboBox<String> comboMes = new JComboBox<>(new String[]{
                "Todos", "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        });

        // Campo de texto para o ano
        JTextField txtAno = new JTextField(4);

        // Botão para aplicar o filtro
        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> {
            String mesSelecionado = (String) comboMes.getSelectedItem();
            String anoSelecionado = txtAno.getText();
            List<TicketModel> ticketsFiltrados = filtrarTickets(tickets, mesSelecionado, anoSelecionado);
            atualizarTabela(ticketsFiltrados, tableModel);
        });

        // Painel superior com os filtros
        JPanel panelFiltros = new JPanel();
        panelFiltros.add(new JLabel("Mês:"));
        panelFiltros.add(comboMes);
        panelFiltros.add(new JLabel("Ano:"));
        panelFiltros.add(txtAno);
        panelFiltros.add(btnFiltrar);

        dialog.add(panelFiltros, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dialog.dispose());
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnFechar);
        dialog.add(panelBotoes, BorderLayout.SOUTH);

        // Exibe todos os tickets inicialmente
        atualizarTabela(tickets, tableModel);

        dialog.setVisible(true);
    }



    // Método para atualizar a tabela com uma lista de tickets
    private void atualizarTabela(List<TicketModel> tickets, DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Limpa a tabela
        for (TicketModel ticket : tickets) {
            tableModel.addRow(new Object[]{
                    ticket.getIdTicket(),
                    ticket.getIdEstacionamento(),
                    ticket.getIdVaga(),
                    ticket.getEntrada(),
                    ticket.getSaida(),
                    ticket.getCusto(),
                    ticket.getPlaca(),
            });
        }
    }

    // Método para filtrar tickets por mês e ano
    private List<TicketModel> filtrarTickets(List<TicketModel> tickets, String mes, String ano) {
        return tickets.stream()
                .filter(ticket -> {
                    boolean mesValido = mes.equals("Todos") ||
                            ticket.getEntrada().getMonthValue() == comboMesParaNumero(mes);
                    boolean anoValido = ano.isEmpty() ||
                            String.valueOf(ticket.getEntrada().getYear()).equals(ano);
                    return mesValido && anoValido;
                })
                .toList();
    }


    // Método auxiliar para converter o mês do ComboBox para um número
    private int comboMesParaNumero(String mes) {
        return switch (mes) {
            case "Janeiro" -> 1;
            case "Fevereiro" -> 2;
            case "Março" -> 3;
            case "Abril" -> 4;
            case "Maio" -> 5;
            case "Junho" -> 6;
            case "Julho" -> 7;
            case "Agosto" -> 8;
            case "Setembro" -> 9;
            case "Outubro" -> 10;
            case "Novembro" -> 11;
            case "Dezembro" -> 12;
            default -> -1; // "Todos"
        };
    }

    public static void main(String[] args) {
        new MenuCliente();
    }


}