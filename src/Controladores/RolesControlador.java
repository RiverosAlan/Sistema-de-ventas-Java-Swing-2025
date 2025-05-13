package Controladores;

import Dao.RolesDAO;
import Modelos.Roles;
import java.util.List;

public class RolesControlador {

    private final RolesDAO rolesDAO = new RolesDAO();

    // Crear un nuevo rol
    public boolean crearRol(String nombre, String descripcion) {
        Roles nuevoRol = new Roles();
        nuevoRol.setNombre(nombre);
        nuevoRol.setDescripcion(descripcion);
        return rolesDAO.insertarRol(nuevoRol); // Método del DAO para crear el rol
    }

    // Obtener todos los roles
    public List<Roles> obtenerTodosLosRoles() {
        return rolesDAO.obtenerTodos(); // Método del DAO para obtener todos los roles
    }

    // Obtener un rol por su ID
    public Roles obtenerRolPorId(int id) {
        return rolesDAO.obtenerPorId(id); // Método del DAO para obtener un rol por ID
    }

    // Actualizar un rol existente
    public boolean editarRol(int id, String nombre, String descripcion) {
        Roles rolExistente = rolesDAO.obtenerPorId(id);
        if (rolExistente != null) {
            rolExistente.setNombre(nombre);
            rolExistente.setDescripcion(descripcion);
            return rolesDAO.actualizar(rolExistente); // Método del DAO para actualizar el rol
        }
        return false; // Si no se encuentra el rol
    }

    // Eliminar un rol por su ID
    public boolean eliminarRol(int id) {
        return rolesDAO.eliminar(id); // Método del DAO para eliminar el rol
    }

    // Método para obtener un rol por su nombre desde el DAO
    public Roles obtenerRolPorNombre(String nombre) {
        return rolesDAO.obtenerRolPorNombre(nombre);
    }

    public int obtenerIdRolPorNombre(String nombre) {
        return rolesDAO.obtenerIdRolPorNombre(nombre);
    }

    public String obtenerNombreRolPorId(int id) {
        // Llamar al método del DAO para obtener el nombre del rol por su id
        return rolesDAO.obtenerNombreRolPorId(id);
    }

}
