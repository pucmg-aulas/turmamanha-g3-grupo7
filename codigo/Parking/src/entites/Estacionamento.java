package entites;

import java.util.ArrayList;

public class Estacionamento {
    private String endereco;
    private String telefone;
    private ArrayList<String> vagas;
    private int numeroFilas;
    private int[] vagasPorFila;

    public Estacionamento(String endereco, String telefone, int numeroFilas, int[] vagasPorFila) {
        this.endereco = endereco;
        this.telefone = telefone;
        this.numeroFilas = numeroFilas;
        this.vagasPorFila = vagasPorFila;
        this.vagas = new ArrayList<>(); 

        gerarVagas();
    }

    private void gerarVagas() {
        char fila = 'A'; 
        for (int i = 0; i < numeroFilas; i++) {
            for (int j = 1; j <= vagasPorFila[i]; j++) {
                String idVaga = fila + String.format("%02d", j);
                vagas.add(idVaga);
            }
            fila++; 
        }
    }

    public void listarVagas() {
        for (String idVaga : vagas) {
            System.out.println("ID da Vaga: " + idVaga); // Exibe o ID da vaga
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
