package Dao;

import Modelos.Categoria;
import Modelos.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection conexion;

    public CategoriaDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (nombre, descripcion , estado ) VALUES (?, ?, 1)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNombre());
            stmt.setString(2, categoria.getDescripcion());
            stmt.setInt(3, categoria.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCategoria(int id) {
        String sql = "UPDATE categorias SET estado = 0 WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Categoria obtenerCategoriaPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
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

    public List<Categoria> obtenerTodasLasCategorias() {
        List<Categoria> listaCategorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias WHERE estado = 1";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                );
                listaCategorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaCategorias;
    }

    public Integer obtenerIdPorNombre(String nombre) {
        String sql = "SELECT id FROM categorias WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id"); // Retorna el ID si se encuentra el nombre
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Retorna null si no se encuentra la categoría
    }

    public String obtenerNombrePorId(int id) {
        String sql = "SELECT nombre FROM categorias WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre"); // Retorna el nombre si se encuentra el ID
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra la categoría
    }

}
