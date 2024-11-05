package model;

public class VagaFactoryModel {

    public static VagaModel criarVaga(int tipo, String id) {
        VagaModel vaga = null;

        if (tipo == 1) {
            vaga = new VagaRegularModel(id);
        } else if (tipo == 2) {
            vaga = new VagaPCDModel(id);
        } else if (tipo == 3) {
            vaga = new VagaIdosoModel(id);
        } else if (tipo == 4) {
            vaga = new VagaVIPModel(id);
        }

        return vaga;
    }
}
