package dao;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO {
    private static final String DIRECTORY = "data";
    private static final String FILE_NAME_PREFIX = DIRECTORY + "/vagas_";

    public VagaDAO() {
        File directory = new File(DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    public void salvarVaga(String estacionamentoId, VagaModel vaga) {
        String fileName = FILE_NAME_PREFIX + estacionamentoId + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(vaga.getId() + ";" + vaga.getTipo() + ";" + vaga.isOcupada());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar vaga: " + e.getMessage());
        }
    }

    public List<VagaModel> carregarVagas(String estacionamentoId) {
        List<VagaModel> vagas = new ArrayList<>();
        String fileName = DIRECTORY + "/vagas_" + estacionamentoId + ".txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");

                // Verificação para evitar erro de ArrayIndexOutOfBoundsException
                if (data.length >= 2) {
                    String idVaga = data[0];
                    String tipoVaga = data[1];
                    boolean ocupada = data.length == 3 && Boolean.parseBoolean(data[2]);

                    VagaModel vaga;
                    switch (tipoVaga) {
                        case "VIP":
                            vaga = new VagaVIPModel(idVaga);
                            break;
                        case "PCD":
                            vaga = new VagaPCDModel(idVaga);
                            break;
                        case "Idoso":
                            vaga = new VagaIdosoModel(idVaga);
                            break;
                        default:
                            vaga = new VagaPadraoModel(idVaga);
                            break;
                    }

                    if (ocupada) {
                        vaga.ocuparVaga(null);  // Marcar a vaga como ocupada sem veículo
                    }
                    vagas.add(vaga);
                } else {
                    System.err.println("Linha com formato incorreto: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar vagas: " + e.getMessage());
        }

        return vagas;
    }

    public boolean salvarVagas(String estacionamentoId, List<VagaModel> vagas) {
        String fileName = FILE_NAME_PREFIX + estacionamentoId + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (VagaModel vaga : vagas) {
                writer.write(vaga.getId() + ";" + vaga.getTipo() + ";" + vaga.isOcupada());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao salvar vagas: " + e.getMessage());
            return false;
        }
    }
}
