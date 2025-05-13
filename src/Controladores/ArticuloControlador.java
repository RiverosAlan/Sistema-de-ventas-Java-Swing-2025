package Controladores;

import Dao.ArticuloDAO;
import Modelos.Articulo;
import java.util.List;

public class ArticuloControlador {

    private final ArticuloDAO articuloDAO = new ArticuloDAO();

    public boolean agregarArticulo(Articulo articulo) {
        return articuloDAO.insertarArticulo(articulo);
    }

    public boolean actualizarArticulo(int idArticulo, Articulo articulo) {
        return articuloDAO.actualizarArticulo(idArticulo, articulo);
    }

    public boolean eliminarArticulo(int id) {
        return articuloDAO.eliminarArticulo(id);
    }

    public Articulo obtenerArticuloPorId(int id) {
        return articuloDAO.obtenerArticuloPorId(id);
    }

    public List<Articulo> obtenerTodosLosArticulos() {
        return articuloDAO.obtenerTodosLosArticulos();
    }

    public List<Articulo> buscarArticulosPorNombre(String nombre) {
        return articuloDAO.obtenerArticulosPorNombre(nombre);
    }

    public Articulo buscarArticuloPorNombreExacto(String nombre) {

        return articuloDAO.obtenerArticuloPorNombre(nombre);
    }

    public boolean incrementarStock(int idArticulo, int cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a incrementar debe ser mayor a 0.");
            return false;
        }
        return articuloDAO.incrementarStock(idArticulo, cantidad);
    }

    public boolean reducirStock(int idArticulo, int cantidad) {
        if (cantidad <= 0) {
            System.out.println("La cantidad a reducir debe ser mayor a 0.");
            return false;
        }
        return articuloDAO.reducirStock(idArticulo, cantidad);
    }

}
