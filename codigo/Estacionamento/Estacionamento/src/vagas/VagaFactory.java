package vagas;

public class VagaFactory {

    public static Vaga criarVaga(String tipo, String id) {
        switch (tipo.toLowerCase()) { 
            case "regular":
                return new VagaRegular(id);
            case "idoso":
                return new VagaIdoso(id);
            case "pcd":
                return new VagaPCD(id);
            case "vip":
                return new VagaVIP(id);
            default:
                return null; 
        }
    }
}
