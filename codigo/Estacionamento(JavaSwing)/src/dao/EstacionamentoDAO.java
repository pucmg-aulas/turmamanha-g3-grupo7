package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.ClienteModel;
import model.EstacionamentoModel;
import model.VagaFactoryModel;
import model.VagaModel;
import model.VeiculoModel;

public class EstacionamentoDAO {
    private static final String DIRETORIO_ESTACIONAMENTOS = "estacionamentos/";

    public void salvarVagas(List<VagaModel> vagas, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (VagaModel vaga : vagas) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
        } catch (IOException e) {
        }
    }

    public void salvarEstacionamento(EstacionamentoModel estacionamento) {
        String nomeArquivo = DIRETORIO_ESTACIONAMENTOS + estacionamento.getNome().replaceAll(" ", "_") + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            writer.write("Nome: " + estacionamento.getNome() + "\n");
            writer.write("Endereço: " + estacionamento.getEndereco() + "\n");
            writer.write("Telefone: " + estacionamento.getTelefone() + "\n");
            salvarVagas(estacionamento.getVagas(), "vagas_" + estacionamento.getNome().replaceAll(" ", "_") + ".txt");
        } catch (IOException e) {
        }
    }

    public boolean removerEstacionamento(String nome) {
        String nomeArquivo = DIRETORIO_ESTACIONAMENTOS + nome.replaceAll(" ", "_") + ".txt";
        File arquivo = new File(nomeArquivo);
        return arquivo.delete();
    }

    public EstacionamentoModel buscarPorNome(String nome) {
        String nomeArquivo = DIRETORIO_ESTACIONAMENTOS + nome.replaceAll(" ", "_") + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            String endereco = null;
            String telefone = null;
            List<VagaModel> vagas = new ArrayList<>();

            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Nome: ")) {
                    continue;
                } else if (linha.startsWith("Endereço: ")) {
                    endereco = linha.substring("Endereço: ".length());
                } else if (linha.startsWith("Telefone: ")) {
                    telefone = linha.substring("Telefone: ".length());
                } else {
                    String[] partes = linha.split(",");
                    String tipo = partes[0];
                    String id = partes[1];
                    boolean ocupada = Boolean.parseBoolean(partes[2]);
                    VagaModel vaga = VagaFactoryModel.criarVaga(Integer.parseInt(tipo), id);
                    if (ocupada) {
                        // Agora podemos criar VeiculoModel com a placa e o cliente associado
                        ClienteModel cliente = new ClienteModel("Cliente nome", "ClienteID"); // Substitua pelos dados reais do cliente
                        vaga.ocuparVaga(new VeiculoModel("XXX-XXXX", cliente));
                    }
                    vagas.add(vaga);
                }
            }

            if (endereco != null && telefone != null) {
                return new EstacionamentoModel(nome, endereco, telefone);
            }
        } catch (IOException e) {
        }
        return null;
    }

    public List<EstacionamentoModel> listarTodos() {
        List<EstacionamentoModel> estacionamentos = new ArrayList<>();
        File dir = new File(DIRETORIO_ESTACIONAMENTOS);
        if (dir.exists() && dir.isDirectory()) {
            File[] arquivos = dir.listFiles((d, name) -> name.endsWith(".txt"));
            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    String nome = arquivo.getName().replace(".txt", "").replaceAll("_", " ");
                    EstacionamentoModel estacionamento = buscarPorNome(nome);
                    if (estacionamento != null) {
                        estacionamentos.add(estacionamento);
                    }
                }
            }
        }
        return estacionamentos;
    }
}
