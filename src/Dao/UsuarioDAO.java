package Dao;

import Modelos.Conexion;
import Modelos.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final Connection connection;

    public UsuarioDAO() {
        this.connection = Conexion.getConnection();
    }

// Método para insertar un nuevo usuario en la base de datos
    public boolean insertarUsuario(Usuario usuario) {

        String sql = "INSERT INTO usuarios (id_rol, nombre, tipo_documento, num_documento, direccion, telefono, email, clave, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuario.getIdRol());
            statement.setString(2, usuario.getNombre()); // Nuevo campo "nombre"
            statement.setString(3, usuario.getTipoDocumento());
            statement.setString(4, usuario.getNumDocumento());
            statement.setString(5, usuario.getDireccion());
            statement.setString(6, usuario.getTelefono());
            statement.setString(7, usuario.getEmail());
            statement.setString(8, usuario.getClave());
            statement.setBoolean(9, usuario.isEstado());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// Método para actualizar un usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET id_rol = ?, nombre = ?, tipo_documento = ?, num_documento = ?, direccion = ?, telefono = ?, "
                + "email = ?, clave = ?, estado = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, usuario.getIdRol());
            statement.setString(2, usuario.getNombre()); // Nuevo campo "nombre"
            statement.setString(3, usuario.getTipoDocumento());
            statement.setString(4, usuario.getNumDocumento());
            statement.setString(5, usuario.getDireccion());
            statement.setString(6, usuario.getTelefono());
            statement.setString(7, usuario.getEmail());
            statement.setString(8, usuario.getClave());
            statement.setBoolean(9, usuario.isEstado());
            statement.setInt(10, usuario.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

// Método para "eliminar" un usuario cambiando su estado de 1 a 0
    public boolean eliminarUsuario(int id) {
        String sql = "UPDATE usuarios SET estado = 0 WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() {

        String sql = "SELECT * FROM usuarios WHERE estado = 1";
        List<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usuarios.add(mapResultSetToUsuario(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    // Método para mapear el ResultSet a un objeto Usuario
    private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {

        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setIdRol(resultSet.getInt("id_rol"));
        usuario.setNombre(resultSet.getString("nombre"));
        usuario.setTipoDocumento(resultSet.getString("tipo_documento"));
        usuario.setNumDocumento(resultSet.getString("num_documento"));
        usuario.setDireccion(resultSet.getString("direccion"));
        usuario.setTelefono(resultSet.getString("telefono"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setClave(resultSet.getString("clave"));
        usuario.setEstado(resultSet.getBoolean("estado"));

        return usuario;
    }

    // Método para verificar las credenciales de login (email y clave)
    public Usuario login(String email, String clave) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND clave = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, clave);  // Suponiendo que la clave esté en texto plano
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear un objeto Usuario con los datos obtenidos
                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getInt("id"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setTipoDocumento(rs.getString("tipo_documento"));
                    usuario.setNumDocumento(rs.getString("num_documento"));
                    usuario.setDireccion(rs.getString("direccion"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setClave(rs.getString("clave"));
                    usuario.setEstado(rs.getBoolean("estado"));
                    
                    return usuario;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si no se encuentra el usuario o las credenciales no son correctas
    }

    // Método para obtener la clave de un usuario por su ID
    public String obtenerClavePorId(int id) {
        String sql = "SELECT clave FROM usuarios WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retornar la clave del usuario
                return resultSet.getString("clave");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Retorna null si no se encuentra el usuario o ocurre un error
    }

    // Método para obtener un usuario por clave y correo electrónico
    public Usuario obtenerUsuarioPorClaveYEmail(String clave, String email) {
        String sql = "SELECT * FROM usuarios WHERE clave = ? AND email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, clave);
            statement.setString(2, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToUsuario(resultSet); // Método para mapear el ResultSet a un objeto Usuario
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
