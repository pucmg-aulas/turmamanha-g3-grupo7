package view;

import dao.ClienteDAO;
import model.ClienteModel;
import model.VeiculoModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuCliente extends JFrame {

    private JButton btnAdicionarCliente;
    private JButton btnAdicionarVeiculo;
    private JList<String> listaClientes;
    private DefaultListModel<String> listaClientesModel;
    private ClienteDAO clienteDAO;
    private List<ClienteModel> clientesCadastrados;

    public MenuCliente() {
        clienteDAO = new ClienteDAO();

        setTitle("Menu de Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout());

        btnAdicionarCliente = new JButton("Adicionar Cliente");
        btnAdicionarVeiculo = new JButton("Adicionar Veículo");

        panelBotoes.add(btnAdicionarCliente);
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

        setVisible(true);
    }

    private void atualizarListaClientes() {
        listaClientesModel.clear();
        clientesCadastrados = clienteDAO.listarTodos();
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

        ClienteModel cliente = new ClienteModel(nome, clienteDAO.gerarId());
        clienteDAO.salvarCliente(cliente);
        atualizarListaClientes();
    }

    private void abrirAdicionarVeiculo() {
        if (clientesCadastrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Janela para selecionar cliente
        ClienteModel clienteSelecionado = selecionarCliente();
        if (clienteSelecionado == null) return; // Cancelado

        // Solicitar placa do veículo
        String placa = JOptionPane.showInputDialog(this, "Digite a placa do veículo:", "Adicionar Veículo", JOptionPane.PLAIN_MESSAGE);
        if (placa == null || placa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Placa inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        VeiculoModel veiculo = new VeiculoModel(placa, clienteSelecionado);
        clienteDAO.adicionarVeiculoAoCliente(clienteSelecionado.getId(), veiculo);
        atualizarListaClientes();
    }

    private ClienteModel selecionarCliente() {
        String[] clientesArray = new String[clientesCadastrados.size()];
        for (int i = 0; i < clientesCadastrados.size(); i++) {
            ClienteModel cliente = clientesCadastrados.get(i);
            clientesArray[i] = cliente.getId() + " - " + cliente.getNome();
        }

        String clienteSelecionado = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o cliente:",
                "Selecionar Cliente",
                JOptionPane.PLAIN_MESSAGE,
                null,
                clientesArray,
                clientesArray[0]
        );

        if (clienteSelecionado != null) {
            String idSelecionado = clienteSelecionado.split(" - ")[0];
            return clientesCadastrados.stream()
                    .filter(cliente -> cliente.getId().equals(idSelecionado))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public static void main(String[] args) {
        new MenuCliente();
    }
}
