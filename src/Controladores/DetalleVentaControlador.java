package Controladores;

import Dao.DetalleVentaDAO;
import Modelos.DetalleVenta;
import java.util.List;

public class DetalleVentaControlador {

    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();

    // Método para insertar un detalle de venta
    public boolean agregarDetalleVenta(DetalleVenta detalle) {
        return detalleVentaDAO.insertarDetalleVenta(detalle);
    }

    // Método para actualizar un detalle de venta
    public boolean actualizarDetalleVenta(DetalleVenta detalle) {
        return detalleVentaDAO.actualizarDetalleVenta(detalle);
    }

    // Método para eliminar un detalle de venta por ID
    public boolean eliminarDetalleVenta(int id) {
        return detalleVentaDAO.eliminarDetalleVenta(id);
    }

    // Método para obtener un detalle de venta por ID
    public DetalleVenta obtenerDetalleVentaPorId(int id) {
        return detalleVentaDAO.obtenerDetalleVentaPorId(id);
    }

    // Método para obtener todos los detalles de ventas
    public List<DetalleVenta> obtenerTodosLosDetallesVenta() {
        return detalleVentaDAO.obtenerTodosLosDetallesVenta();
    }

    public List<DetalleVenta> obtenerDetallesVentaPorId(int idVenta) {
        return detalleVentaDAO.obtenerDetallesVentaPorId(idVenta);
    }

}
