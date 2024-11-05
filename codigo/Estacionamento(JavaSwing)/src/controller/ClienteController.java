<<<<<<< HEAD
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

        // Listener para adicionar cliente normal
        this.view.adicionarClienteListener(e -> adicionarCliente());

        // Listener para adicionar cliente anônimo
        this.view.adicionarAnonimoListener(e -> adicionarClienteAnonimo());
    }

    private void adicionarCliente() {
        String nome = view.getNome();
        String id = dao.gerarId(); // Gera um ID único para o cliente
        modelo = new ClienteModel(nome, id); // Cria um novo ClienteModel com o ID gerado

        dao.salvarCliente(modelo); // Salva o cliente no arquivo de texto
        view.mostrarMensagem("Cliente adicionado: " + nome + " (ID: " + id + ")");
    }

    private void adicionarClienteAnonimo() {
        String id = dao.gerarId(); // Gera um ID único para o cliente anônimo
        modelo = new ClienteModel("Anônimo", id); // Define o nome como "Anônimo"

        dao.salvarCliente(modelo); // Salva o cliente no arquivo de texto
        view.mostrarMensagem("Cliente anônimo adicionado com ID: " + id);
    }
=======
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

        // Listener para adicionar cliente normal
        this.view.adicionarClienteListener(e -> adicionarCliente());

        // Listener para adicionar cliente anônimo
        this.view.adicionarAnonimoListener(e -> adicionarClienteAnonimo());
    }

    private void adicionarCliente() {
        String nome = view.getNome();
        String id = dao.gerarId(); // Gera um ID único para o cliente
        modelo = new ClienteModel(nome, id); // Cria um novo ClienteModel com o ID gerado

        dao.salvarCliente(modelo); // Salva o cliente no arquivo de texto
        view.mostrarMensagem("Cliente adicionado: " + nome + " (ID: " + id + ")");
    }

    private void adicionarClienteAnonimo() {
        String id = dao.gerarId(); // Gera um ID único para o cliente anônimo
        modelo = new ClienteModel("Anônimo", id); // Define o nome como "Anônimo"

        dao.salvarCliente(modelo); // Salva o cliente no arquivo de texto
        view.mostrarMensagem("Cliente anônimo adicionado com ID: " + id);
    }
>>>>>>> d9070482504422a0b550f2cc893f54b1a437c91e
}