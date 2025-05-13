package Dao;

import Modelos.Conexion;
import Modelos.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    private Connection conexion;

    public ProveedorDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (razon_social, direccion, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getRazonSocial());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET razon_social = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getRazonSocial());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.setInt(5, proveedor.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "UPDATE proveedores SET estado = 0 WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proveedor obtenerProveedorPorId(int id) {
        String sql = "SELECT * FROM proveedores WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Proveedor(
                            rs.getInt("id"),
                            rs.getString("razon_social"),
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

    public List<Proveedor> obtenerTodosLosProveedores() {
        List<Proveedor> listaProveedores = new ArrayList<>();
        String sql = "SELECT * FROM proveedores WHERE estado = 1 ";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("razon_social"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                listaProveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProveedores;
    }

    public Integer obtenerIdPorNombre(String nombre) {
        String sql = "SELECT id FROM proveedores WHERE razon_social = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna null si no se encuentra el nombre o ocurre un error
    }

    public String obtenerNombrePorId(int id) {
        String sql = "SELECT razon_social FROM proveedores WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("razon_social");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el ID o ocurre un error
    }

}
