package Controladores;

import Dao.CategoriaDAO;
import Modelos.Categoria;
import java.util.List;

public class CategoriaControlador {

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    // Crear un nuevo rol
    public boolean crearCategoria(String nombre, String descripcion) {

        Categoria nuevaCategoria = new Categoria();

        nuevaCategoria.setNombre(nombre);
        nuevaCategoria.setDescripcion(descripcion);

        return categoriaDAO.insertarCategoria(nuevaCategoria);
    }

    // Obtener todos los roles
    public List<Categoria> obtenerTodos() {
        return categoriaDAO.obtenerTodasLasCategorias();
    }

    // Obtener un rol por su ID
    public Categoria obtenerCategoriaPorId(int id) {
        return categoriaDAO.obtenerCategoriaPorId(id);
    }

    // Actualizar un rol existente
    public boolean editarCategoria(int id, String nombre, String descripcion) {
        Categoria categoria = categoriaDAO.obtenerCategoriaPorId(id);

        if (categoria != null) {
            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);
            return categoriaDAO.actualizarCategoria(categoria);
        }
        return false; // Si no se encuentra el rol
    }

    // Eliminar un rol por su ID
    public boolean eliminarCategoria(int id) {
        return categoriaDAO.eliminarCategoria(id);
    }

    public Integer obtenerIdPorNombre(String nombre) {
        return categoriaDAO.obtenerIdPorNombre(nombre);
    }

    public String obtenerNombrePorId(int id) {
        return categoriaDAO.obtenerNombrePorId(id);
    }

}
