package tickets;

import vagas.Vaga;
import vagas.VagaFactory;
import veiculos.Cliente;

public class Historico {
    private int contaMensal;
    private int contaTotal;



    public static int contaMensal(Vaga vagaId, int Valor){
        int Total = 0;
        Total += Valor;
        return Total;


    }

    public static int contaMensal(Cliente clienteId, int Valor){
        int Total = 0;
        Total += Valor;
        return Total;
        

    }

    public static int contaTotal(Cliente clienteId, int Valor){
        int Total = 0;
        Total += Valor;
        return Total;

    }





}
