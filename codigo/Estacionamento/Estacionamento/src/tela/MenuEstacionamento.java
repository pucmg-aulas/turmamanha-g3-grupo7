package tela;

import estacionamento.*;
import veiculos.Cliente;
import veiculos.Veiculo;
import vagas.Vaga;
import vagas.VagaFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEstacionamento {
    private static Scanner scanner = new Scanner(System.in);

    // Inicializando o estacionamento com nome, endereço e telefone no construtor
    private static Estacionamento estacionamento = new Estacionamento(
            "Estacionamento Central", "Rua Principal, 123", "1234-5678"
    );

    // Lista para armazenar os clientes do estacionamento
    private static List<Cliente> clientes = new ArrayList<>();

    public static void displayMenu() {
        int opcao;
        do {
            System.out.println("\n===== Menu de Estacionamento =====");
            System.out.println("1. Adicionar Vaga");
            System.out.println("2. Estacionar Veículo");
            System.out.println("3. Liberar Vaga");
            System.out.println("4. Alterar Endereço");
            System.out.println("5. Alterar Telefone");
            System.out.println("6. Salvar Dados");
            System.out.println("7. Carregar Dados");
            System.out.println("8. Gerenciar Clientes");  
            System.out.println("9. Sair");
            System.out.print("Selecione uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    adicionarVaga();
                    break;
                case 2:
                    estacionarVeiculo();
                    break;
                case 3:
                    liberarVaga();
                    break;
                case 4:
                    alterarEndereco();
                    break;
                case 5:
                    alterarTelefone();
                    break;
                case 6:
                    salvarDados();
                    break;
                case 7:
                    carregarDados();
                    break;
                case 8:
                    displayMenuCliente();  
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 9);
        scanner.close();
    }

    private static void alterarEndereco() {
        System.out.println("Digite o novo endereço do estacionamento:");
        String novoEndereco = scanner.nextLine();
        estacionamento.alterarEndereco(novoEndereco);
        System.out.println("Endereço alterado com sucesso.");
    }

    private static void alterarTelefone() {
        System.out.println("Digite o novo telefone do estacionamento:");
        String novoTelefone = scanner.nextLine();
        estacionamento.alterarTelefone(novoTelefone);
        System.out.println("Telefone alterado com sucesso.");
    }

    private static void adicionarVaga() {
        System.out.println("Digite o tipo de vaga (Regular, Idoso, PCD, VIP):");
        String tipo = scanner.nextLine();
        System.out.println("Digite o ID da vaga:");
        String id = scanner.nextLine();
        Vaga vaga = VagaFactory.criarVaga(tipo, id); // Criação da vaga usando a VagaFactory
        if (vaga != null) {
            estacionamento.adicionarVaga(vaga);
            System.out.println("Vaga adicionada com sucesso.");
        } else {
            System.out.println("Tipo de vaga inválido.");
        }
    }

    private static void estacionarVeiculo() {
        System.out.println("Digite a placa do veículo:");
        String placa = scanner.nextLine();
        System.out.println("Digite o ID da vaga onde deseja estacionar:");
        String idVaga = scanner.nextLine();
        Veiculo veiculo = new Veiculo(placa, "Cliente ID");  // Criação de um veículo temporário com a placa fornecida
        if (estacionamento.estacionarVeiculo(veiculo, idVaga)) {
            System.out.println("Veículo estacionado com sucesso.");
        } else {
            System.out.println("Falha ao estacionar o veículo.");
        }
    }
    
    private static void liberarVaga() {
        System.out.println("Digite o ID da vaga que deseja liberar:");
        String idVaga = scanner.nextLine();
        if (estacionamento.liberarVaga(idVaga)) {
            System.out.println("Vaga liberada com sucesso.");
        } else {
            System.out.println("Falha ao liberar a vaga.");
        }
    }

    private static void salvarDados() {
        DadosEstacionamento.salvarVagas(estacionamento.getVagas(), "vagas.txt");
        System.out.println("Dados salvos com sucesso.");
    }

    private static void carregarDados() {
        DadosEstacionamento.carregarVagas(estacionamento, "vagas.txt");
        System.out.println("Dados carregados com sucesso.");
    }

    // Menu de Clientes integrado
    private static void displayMenuCliente() {
        int opcao;
        do {
            System.out.println("\n===== Menu de Cliente =====");
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Adicionar Veículo a Cliente");
            System.out.println("3. Exibir Veículos de Cliente");
            System.out.println("4. Remover Veículo de Cliente");  // Nova opção para remover veículo
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Selecione uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcao) {
                case 1:
                    adicionarCliente();
                    break;
                case 2:
                    adicionarVeiculoCliente();
                    break;
                case 3:
                    exibirVeiculosCliente();
                    break;
                case 4:
                    apagarVeiculoCliente();  // Chama o método para remover veículo
                    break;
                case 5:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 5);
    }

    private static void adicionarCliente() {
        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        System.out.println("Digite o ID do cliente:");
        String id = scanner.nextLine();
        Cliente cliente = new Cliente(nome, id);
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso.");
    }

    private static void adicionarVeiculoCliente() {
        System.out.println("Digite o ID do cliente:");
        String idCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorId(idCliente);   // Busca do cliente pelo ID

        if (cliente != null) {
            System.out.println("Digite a placa do veículo:");
            String placa = scanner.nextLine();
            Veiculo veiculo = new Veiculo(placa, idCliente);   // Criação do veículo com a placa fornecida
            cliente.adicionarVeiculo(veiculo);
            System.out.println("Veículo adicionado ao cliente.");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void exibirVeiculosCliente() {
        System.out.println("Digite o ID do cliente:");
        String idCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorId(idCliente);

        if (cliente != null) {
            List<Veiculo> veiculos = cliente.getVeiculos();
            if (veiculos.isEmpty()) {
                System.out.println("Este cliente não possui veículos.");
            } else {
                System.out.println("Veículos do cliente " + cliente.getNome() + ":");
                for (Veiculo veiculo : veiculos) {
                    System.out.println("Placa: " + veiculo.getPlaca());
                }
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static void apagarVeiculoCliente() {
        System.out.println("Digite o ID do cliente:");
        String idCliente = scanner.nextLine();
        Cliente cliente = buscarClientePorId(idCliente);

        if (cliente != null) {
            System.out.println("Digite a placa do veículo que deseja remover:");
            String placa = scanner.nextLine();
            if (cliente.apagarVeiculo(placa)) {
                System.out.println("Veículo removido com sucesso.");
            } else {
                System.out.println("Veículo com a placa fornecida não encontrado.");
            }
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private static Cliente buscarClientePorId(String id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        displayMenu();
    }
}
    