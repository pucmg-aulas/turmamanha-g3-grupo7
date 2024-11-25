package view;

import controller.ListarEstacionamentoController;
import model.EstacionamentoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarEstacionamentosView extends JFrame {

    private JPanel panel;
    private JTable tabelaEstacionamentos;
    private DefaultTableModel tabelaModelo;

    private JButton gerenciarEstacionamentoButton;
    private JButton atualizarListaButton;
    private JButton fecharButton;
    private JButton adicionarButton;
    private JButton removerButton;

    private ListarEstacionamentoController controller;

    public ListarEstacionamentosView(ListarEstacionamentoController controller) {
        this.controller = controller;

        setTitle("Lista de Estacionamentos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        inicializarComponentes();
        carregarEstacionamentosNaTabela();
        adicionarEventos();
    }

    private void inicializarComponentes() {
        panel = new JPanel(new BorderLayout());
        setContentPane(panel);

        tabelaModelo = new DefaultTableModel(new Object[]{"ID", "Nome", "Endereço", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaEstacionamentos = new JTable(tabelaModelo);
        JScrollPane scrollPane = new JScrollPane(tabelaEstacionamentos);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        gerenciarEstacionamentoButton = new JButton("Gerenciar Estacionamento");
        atualizarListaButton = new JButton("Atualizar Lista");
        fecharButton = new JButton("Fechar");
        adicionarButton = new JButton("Adicionar");
        removerButton = new JButton("Remover");

        botoesPanel.add(gerenciarEstacionamentoButton);
        botoesPanel.add(atualizarListaButton);
        botoesPanel.add(adicionarButton);
        botoesPanel.add(removerButton);
        botoesPanel.add(fecharButton);

        panel.add(botoesPanel, BorderLayout.SOUTH);
    }

    private void adicionarEventos() {
        gerenciarEstacionamentoButton.addActionListener(e -> gerenciarEstacionamentoSelecionado());
        atualizarListaButton.addActionListener(e -> carregarEstacionamentosNaTabela());
        adicionarButton.addActionListener(e -> adicionarEstacionamento());
        removerButton.addActionListener(e -> removerEstacionamento());
        fecharButton.addActionListener(e -> dispose());
    }

    private void carregarEstacionamentosNaTabela() {
        tabelaModelo.setRowCount(0);
        List<EstacionamentoModel> estacionamentos = controller.listarEstacionamentos();

        for (EstacionamentoModel estacionamento : estacionamentos) {
            tabelaModelo.addRow(new Object[]{
                    estacionamento.getId(),
                    estacionamento.getNome(),
                    estacionamento.getEndereco(),
                    estacionamento.getTelefone()
            });
        }
    }

    private void adicionarEstacionamento() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do estacionamento:");
        String endereco = JOptionPane.showInputDialog(this, "Digite o endereço:");
        String telefone = JOptionPane.showInputDialog(this, "Digite o telefone:");
        String colunasStr = JOptionPane.showInputDialog(this, "Digite o número de colunas:");
        String vagasPorColunaStr = JOptionPane.showInputDialog(this, "Digite o número de vagas por coluna:");

        try {
            int colunas = Integer.parseInt(colunasStr);
            int vagasPorColuna = Integer.parseInt(vagasPorColunaStr);

            boolean sucesso = controller.adicionarEstacionamento(nome, endereco, telefone, colunas, vagasPorColuna);
            if (sucesso) {
                carregarEstacionamentosNaTabela();
                JOptionPane.showMessageDialog(this, "Estacionamento adicionado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar estacionamento.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerEstacionamento() {
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um estacionamento.");
            return;
        }

        int idEstacionamento = (int) tabelaModelo.getValueAt(linhaSelecionada, 0);
        boolean sucesso = controller.removerEstacionamento(idEstacionamento);

        if (sucesso) {
            carregarEstacionamentosNaTabela();
            JOptionPane.showMessageDialog(this, "Estacionamento removido com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao remover estacionamento.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void gerenciarEstacionamentoSelecionado() {
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um estacionamento.");
            return;
        }

        int idEstacionamento = (int) tabelaModelo.getValueAt(linhaSelecionada, 0);
        EstacionamentoModel estacionamento = controller.buscarEstacionamentoPorId(idEstacionamento);

        if (estacionamento != null) {
            GerenciarView gerenciarView = new GerenciarView(estacionamento);
            gerenciarView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Estacionamento não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListarEstacionamentoController controller = new ListarEstacionamentoController();
            ListarEstacionamentosView view = new ListarEstacionamentosView(controller);
            view.setVisible(true);
        });
    }
}
