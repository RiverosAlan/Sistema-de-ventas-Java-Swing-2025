package Dao;

import Modelos.Conexion;

import Modelos.Roles;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolesDAO {

    private final Connection conexion;

    public RolesDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarRol(Roles rol) {
        String sql = "INSERT INTO roles (nombre, descripcion ) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Roles rol) {
        String sql = "UPDATE roles SET nombre = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, rol.getNombre());
            stmt.setString(2, rol.getDescripcion());
            stmt.setInt(3, rol.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM roles WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Roles obtenerPorId(int id) {
        String sql = "SELECT * FROM roles WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Roles(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Roles> obtenerTodos() {
        List<Roles> listaRoles = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Roles rol = new Roles(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                listaRoles.add(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRoles;
    }

    // Método para obtener un rol por su nombre
    public Roles obtenerRolPorNombre(String nombre) {
        String sql = "SELECT * FROM roles WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Roles rol = new Roles();
                    rol.setId(resultSet.getInt("id"));
                    rol.setNombre(resultSet.getString("nombre"));
                    rol.setDescripcion(resultSet.getString("descripcion"));
                    return rol;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para obtener el id de un rol por su nombre
    public int obtenerIdRolPorNombre(String nombre) {
        String sql = "SELECT id FROM roles WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); // Devolver el id del rol
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retorna -1 si no se encuentra el rol
    }

    // Método para obtener el nombre de un rol por su id
    public String obtenerNombreRolPorId(int id) {
        String sql = "SELECT nombre FROM roles WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("nombre"); // Retorna el nombre del rol
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el rol
    }

}
