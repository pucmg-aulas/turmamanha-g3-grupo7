package veiculos;

public class Veiculo {
    private Cliente cliente;
    private String placa;

    public Veiculo(String placa, Cliente cliente) {
        this.placa = placa;
        this.cliente = cliente;
    }

    public String getPlaca() {
        return placa;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
