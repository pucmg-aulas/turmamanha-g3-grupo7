package entites;

public class Vip extends Vaga {
    private static final double DESCONTO_VIP = 15.0; // Desconto fixo de 15%

    public Vip(String id, boolean status) {
        super(id, status);
    }

    public double cobrarCliente() {
        return super.cobrarCliente(DESCONTO_VIP);
    }
}
