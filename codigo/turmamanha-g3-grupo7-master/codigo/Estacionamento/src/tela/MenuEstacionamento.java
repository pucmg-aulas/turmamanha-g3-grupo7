package tela;

import empresa.Xulambs;
import estacionamento.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import vagas.Vaga;
import vagas.VagaFactory;
import veiculos.Cliente;
import veiculos.Veiculo;

public class MenuEstacionamento {
    
    private static Scanner scanner = new Scanner(System.in);
    
    // Inicializando a rede de estacionamentos
    private static Xulambs redeXulambs = new Xulambs();

    // Lista para armazenar os clientes do estacionamento
    private static List<Cliente> clientes = new ArrayList<>();

    // Menu principal da rede de estacionamentos
    public static void displayMenuEmpresa() {
        int opcao;
        do {
            System.out.println("\n===== Menu da Rede de Estacionamentos =====");
            System.out.println("1. Adicionar Estacionamento");
            System.out.println("2. Remover Estacionamento");
            System.out.println("3. Listar Estacionamentos");
            System.out.println("4. Selecionar Estacionamento para Gerenciar");
            System.out.println("5. Sair");
            System.out.print("Selecione uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha após a leitura da opção

            switch (opcao) {
                case 1 -> adicionarEstacionamento();
                case 2 -> removerEstacionamento();
                case 3 -> listarEstacionamentos();
                case 4 -> selecionarEstacionamento();
                case 5 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);
        scanner.close();
    }

    private static void adicionarEstacionamento() {
        System.out.println("Digite o nome do estacionamento:");
        String nome = scanner.nextLine();
        System.out.println("Digite o endereço do estacionamento:");
        String endereco = scanner.nextLine();
        System.out.println("Digite o telefone do estacionamento:");
        String telefone = scanner.nextLine();

        Estacionamento novoEstacionamento = new Estacionamento(nome, endereco, telefone);
        redeXulambs.adicionarEstacionamento(novoEstacionamento);
        System.out.println("Estacionamento adicionado com sucesso.");
    }

    private static void removerEstacionamento() {
        System.out.println("Digite o nome do estacionamento a remover:");
        String nome = scanner.nextLine();
        if (redeXulambs.removerEstacionamento(nome)) {
            System.out.println("Estacionamento removido com sucesso.");
        } else {
            System.out.println("Estacionamento não encontrado.");
        }
    }

    private static void listarEstacionamentos() {
        System.out.println("Lista de Estacionamentos:");
        for (Estacionamento estacionamento : redeXulambs.getEstacionamentos()) {
            System.out.println("- " + estacionamento.getNome());
        }
    }

    private static void selecionarEstacionamento() {
        System.out.println("Digite o nome do estacionamento que deseja gerenciar:");
        String nome = scanner.nextLine();
        Estacionamento estacionamento = redeXulambs.buscarEstacionamento(nome);

        if (estacionamento != null) {
            // Chama o menu para gerenciar o estacionamento selecionado
            displayMenu(estacionamento);
        } else {
            System.out.println("Estacionamento não encontrado.");
        }
    }

    // Menu de gerenciamento de um estacionamento específico
    // Menu de gerenciamento de um estacionamento específico
public static void displayMenu(Estacionamento estacionamento) {
    int opcao;
    do {
        System.out.println("\n===== Menu de Gerenciamento do Estacionamento " + estacionamento.getNome() + " =====");
        System.out.println("1. Adicionar Vaga Manualmente");
        System.out.println("2. Gerar Múltiplas Vagas Automaticamente");
        System.out.println("3. Estacionar Veículo");
        System.out.println("4. Liberar Vaga");
        System.out.println("5. Listar Vagas");
        System.out.println("6. Alterar Endereço");
        System.out.println("7. Alterar Telefone");
        System.out.println("8. Salvar Dados");
        System.out.println("9. Carregar Dados");
        System.out.println("10. Gerenciar Clientes");
        System.out.println("11. Voltar ao Menu Principal");
        System.out.print("Selecione uma opção: ");
        opcao = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcao) {
            case 1 -> adicionarVaga(estacionamento); // Adiciona vaga manualmente
            case 2 -> gerarVagasAutomaticamente(estacionamento); // Gera múltiplas vagas
            case 3 -> estacionarVeiculo(estacionamento);
            case 4 -> liberarVaga(estacionamento);
            case 5 -> listarVagas(estacionamento); // Nova opção para listar vagas
            case 6 -> alterarEndereco(estacionamento);
            case 7 -> alterarTelefone(estacionamento);
            case 8 -> salvarDados(estacionamento);
            case 9 -> carregarDados(estacionamento);
            case 10 -> displayMenuCliente();
            case 11 -> System.out.println("Voltando ao menu principal...");
            default -> System.out.println("Opção inválida. Tente novamente.");
        }
    } while (opcao != 11);
}

// Método para gerar múltiplas vagas automaticamente
private static void gerarVagasAutomaticamente(Estacionamento estacionamento) {
    System.out.println("Digite o número de colunas:");
    int numColunas = scanner.nextInt();

    System.out.println("Digite o número de vagas por coluna:");
    int vagasPorColuna = scanner.nextInt();

    System.out.println("Selecione o tipo de vaga:");
    System.out.println("1. Regular");
    System.out.println("2. PCD");
    System.out.println("3. Idoso");
    System.out.println("4. VIP");
    int tipo = scanner.nextInt();
    scanner.nextLine(); // Limpar o buffer de entrada

    // Chama o método de gerar vagas do estacionamento
    estacionamento.gerarVagas(numColunas, vagasPorColuna, tipo);
    System.out.println("Vagas geradas com sucesso.");
}

// Método para listar todas as vagas do estacionamento
private static void listarVagas(Estacionamento estacionamento) {
    List<Vaga> vagas = estacionamento.getVagas();

    if (vagas.isEmpty()) {
        System.out.println("Nenhuma vaga disponível no momento.");
    } else {
        System.out.println("\n===== Lista de Vagas do Estacionamento =====");
        for (Vaga vaga : vagas) {
            String status = vaga.isOcupada() ? "Ocupada por " + vaga.getVeiculo().getPlaca() : "Livre";
            System.out.println("Vaga: " + vaga.getId() + " | Status: " + status);
        }
    }
}

    private static void alterarEndereco(Estacionamento estacionamento) {
        System.out.println("Digite o novo endereço do estacionamento:");
        String novoEndereco = scanner.nextLine();
        estacionamento.alterarEndereco(novoEndereco);
        System.out.println("Endereço alterado com sucesso.");
    }

    private static void alterarTelefone(Estacionamento estacionamento) {
        System.out.println("Digite o novo telefone do estacionamento:");
        String novoTelefone = scanner.nextLine();
        estacionamento.alterarTelefone(novoTelefone);
        System.out.println("Telefone alterado com sucesso.");
    }

    private static void adicionarVaga(Estacionamento estacionamento) {
        System.out.println("Selecione o tipo de vaga:");
        System.out.println("1. Regular");
        System.out.println("2. PCD");
        System.out.println("3. Idoso");
        System.out.println("4. VIP");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        System.out.println("Digite o ID da vaga:");
        String id = scanner.nextLine();

        Vaga vaga = VagaFactory.criarVaga(tipo, id); // Criação da vaga usando um número para o tipo
        if (vaga != null) {
            estacionamento.adicionarVaga(vaga);
            System.out.println("Vaga adicionada com sucesso.");
        } else {
            System.out.println("Tipo de vaga inválido.");
        }
    }

    private static void estacionarVeiculo(Estacionamento estacionamento) {
    System.out.println("Digite o ID do cliente:");
    String idCliente = scanner.nextLine();
    // Busca o cliente pelo ID
    Cliente cliente = buscarClientePorId(idCliente);
    if (cliente == null) {
        System.out.println("Cliente não encontrado. Cadastre o cliente primeiro.");
        return; // Sai do método se o cliente não foi encontrado
    }
        System.out.println("Digite a placa do veículo:");
        String placa = scanner.nextLine();  
        System.out.println("Digite o ID da vaga onde deseja estacionar:");
        String idVaga = scanner.nextLine();
        Veiculo veiculo = new Veiculo(placa, cliente);  // Criação de um veículo temporário com a placa fornecida
        if (estacionamento.estacionarVeiculo(veiculo, idVaga)) {
            System.out.println("Veículo estacionado com sucesso.");
        } else {
            System.out.println("Falha ao estacionar o veículo.");
        }
    }

    private static void liberarVaga(Estacionamento estacionamento) {
        System.out.println("Digite o ID da vaga que deseja liberar:");
        String idVaga = scanner.nextLine();
        if (estacionamento.liberarVaga(idVaga)) {
            System.out.println("Vaga liberada com sucesso.");
        } else {
            System.out.println("Falha ao liberar a vaga.");
        }
    }

    private static void salvarDados(Estacionamento estacionamento) {
        DadosEstacionamento.salvarVagas(estacionamento.getVagas(), "vagas.txt");
        System.out.println("Dados salvos com sucesso.");
    }

    private static void carregarDados(Estacionamento estacionamento) {
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
            System.out.println("4. Remover Veículo de Cliente");
            System.out.println("5. Voltar ao Menu de Gerenciamento do Estacionamento");
            System.out.print("Selecione uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 
            switch (opcao) {
                case 1 -> adicionarCliente();
                case 2 -> adicionarVeiculoCliente();
                case 3 -> exibirVeiculosCliente();
                case 4 -> apagarVeiculoCliente();
                case 5 -> System.out.println("Voltando ao Menu de Gerenciamento do Estacionamento...");
                default -> System.out.println("Opção inválida. Tente novamente.");
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
            Veiculo veiculo = new Veiculo(placa, cliente);   // Criação do veículo com a placa fornecida
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
        displayMenuEmpresa();
    }
}
