package controller;

import dao.ClienteDAO;
import model.ClienteModel;
import view.AdicionarCliente;

public class ClienteController {
    private ClienteModel modelo;
    private AdicionarCliente view;
    private ClienteDAO dao;

    public ClienteController(ClienteModel modelo, AdicionarCliente view) {
        this.modelo = modelo;
        this.view = view;
        this.dao = new ClienteDAO();

        this.view.adicionarClienteListener(e -> adicionarCliente());

        this.view.adicionarAnonimoListener(e -> adicionarClienteAnonimo());
    }

    private void adicionarCliente() {
        String nome = view.getNome();
        String id = dao.gerarId(); // Gera um ID
        modelo = new ClienteModel(nome, id); //novo ClienteModel com o ID gerado
        dao.salvarCliente(modelo);
        view.mostrarMensagem("Cliente adicionado: " + nome + " (ID: " + id + ")");
    }

    private void adicionarClienteAnonimo() {
        String id = dao.gerarId(); //ID único para o cliente anônimo
        modelo = new ClienteModel("Anônimo", id);
        dao.salvarCliente(modelo);
        view.mostrarMensagem("Cliente anônimo adicionado com ID: " + id);
    }
}