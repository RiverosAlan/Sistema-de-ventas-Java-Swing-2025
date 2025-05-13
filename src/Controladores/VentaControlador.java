package Controladores;

import Dao.VentaDAO;
import Modelos.Venta;
import java.sql.*;

import java.util.List;

public class VentaControlador {

    private final VentaDAO ventaDAO = new VentaDAO();

    // Método para insertar una venta
    public boolean agregarVenta(Venta venta) {
        return ventaDAO.insertarVenta(venta);
    }

    // Método para actualizar una venta
    public boolean actualizarVenta(Venta venta) {
        return ventaDAO.actualizarVenta(venta);
    }

    // Método para eliminar una venta por ID
    public boolean eliminarVenta(int id) {
        return ventaDAO.eliminarVenta(id);
    }

    // Método para obtener una venta por ID
    public Venta obtenerVentaPorId(int id) {
        return ventaDAO.obtenerVentaPorId(id);
    }

    // Método para obtener todas las ventas
    public List<Venta> obtenerTodasLasVentas() {
        return ventaDAO.obtenerTodasLasVentas();
    }

    public int obtenerNumeroDeVentas() {
        return ventaDAO.obtenerNumeroDeVentas();
    }

    public int obtenerUltimoIdVenta() {
        return ventaDAO.obtenerUltimoIdVenta();
    }

    public List<Venta> obtenerVentasConCliente() throws SQLException {
        return ventaDAO.obtenerVentasConCliente();
    }

    public List<Venta> obtenerVentasdelDia() throws SQLException {
        return ventaDAO.obtenerVentasdelDia();
    }

    public List<Venta> obtenerVentasPorRangoFechas(Date fechaInicio, Date fechaFin) throws SQLException {

        return ventaDAO.obtenerVentasPorRangoFechas(fechaInicio, fechaFin);
    }

}
