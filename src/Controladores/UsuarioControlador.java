package Controladores;

import Dao.UsuarioDAO;
import Modelos.Usuario;
import java.util.List;

public class UsuarioControlador {

    private UsuarioDAO usuarioDAO;

    public UsuarioControlador() {

        usuarioDAO = new UsuarioDAO();
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioDAO.obtenerTodosUsuarios();
    }

    // Obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioDAO.obtenerUsuarioPorId(id);
    }

    // Agregar un nuevo usuario
    public boolean agregarUsuario(Usuario usuario) {
        return usuarioDAO.insertarUsuario(usuario);
    }

    // Actualizar un usuario existente
    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizarUsuario(usuario);
    }

    // Eliminar un usuario por ID
    public boolean eliminarUsuario(int id) {
        return usuarioDAO.eliminarUsuario(id);
    }

    // Método para realizar el login
    public Usuario login(String email, String clave) {
        return usuarioDAO.login(email, clave);
    }

    // Método en el controlador para obtener la clave por ID
    public String obtenerClave(int id) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();  // Asegúrate de tener una instancia del DAO
        return usuarioDAO.obtenerClavePorId(id);  // Llamamos al método del DAO
    }

    public Usuario obtenerUsuarioPorClaveYEmail(String clave, String email) {
        return usuarioDAO.obtenerUsuarioPorClaveYEmail(clave, email);

    }

}
