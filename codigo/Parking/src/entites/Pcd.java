package entites;

public class Pcd extends Vaga {
    private static final double DESCONTO_PCD = 15.0; // Desconto fixo de 15%

    public Pcd(String id, boolean status) {
        super(id, status);
    }

    public double cobrarCliente() {
        return super.cobrarCliente(DESCONTO_PCD);
    }
}
