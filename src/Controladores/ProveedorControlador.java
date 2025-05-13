package Controladores;

import Dao.ProveedorDAO;
import Modelos.Proveedor;
import java.util.List;

public class ProveedorControlador {

    private final ProveedorDAO proveedorDAO = new ProveedorDAO();

    public boolean agregarProveedor(Proveedor proveedor) {
        return proveedorDAO.insertarProveedor(proveedor);
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        return proveedorDAO.actualizarProveedor(proveedor);
    }

    public boolean eliminarProveedor(int id) {
        return proveedorDAO.eliminarProveedor(id);
    }

    public Proveedor obtenerProveedorPorId(int id) {
        return proveedorDAO.obtenerProveedorPorId(id);
    }

    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorDAO.obtenerTodosLosProveedores();
    }
    
    
     public Integer obtenerIdPorNombre(String nombre) {
        return proveedorDAO.obtenerIdPorNombre(nombre);
    }

 
    public String obtenerNombrePorId(int id) {
        return proveedorDAO.obtenerNombrePorId(id);
    }

}
