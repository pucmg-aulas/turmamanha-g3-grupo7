package view;

import dao.EstacionamentoDAO;
import model.EstacionamentoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarEstacionamentosView extends JFrame {

    // Componentes principais
    private JPanel panel; // Painel principal
    private JTable tabelaEstacionamentos; // Tabela para exibir os estacionamentos
    private DefaultTableModel tabelaModelo; // Modelo da tabela
    private JButton gerenciarEstacionamentoButton; // Botão "Gerenciar Estacionamento"
    private JButton atualizarListaButton; // Botão "Atualizar Lista"
    private JButton fecharButton; // Botão "Fechar"

    // DAO para acessar os dados
    private EstacionamentoDAO estacionamentoDAO;

    public ListarEstacionamentosView() {
        setTitle("Lista de Estacionamentos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        estacionamentoDAO = new EstacionamentoDAO(); // Inicializa o DAO

        inicializarComponentes();
        carregarEstacionamentosNaTabela(); // Preenche a tabela com os dados
        adicionarEventos(); // Adiciona os eventos aos botões
    }

    private void inicializarComponentes() {
        // Inicializa o painel principal
        panel = new JPanel(new BorderLayout());
        setContentPane(panel); // Define como o conteúdo principal do JFrame

        // Configuração da tabela
        tabelaModelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Endereço", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabelaEstacionamentos = new JTable(tabelaModelo);
        JScrollPane scrollPane = new JScrollPane(tabelaEstacionamentos);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Botão "Gerenciar Estacionamento"
        gerenciarEstacionamentoButton = new JButton("Gerenciar Estacionamento");
        botoesPanel.add(gerenciarEstacionamentoButton);

        // Botão "Atualizar Lista"
        atualizarListaButton = new JButton("Atualizar Lista");
        botoesPanel.add(atualizarListaButton);

        // Botão "Fechar"
        fecharButton = new JButton("Fechar");
        botoesPanel.add(fecharButton);

        // Adiciona o painel de botões ao painel principal
        panel.add(botoesPanel, BorderLayout.SOUTH);
    }

    private void adicionarEventos() {
        // Evento para o botão "Gerenciar Estacionamento"
        gerenciarEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciarEstacionamentoSelecionado();
            }
        });

        // Evento para o botão "Atualizar Lista"
        atualizarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarEstacionamentosNaTabela();
            }
        });

        // Evento para o botão "Fechar"
        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });
    }

    private void carregarEstacionamentosNaTabela() {
        // Limpa os dados existentes na tabela
        tabelaModelo.setRowCount(0);

        // Obtém os dados do DAO
        List<EstacionamentoModel> estacionamentos = estacionamentoDAO.listarEstacionamentos();

        // Verifica se há dados para exibir
        if (estacionamentos == null || estacionamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum estacionamento encontrado.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Preenche a tabela com os dados dos estacionamentos
        for (EstacionamentoModel estacionamento : estacionamentos) {
            tabelaModelo.addRow(new Object[]{
                    estacionamento.getId(),
                    estacionamento.getNome(),
                    estacionamento.getEndereco(),
                    estacionamento.getTelefone()
            });
        }
    }

    private void gerenciarEstacionamentoSelecionado() {
        // Obtém a linha selecionada na tabela
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um estacionamento para gerenciar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém os dados da linha selecionada
        int idEstacionamento = (int) tabelaModelo.getValueAt(linhaSelecionada, 0);
        String nomeEstacionamento = (String) tabelaModelo.getValueAt(linhaSelecionada, 1);
        String enderecoEstacionamento = (String) tabelaModelo.getValueAt(linhaSelecionada, 2);
        String telefoneEstacionamento = (String) tabelaModelo.getValueAt(linhaSelecionada, 3);

        // Cria um objeto EstacionamentoModel com os dados selecionados
        EstacionamentoModel estacionamentoSelecionado = new EstacionamentoModel(
                idEstacionamento,
                nomeEstacionamento,
                enderecoEstacionamento,
                telefoneEstacionamento
        );

        // Abre a tela de gerenciamento (GerenciarView)
        GerenciarView gerenciarView = new GerenciarView(estacionamentoSelecionado);
        gerenciarView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListarEstacionamentosView tela = new ListarEstacionamentosView();
            tela.setVisible(true);
        });
    }
}
