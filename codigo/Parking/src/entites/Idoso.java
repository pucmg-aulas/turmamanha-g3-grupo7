package entites;

public class Idoso extends Vaga {
    private static final double DESCONTO_IDOSO = 15.0; // Desconto fixo de 15%

    public Idoso(String id, boolean status) {
        super(id, status);
    }

    public double cobrarCliente() {
        return super.cobrarCliente(DESCONTO_IDOSO);
    }
}
