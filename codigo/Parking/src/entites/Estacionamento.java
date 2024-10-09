package entites;

import java.util.ArrayList;

public class Estacionamento {
    private String endereco;
    private String telefone;
    private ArrayList<Vaga> vagas;
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
                Vaga novaVaga = new Vaga(idVaga, false); 
                vagas.add(novaVaga); 
            }
            fila++; 
        }
    }
    
    public int calcularQuantidadeDeVagas() {
        return vagas.size();
    }

    public void alterarTipoDeVaga(String idVaga, String tipo) {
        for (int i = 0; i < vagas.size(); i++) {
            Vaga vaga = vagas.get(i);
            if (vaga.getId().equals(idVaga)) {
                Vaga novaVaga;
                switch (tipo.toLowerCase()) {
                    case "pcd":
                        novaVaga = new Pcd(vaga.getId(), vaga.isOcupada());
                        break;
                    case "idoso":
                        novaVaga = new Idoso(vaga.getId(), vaga.isOcupada());
                        break;
                    case "vip":
                        novaVaga = new Vip(vaga.getId(), vaga.isOcupada());
                        break;
                    default:
                        System.out.println("Tipo inválido. Escolha entre 'PCD', 'Idoso' ou 'VIP'.");
                        return;
                }
                vagas.set(i, novaVaga);
                System.out.println("Vaga " + idVaga + " alterada para " + tipo.toUpperCase() + ".");
                return;
            }
        }
        System.out.println("Vaga não encontrada.");
    }
    
    public void adicionarVaga(Vaga vaga) {
        vagas.add(vaga);
    }

    public void removerVaga(Vaga vaga) {
        vagas.remove(vaga);
    }

    public ArrayList<Vaga> vagasDisponiveis() {
        ArrayList<Vaga> disponiveis = new ArrayList<>();
        for (Vaga vaga : vagas) {
            if (!vaga.isOcupada()) {
                disponiveis.add(vaga);
            }
        }
        return disponiveis;
    }


    public void listarVagasDisponiveis() {
        System.out.println("\n=== Vagas Disponíveis ===");
        boolean encontrouVaga = false;
        for (Vaga vaga : vagas) {
            if (!vaga.isOcupada()) {
                System.out.println("ID da Vaga: " + vaga.getId());
                encontrouVaga = true;
            }
        }
        if (!encontrouVaga) {
            System.out.println("Nenhuma vaga disponível.");
        }
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }
    
    public ArrayList<Vaga> getVagas() {
        return vagas;
    }
    
    public void alterarEndereco(String novoEndereco) {
        this.endereco = novoEndereco;
    }

    public void alterarTelefone(String novoTelefone) {
        this.telefone = novoTelefone;
    }
    
    
}
