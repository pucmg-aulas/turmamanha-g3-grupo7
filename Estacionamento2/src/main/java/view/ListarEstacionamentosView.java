package view;

import controller.ListarEstacionamentoController;
import model.EstacionamentoModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private ListarEstacionamentoController estacionamentoController;

    public ListarEstacionamentosView() {
        setTitle("Lista de Estacionamentos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        estacionamentoController = new ListarEstacionamentoController();

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
        gerenciarEstacionamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerenciarEstacionamentoSelecionado();
            }
        });

        atualizarListaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarEstacionamentosNaTabela();
            }
        });

        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarEstacionamento();
            }
        });

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerEstacionamento();
            }
        });

        fecharButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void carregarEstacionamentosNaTabela() {
        tabelaModelo.setRowCount(0);
        List<EstacionamentoModel> estacionamentos = estacionamentoController.listarEstacionamentos();

        if (estacionamentos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum estacionamento encontrado.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

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
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um estacionamento para gerenciar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEstacionamento = (int) tabelaModelo.getValueAt(linhaSelecionada, 0);
        EstacionamentoModel estacionamento = estacionamentoController.buscarEstacionamentoPorId(idEstacionamento);

        if (estacionamento != null) {
            GerenciarView gerenciarView = new GerenciarView(estacionamento);
            gerenciarView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Estacionamento não encontrado.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarEstacionamento() {
        String nome = JOptionPane.showInputDialog(this, "Digite o nome do estacionamento:");
        String endereco = JOptionPane.showInputDialog(this, "Digite o endereço:");
        String telefone = JOptionPane.showInputDialog(this, "Digite o telefone:");

        if (nome != null && endereco != null && telefone != null) {
            EstacionamentoModel novoEstacionamento = new EstacionamentoModel(nome, endereco, telefone);
            estacionamentoController.adicionarEstacionamento(novoEstacionamento);
            carregarEstacionamentosNaTabela();
        }
    }

    private void removerEstacionamento() {
        int linhaSelecionada = tabelaEstacionamentos.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, selecione um estacionamento para remover.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idEstacionamento = (int) tabelaModelo.getValueAt(linhaSelecionada, 0);
        boolean removido = estacionamentoController.removerEstacionamento(idEstacionamento);

        if (removido) {
            JOptionPane.showMessageDialog(this,
                    "Estacionamento removido com sucesso.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            carregarEstacionamentosNaTabela();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Erro ao remover o estacionamento.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListarEstacionamentosView tela = new ListarEstacionamentosView();
            tela.setVisible(true);
        });
    }
}
