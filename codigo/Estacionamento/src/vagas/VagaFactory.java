package vagas;

public class VagaFactory {

    public static Vaga criarVaga(int tipo, String id) {
        return switch (tipo) {
            case 1 -> new VagaRegular(id);
            case 2 -> new VagaPCD(id);
            case 3 -> new VagaIdoso(id);
            case 4 -> new VagaVIP(id);
            default -> null;
        };
    }
}
