package main;

import entites.Estacionamento;
import entites.Vaga;

import java.util.Scanner;

public class Main {
    private static Estacionamento estacionamento;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 3) {
            System.out.println("\n=== Menu Principal ===");
            System.out.println("1. Gerenciar Estacionamento");
            System.out.println("2. Gerenciar Vagas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    menuEstacionamento(scanner);
                    break;
                case 2:
                    if (estacionamento != null) {
                        menuVagas(scanner);
                    } else {
                        System.out.println("Estacionamento não criado. Por favor, crie o estacionamento primeiro.");
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void menuEstacionamento(Scanner scanner) {
        int opcao = 0;

        while (opcao != 8) { 
            System.out.println("\n=== Menu Gerenciar Estacionamento ===");
            System.out.println("1. Cadastrar Estacionamento");
            System.out.println("2. Listar Vagas Disponíveis");
            System.out.println("3. Adicionar Vaga");
            System.out.println("4. Remover Vaga");
            System.out.println("5. Alterar Endereço");
            System.out.println("6. Alterar Telefone");
            System.out.println("7. Mostrar Endereço e Telefone"); 
            System.out.println("8. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o endereço do estacionamento: ");
                    scanner.nextLine(); 
                    String endereco = scanner.nextLine();
                    System.out.print("Digite o telefone do estacionamento: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Digite o número de filas: ");
                    int numeroFilas = scanner.nextInt();
                    int[] vagasPorFila = new int[numeroFilas];
                    
                    for (int i = 0; i < numeroFilas; i++) {
                        System.out.print("Digite o número de vagas para a fila " + (char) ('A' + i) + ": ");
                        vagasPorFila[i] = scanner.nextInt();
                    }

                    estacionamento = new Estacionamento(endereco, telefone, numeroFilas, vagasPorFila);
                    System.out.println("Estacionamento cadastrado e vagas geradas com sucesso!");
                    break;
                case 2:
                    if (estacionamento != null) {
                        estacionamento.listarVagasDisponiveis();
                    } else {
                        System.out.println("Estacionamento não cadastrado.");
                    }
                    break;
                case 3:
                    if (estacionamento != null) {
                        System.out.print("Digite o ID da nova vaga: ");
                        scanner.nextLine(); 
                        String idVaga = scanner.nextLine();
                        Vaga novaVaga = new Vaga(idVaga, false);
                        estacionamento.adicionarVaga(novaVaga);
                        System.out.println("Vaga adicionada com sucesso!");
                    } else {
                        System.out.println("Estacionamento não cadastrado.");
                    }
                    break;
                case 4:
                    if (estacionamento != null) {
                        System.out.print("Digite o ID da vaga a ser removida: ");
                        scanner.nextLine();
                        String idRemover = scanner.nextLine();
                        Vaga vagaRemover = null;
                        for (Vaga vaga : estacionamento.getVagas()) {
                            if (vaga.getId().equals(idRemover)) {
                                vagaRemover = vaga;
                                break;
                            }
                        }
                        if (vagaRemover != null) {
                            estacionamento.removerVaga(vagaRemover);
                            System.out.println("Vaga removida com sucesso!");
                        } else {
                            System.out.println("Vaga não encontrada.");
                        }
                    } else {
                        System.out.println("Estacionamento não cadastrado.");
                    }
                    break;
                case 5:
                    if (estacionamento != null) {
                        System.out.print("Digite o novo endereço: ");
                        scanner.nextLine(); 
                        String novoEndereco = scanner.nextLine();
                        estacionamento.alterarEndereco(novoEndereco);
                    } else {
                        System.out.println("Estacionamento não criado. Por favor, crie o estacionamento primeiro.");
                    }
                    break;
                case 6:
                    if (estacionamento != null) {
                        System.out.print("Digite o novo telefone: ");
                        scanner.nextLine(); 
                        String novoTelefone = scanner.nextLine();
                        estacionamento.alterarTelefone(novoTelefone);
                    } else {
                        System.out.println("Estacionamento não criado. Por favor, crie o estacionamento primeiro.");
                    }
                    break;
                case 7: 
                    if (estacionamento != null) {
                        System.out.println("Endereço: " + estacionamento.getEndereco());
                        System.out.println("Telefone: " + estacionamento.getTelefone());
                    } else {
                        System.out.println("Estacionamento não cadastrado.");
                    }
                    break;
                case 8:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }


    private static void menuVagas(Scanner scanner) {
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n=== Menu Gerenciar Vagas ===");
            System.out.println("1. Ocupar Vaga");
            System.out.println("2. Cobrar Cliente");
            System.out.println("3. Adicionar Atributo a Vaga (PCD, Idoso ou VIP)");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da vaga a ser ocupada: ");
                    scanner.nextLine();
                    String idOcupar = scanner.nextLine();
                    Vaga vagaOcupar = encontrarVaga(idOcupar);
                    if (vagaOcupar != null) {
                        vagaOcupar.ocupar();
                    } else {
                        System.out.println("Vaga não encontrada.");
                    }
                    break;
                case 2:
                    System.out.print("Digite o ID da vaga para cobrar: ");
                    scanner.nextLine();
                    String idCobrar = scanner.nextLine();
                    Vaga vagaCobrar = encontrarVaga(idCobrar);
                    if (vagaCobrar != null) {
                        double valor = vagaCobrar.cobrarCliente(0); // Sem desconto padrão
                        System.out.println("Valor a ser cobrado: R$ " + valor);
                    } else {
                        System.out.println("Vaga não encontrada.");
                    }
                    break;
                case 3:
                    System.out.print("Digite o ID da vaga para adicionar o atributo: ");
                    scanner.nextLine();
                    String idAlterar = scanner.nextLine();
                    System.out.print("Escolha o tipo (PCD, Idoso, VIP): ");
                    String tipo = scanner.nextLine();
                    estacionamento.alterarTipoDeVaga(idAlterar, tipo);
                    break;
                case 4:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
    private static Vaga encontrarVaga(String idVaga) {
        if (estacionamento != null) {
            // Busca a vaga na lista completa de vagas, independente do status
            for (Vaga vaga : estacionamento.getVagas()) {
                if (vaga.getId().equals(idVaga)) {
                    return vaga;
                }
            }
        }
        return null;
    }

}
