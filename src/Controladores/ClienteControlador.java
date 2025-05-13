package Controladores;

import Dao.ClienteDAO;
import Modelos.Cliente;
import java.util.List;

public class ClienteControlador {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public boolean agregarCliente(Cliente cliente) {
        return clienteDAO.insertarCliente(cliente);
    }

    public boolean actualizarCliente(int idCliente, Cliente cliente) {
        return clienteDAO.actualizarCliente(idCliente, cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteDAO.eliminarCliente(id);
    }

    public Cliente obtenerClientePorId(int id) {
        return clienteDAO.obtenerClientePorId(id);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.obtenerTodosLosClientes();
    }

    public List<Cliente> obtenerClientesPorNombre(String nombre) {
        // Verificar si el nombre es nulo o vacío
        if (nombre == null || nombre.trim().isEmpty()) {
            // Si el nombre está vacío, devolver todos los clientes
            return clienteDAO.obtenerTodosLosClientes();
        }

        // De lo contrario, buscar clientes por el nombre proporcionado
        return clienteDAO.obtenerClientesPorNombre(nombre);
    }

    public Cliente buscarClientePorNombre(String nombre) {
        // Llamar al método del DAO para obtener el cliente por nombre exacto
        return clienteDAO.obtenerClientePorNombre(nombre);
    }

}
