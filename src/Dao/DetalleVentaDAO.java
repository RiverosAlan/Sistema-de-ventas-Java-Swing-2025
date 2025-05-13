package Dao;

import Modelos.Conexion;
import Modelos.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAO {

    private Connection conexion;

    public DetalleVentaDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarDetalleVenta(DetalleVenta detalle) {
        String sql = "INSERT INTO detalle_venta (id_venta, id_articulo, cantidad, precio, descuento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdVenta());
            stmt.setInt(2, detalle.getIdArticulo());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setFloat(4, detalle.getPrecio());
            stmt.setFloat(5, detalle.getDescuento());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarDetalleVenta(DetalleVenta detalle) {
        String sql = "UPDATE detalle_venta SET id_venta = ?, id_articulo = ?, cantidad = ?, precio = ?, descuento = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getIdVenta());
            stmt.setInt(2, detalle.getIdArticulo());
            stmt.setInt(3, detalle.getCantidad());
            stmt.setFloat(4, detalle.getPrecio());
            stmt.setFloat(5, detalle.getDescuento());
            stmt.setInt(6, detalle.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarDetalleVenta(int id) {
        String sql = "DELETE FROM detalle_venta WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public DetalleVenta obtenerDetalleVentaPorId(int id) {
        String sql = "SELECT * FROM detalle_venta WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DetalleVenta(
                            rs.getInt("id"),
                            rs.getInt("id_venta"),
                            rs.getInt("id_articulo"),
                            rs.getInt("cantidad"),
                            rs.getFloat("precio"),
                            rs.getFloat("descuento")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DetalleVenta> obtenerTodosLosDetallesVenta() {
        List<DetalleVenta> listaDetalles = new ArrayList<>();
        String sql = "SELECT * FROM detalle_venta";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DetalleVenta detalle = new DetalleVenta(
                        rs.getInt("id"),
                        rs.getInt("id_venta"),
                        rs.getInt("id_articulo"),
                        rs.getInt("cantidad"),
                        rs.getFloat("precio"),
                        rs.getFloat("descuento")
                );
                listaDetalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalles;
    }

    public List<DetalleVenta> obtenerDetallesVentaPorId(int idVenta) {
        List<DetalleVenta> listaDetalles = new ArrayList<>();
        String sql = "SELECT id, id_venta, id_articulo, cantidad, precio, descuento "
                + "FROM detalle_venta WHERE id_venta = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idVenta);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetalleVenta detalle = new DetalleVenta(
                            rs.getInt("id"),
                            rs.getInt("id_venta"),
                            rs.getInt("id_articulo"),
                            rs.getInt("cantidad"),
                            rs.getFloat("precio"),
                            rs.getFloat("descuento")
                    );
                    listaDetalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaDetalles;
    }

}
