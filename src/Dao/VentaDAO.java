package Dao;

import Modelos.Cliente;
import Modelos.Conexion;
import Modelos.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    private Connection conexion;

    public VentaDAO() {
        this.conexion = Conexion.getConnection();
    }

    public boolean insertarVenta(Venta venta) {
        String sql = "INSERT INTO ventas (id_cliente, id_usuario, tipo_comprobante, num_comprobante, fecha, total, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, venta.getIdCliente());
            stmt.setInt(2, venta.getIdUsuario());
            stmt.setString(3, venta.getTipoComprobante());
            stmt.setString(4, venta.getNumComprobante());
            stmt.setString(5, venta.getFecha());
            stmt.setFloat(6, venta.getTotal());
            stmt.setString(7, venta.getEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarVenta(Venta venta) {
        String sql = "UPDATE ventas SET id_cliente = ?, id_usuario = ?, tipo_comprobante = ?, num_comprobante = ?, "
                + "fecha = ?, total = ?, estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, venta.getIdCliente());
            stmt.setInt(2, venta.getIdUsuario());
            stmt.setString(3, venta.getTipoComprobante());
            stmt.setString(4, venta.getNumComprobante());
            stmt.setString(5, venta.getFecha());
            stmt.setFloat(6, venta.getTotal());
            stmt.setString(7, venta.getEstado());
            stmt.setInt(8, venta.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarVenta(int id) {
        String sql = "DELETE FROM ventas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Venta obtenerVentaPorId(int id) {
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Venta(
                            rs.getInt("id"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_usuario"),
                            rs.getString("tipo_comprobante"),
                            rs.getString("num_comprobante"),
                            rs.getString("fecha"),
                            rs.getFloat("total"),
                            rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Venta> obtenerTodasLasVentas() {
        List<Venta> listaVentas = new ArrayList<>();
        String sql = "SELECT * FROM ventas";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Venta venta = new Venta(
                        rs.getInt("id"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_usuario"),
                        rs.getString("tipo_comprobante"),
                        rs.getString("num_comprobante"),
                        rs.getString("fecha"),
                        rs.getFloat("total"),
                        rs.getString("estado")
                );
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVentas;
    }

    public int obtenerNumeroDeVentas() {
        int numeroVentas = 0;
        String sql = "SELECT COUNT(*) FROM ventas";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                numeroVentas = rs.getInt(1);  // El resultado de COUNT(*) se encuentra en la primera columna
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeroVentas;
    }

    public int obtenerUltimoIdVenta() {
        String sql = "SELECT MAX(id) FROM ventas"; // Selecciona el ID máximo de la tabla ventas
        int lastId = -1; // Valor por defecto si no se encuentra el id

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                lastId = rs.getInt(1); // Obtiene el ID máximo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lastId; // Devuelve el último ID insertado o -1 si no se pudo obtener el ID
    }

    public List<Venta> obtenerVentasConCliente() throws SQLException {

        String sql = "SELECT "
                + "v.id, v.id_cliente, v.id_usuario, v.tipo_comprobante, v.num_comprobante, "
                + "v.fecha, v.total, v.estado, "
                + "c.nombre, c.num_documento, c.telefono "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE v.estado = 1 "
                + "ORDER BY v.id";

        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setIdCliente(rs.getInt("id_cliente"));
                venta.setIdUsuario(rs.getInt("id_usuario"));
                venta.setTipoComprobante(rs.getString("tipo_comprobante"));
                venta.setNumComprobante(rs.getString("num_comprobante"));
                venta.setFecha(rs.getString("fecha"));
                venta.setTotal(rs.getFloat("total"));
                venta.setEstado(rs.getString("estado"));

                // Crear el objeto Cliente
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setNumDocumento(rs.getString("num_documento"));
                cliente.setTelefono(rs.getString("telefono"));

                // Asociar el cliente a la venta
                venta.setCliente(cliente);

                // Añadir la venta a la lista
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public List<Venta> obtenerVentasdelDia() throws SQLException {

        String sql = "SELECT "
                + "v.id, v.id_cliente, v.id_usuario, v.tipo_comprobante, v.num_comprobante, "
                + "v.fecha, v.total, v.estado, "
                + "c.nombre, c.num_documento, c.telefono "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE v.estado = 1 AND DATE(v.fecha) = CURDATE() "
                + "ORDER BY v.id";

        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId(rs.getInt("id"));
                venta.setIdCliente(rs.getInt("id_cliente"));
                venta.setIdUsuario(rs.getInt("id_usuario"));
                venta.setTipoComprobante(rs.getString("tipo_comprobante"));
                venta.setNumComprobante(rs.getString("num_comprobante"));
                venta.setFecha(rs.getString("fecha"));
                venta.setTotal(rs.getFloat("total"));
                venta.setEstado(rs.getString("estado"));

                // Crear el objeto Cliente
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setNumDocumento(rs.getString("num_documento"));
                cliente.setTelefono(rs.getString("telefono"));

                // Asociar el cliente a la venta
                venta.setCliente(cliente);

                // Añadir la venta a la lista
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public List<Venta> obtenerVentasPorRangoFechas(Date fechaInicio, Date fechaFin) throws SQLException {
        String sql = "SELECT "
                + "v.id, v.id_cliente, v.id_usuario, v.tipo_comprobante, v.num_comprobante, "
                + "v.fecha, v.total, v.estado, "
                + "c.nombre, c.num_documento, c.telefono "
                + "FROM ventas v "
                + "INNER JOIN clientes c ON v.id_cliente = c.id "
                + "WHERE v.estado = 1 AND DATE(v.fecha) BETWEEN ? AND ? "
                + "ORDER BY v.id";

        List<Venta> ventas = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            // Asignar parámetros al PreparedStatement
            stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
            stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setId(rs.getInt("id"));
                    venta.setIdCliente(rs.getInt("id_cliente"));
                    venta.setIdUsuario(rs.getInt("id_usuario"));
                    venta.setTipoComprobante(rs.getString("tipo_comprobante"));
                    venta.setNumComprobante(rs.getString("num_comprobante"));
                    venta.setFecha(rs.getString("fecha")); // Fecha como String
                    venta.setTotal(rs.getFloat("total"));
                    venta.setEstado(rs.getString("estado"));

                    // Relación con el cliente
                    Cliente cliente = new Cliente();
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setNumDocumento(rs.getString("num_documento"));
                    cliente.setTelefono(rs.getString("telefono"));
                    venta.setCliente(cliente);

                    ventas.add(venta);
                }
            }
        }

        return ventas;
    }

}
