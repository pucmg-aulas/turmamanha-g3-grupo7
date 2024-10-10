package estacionamento;

import java.io.*;
import java.util.List;
import vagas.*;
import veiculos.Veiculo;

public class DadosEstacionamento {
    
    // Método para salvar a lista de vagas em um arquivo de texto
    public static void salvarVagas(List<Vaga> vagas, String nomeArquivo) {
            // Tenta criar um BufferedWriter para escrever no arquivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Para cada vaga na lista, escreve uma linha no formato: tipo, id, ocupada
            for (Vaga vaga : vagas) {
                writer.write(vaga.getTipo() + "," + vaga.getId() + "," + vaga.isOcupada() + "\n");
            }
            System.out.println("Vagas salvas com sucesso no arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar vagas: " + e.getMessage());
        }
    }
   
    // Método para carregar a lista de vagas a partir de um arquivo de texto
    public static void carregarVagas(Estacionamento estacionamento, String nomeArquivo) {
        // Primeiro, limpa as vagas existentes no estacionamento
        estacionamento.limparVagas(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            // Lê cada linha do arquivo até o final 
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                String tipo = partes[0];
                String id = partes[1];
                boolean ocupada = Boolean.parseBoolean(partes[2]);
                
                // Cria uma nova vaga usando a fábrica de vagas (VagaFactory)
                Vaga vaga = VagaFactory.criarVaga(tipo, id);
                if (ocupada) {
                    vaga.ocuparVaga(new Veiculo("XXX-XXXX", "ClienteID")); // Se a vaga estava ocupada no arquivo, recria a ocupação com um veículo fictício

                }
                // Adiciona a vaga (ocupada ou livre) ao estacionamento
                estacionamento.adicionarVaga(vaga);
            }
            System.out.println("Vagas carregadas com sucesso do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao carregar vagas: " + e.getMessage());
        }
    }
}
