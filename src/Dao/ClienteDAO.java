package Dao;

import Modelos.Cliente;
import Modelos.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Connection conexion;

    public ClienteDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (tipo_cliente, nombre, tipo_documento, num_documento, direccion, telefono, email , estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ? , 1)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "regular");
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getTipoDocumento());
            stmt.setString(4, cliente.getNumDocumento());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getTelefono());
            stmt.setString(7, cliente.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCliente(int idcliente, Cliente cliente) {
        String sql = "UPDATE clientes SET tipo_cliente = ?, nombre = ?, tipo_documento = ?, num_documento = ?, direccion = ?, "
                + "telefono = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "regular");
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getTipoDocumento());
            stmt.setString(4, cliente.getNumDocumento());
            stmt.setString(5, cliente.getDireccion());
            stmt.setString(6, cliente.getTelefono());
            stmt.setString(7, cliente.getEmail());
            stmt.setInt(8, idcliente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        String sql = "UPDATE clientes SET estado = 0 WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente obtenerClientePorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("tipo_cliente"),
                            rs.getString("nombre"),
                            rs.getString("tipo_documento"),
                            rs.getString("num_documento"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes where estado = 1";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("tipo_cliente"),
                        rs.getString("nombre"),
                        rs.getString("tipo_documento"),
                        rs.getString("num_documento"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaClientes;
    }

    public List<Cliente> obtenerClientesPorNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Añadir los signos de porcentaje para hacer la búsqueda parcial
            stmt.setString(1, "%" + nombre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente cliente = new Cliente(
                            rs.getInt("id"),
                            rs.getString("tipo_cliente"),
                            rs.getString("nombre"),
                            rs.getString("tipo_documento"),
                            rs.getString("num_documento"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    public Cliente obtenerClientePorNombre(String nombre) {
        String sql = "SELECT * FROM clientes WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Establecer el parámetro para la consulta exacta
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("tipo_cliente"),
                            rs.getString("nombre"),
                            rs.getString("tipo_documento"),
                            rs.getString("num_documento"),
                            rs.getString("direccion"),
                            rs.getString("telefono"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra coincidencias
    }

}
