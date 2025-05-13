package Dao;

import Modelos.Articulo;
import Modelos.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    private Connection conexion;

    public ArticuloDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarArticulo(Articulo articulo) {
        String sql = "INSERT INTO articulos (id_categoria, id_proveedor, codigo, nombre, precio_venta, stock, descripcion, imagen, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, articulo.getIdCategoria());
            stmt.setInt(2, articulo.getIdProveedor());
            stmt.setString(3, articulo.getCodigo());
            stmt.setString(4, articulo.getNombre());
            stmt.setFloat(5, articulo.getPrecioVenta());
            stmt.setInt(6, articulo.getStock());
            stmt.setString(7, articulo.getDescripcion());
            stmt.setString(8, articulo.getImagen());
            stmt.setBoolean(9, articulo.isEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarArticulo(int idArticulo, Articulo articulo) {
        String sql = "UPDATE articulos SET id_categoria = ?, id_proveedor = ?, codigo = ?, nombre = ?, precio_venta = ?, stock = ?, "
                + "descripcion = ?, imagen = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, articulo.getIdCategoria());
            stmt.setInt(2, articulo.getIdProveedor());
            stmt.setString(3, articulo.getCodigo());
            stmt.setString(4, articulo.getNombre());
            stmt.setFloat(5, articulo.getPrecioVenta());
            stmt.setInt(6, articulo.getStock());
            stmt.setString(7, articulo.getDescripcion());
            stmt.setString(8, articulo.getImagen());
            stmt.setBoolean(9, articulo.isEstado());
            stmt.setInt(10, idArticulo); // Usar el parámetro idArticulo directamente
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarArticulo(int id) {
        String sql = "UPDATE articulos SET estado = 0 WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Articulo obtenerArticuloPorId(int id) {
        String sql = "SELECT * FROM articulos WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Articulo(
                            rs.getInt("id"),
                            rs.getInt("id_categoria"),
                            rs.getInt("id_proveedor"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getFloat("precio_venta"),
                            rs.getInt("stock"),
                            rs.getString("descripcion"),
                            rs.getString("imagen"),
                            rs.getBoolean("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Articulo> obtenerTodosLosArticulos() {
        List<Articulo> listaArticulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos WHERE estado = 1 ";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Articulo articulo = new Articulo(
                        rs.getInt("id"),
                        rs.getInt("id_categoria"),
                        rs.getInt("id_proveedor"),
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getFloat("precio_venta"),
                        rs.getInt("stock"),
                        rs.getString("descripcion"),
                        rs.getString("imagen"),
                        rs.getBoolean("estado")
                );
                listaArticulos.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaArticulos;
    }

    public List<Articulo> obtenerArticulosPorNombre(String nombre) {
        List<Articulo> listaArticulos = new ArrayList<>();
        String sql = "SELECT * FROM articulos WHERE nombre LIKE ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, "%" + nombre + "%"); // Búsqueda parcial
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Articulo articulo = new Articulo(
                            rs.getInt("id"),
                            rs.getInt("id_categoria"),
                            rs.getInt("id_proveedor"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getFloat("precio_venta"),
                            rs.getInt("stock"),
                            rs.getString("descripcion"),
                            rs.getString("imagen"),
                            rs.getBoolean("estado")
                    );
                    listaArticulos.add(articulo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaArticulos;
    }

    public Articulo obtenerArticuloPorNombre(String nombre) {
        String sql = "SELECT * FROM articulos WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre); // Búsqueda exacta
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Articulo(
                            rs.getInt("id"),
                            rs.getInt("id_categoria"),
                            rs.getInt("id_proveedor"),
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getFloat("precio_venta"),
                            rs.getInt("stock"),
                            rs.getString("descripcion"),
                            rs.getString("imagen"),
                            rs.getBoolean("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean incrementarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idArticulo);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reducirStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            stmt.setInt(2, idArticulo);
            stmt.setInt(3, cantidad); // Asegurarse de no dejar stock negativo
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
