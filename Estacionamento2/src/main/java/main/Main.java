package main;

import controller.ListarEstacionamentoController;
import view.ListarEstacionamentosView;

public class Main {
    public static void main(String[] args) {
        ListarEstacionamentoController controller = new ListarEstacionamentoController();
        ListarEstacionamentosView listarEstacionamentosView = new ListarEstacionamentosView(controller);

        listarEstacionamentosView.setVisible(true);
    }
}
