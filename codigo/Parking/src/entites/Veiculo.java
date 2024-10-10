package veiculos;

public class Veiculo {
    private String placa;
    private String idCliente;
    private String vagaId; 

    public Veiculo(String placa, String idCliente) {
        this.placa = placa;
        this.idCliente = idCliente;
        this.vagaId = null;
    }

    public String getPlaca() {
        return placa;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getVagaId() {
        return vagaId;
    }

    public void setVagaId(String vagaId) {
        this.vagaId = vagaId;
    }
}
