package view;

import dao.EstacionamentoDAO;
import model.EstacionamentoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListarEstacionamentosView extends JFrame {

    private JPanel panel;
    private EstacionamentoDAO estacionamentoDAO;
    private JTable tabelaEstacionamentos;
    private DefaultTableModel modeloTabela;
    private JButton btnGerenciar;
    private JButton gerenciarEstacionamentoButton;
    private JButton atualizarListaButton;
    private JButton fecharButton;

    public ListarEstacionamentosView() {
        setTitle("Lista de Estacionamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        estacionamentoDAO = new EstacionamentoDAO();
        inicializarComponentes();
        carregarDadosEstacionamentos();
    }

    private void inicializarComponentes() {
        // Painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Configuração da tabela
<<<<<<< HEAD
        modeloTabela = new DefaultTableModel(new Object[] { "ID", "Nome", "Endereço", "Telefone" }, 0) {
=======
        modeloTabela = new DefaultTableModel(new Object[] { "Nome", "Endereço", "Telefone" }, 0) {
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabelaEstacionamentos = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabelaEstacionamentos);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel para botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Botão Gerenciar
        btnGerenciar = new JButton("Gerenciar Estacionamento");
        btnGerenciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciarEstacionamentoSelecionado();
            }
        });

        botoesPanel.add(btnGerenciar);
        mainPanel.add(botoesPanel, BorderLayout.SOUTH);

        // Adiciona o painel principal ao frame
        add(mainPanel);
    }

    private void gerenciarEstacionamentoSelecionado() {
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um estacionamento para gerenciar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtém os dados do estacionamento selecionado
<<<<<<< HEAD
        int idEstacionamento = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String nomeEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String enderecoEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 2);
        String telefoneEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 3);

        // Cria um objeto EstacionamentoModel com os dados selecionados
        EstacionamentoModel estacionamentoSelecionado = new EstacionamentoModel(
                idEstacionamento,
=======
        String nomeEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        String enderecoEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String telefoneEstacionamento = (String) modeloTabela.getValueAt(linhaSelecionada, 2);

        // Cria um objeto EstacionamentoModel com os dados selecionados
        EstacionamentoModel estacionamentoSelecionado = new EstacionamentoModel( 
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
                nomeEstacionamento,
                enderecoEstacionamento,
                telefoneEstacionamento
        );
        // Abre a tela GerenciarView passando o estacionamento selecionado
        GerenciarView telaGerenciar = new GerenciarView(estacionamentoSelecionado);
        telaGerenciar.setVisible(true);
    }

    private void carregarDadosEstacionamentos() {
        java.util.List<EstacionamentoModel> estacionamentos = estacionamentoDAO.listarEstacionamentos();

<<<<<<< HEAD
        modeloTabela.setRowCount(0);

        for (EstacionamentoModel estacionamento : estacionamentos) {
            modeloTabela.addRow(new Object[] {
                    estacionamento.getId(),
=======
        for (EstacionamentoModel estacionamento : estacionamentos) {
            modeloTabela.addRow(new Object[] {
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
                    estacionamento.getNome(),
                    estacionamento.getEndereco(),
                    estacionamento.getTelefone()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListarEstacionamentosView tela = new ListarEstacionamentosView();
            tela.setVisible(true);
        });
    }
<<<<<<< HEAD

    // Método para remover estacionamento
    private void removerEstacionamento() {
        int selectedRow = tabelaEstacionamentos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um estacionamento para remover.");
            return;
        }

        // Pega o ID do estacionamento selecionado
        int idEstacionamento = (int) modeloTabela.getValueAt(selectedRow, 0);
        boolean sucesso = estacionamentoDAO.removerEstacionamentoPorId(idEstacionamento);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Estacionamento removido com sucesso!");
            carregarDadosEstacionamentos(); // Recarrega a tabela
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao remover o estacionamento.");
        }
    }

=======
>>>>>>> 7fd6d79c017107357f86c21e3b81e7610c0461ec
}