package main;

import entites.Estacionamento;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número de fileiras no estacionamento: ");
        int numeroFilas = scanner.nextInt();

        int[] vagasPorFila = new int[numeroFilas];

        for (int i = 0; i < numeroFilas; i++) {
            System.out.print("Digite o número de vagas para a fileira " + (char) ('A' + i) + ": ");
            vagasPorFila[i] = scanner.nextInt();
        }

        Estacionamento estacionamento = new Estacionamento("Rua C, 789", "(11) 77777-7777", numeroFilas, vagasPorFila);
        System.out.println("\nVagas no estacionamento:");
        estacionamento.listarVagas();

        
        estacionamento.listarVagas();

        scanner.close();
    }
}
