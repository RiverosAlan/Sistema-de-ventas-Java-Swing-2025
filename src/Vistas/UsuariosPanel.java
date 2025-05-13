package Vistas;

import Controladores.RolesControlador;
import Controladores.UsuarioControlador;
import Modelos.Roles;
import Modelos.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UsuariosPanel extends javax.swing.JPanel {

    private UsuarioControlador usuarioControlador = new UsuarioControlador();
    private RolesControlador rolesControlador = new RolesControlador();

    private int idUsuarioSeleccionado = 0;

    public UsuariosPanel() {
        initComponents();
        tablaUsuarios.setDefaultEditor(Object.class, null);
        llenarTabla();
        llenarComboBoxConRoles();
        seleccionarDatosTabla();
    }

    //mostrar datos 
    public void llenarTabla() {

        // Obtener la lista de roles desde el controlador
        List<Usuario> usuarios = usuarioControlador.obtenerTodosUsuarios();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaUsuarios.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Usuario usuario : usuarios) {
            Object[] fila = {
                usuario.getId(), // ID del usuario
                rolesControlador.obtenerNombreRolPorId(usuario.getIdRol()),
                usuario.getNombre(), // Nombre del usuario
                usuario.getTipoDocumento(),
                usuario.getNumDocumento(),
                usuario.getDireccion(),
                usuario.getTelefono(),
                usuario.getEmail()
            };

            tableModel.addRow(fila);
        }

    }

    public void llenarComboBoxConRoles() {
        // Obtener todos los roles desde el DAO
        List<Roles> roles = rolesControlador.obtenerTodosLosRoles();

        // Limpiar el JComboBox antes de llenarlo
        cmbRoles.removeAllItems();

        // Agregar un elemento predeterminado (opcional)
        cmbRoles.addItem("Seleccione un rol");

        // Llenar el JComboBox con los nombres de los roles
        for (Roles rol : roles) {
            cmbRoles.addItem(rol.getNombre());
        }
    }

    private void registrarUsuario() {
        // Obtener los valores de los campos
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String tipoDocumento = txtTipoDocumento.getText().trim();
        String numDocumento = txtNumDocumento.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String idRolStr = cmbRoles.getSelectedItem().toString();  // Obtener el rol seleccionado

        // Obtener las contraseñas desde los campos de texto
        String contraseña = new String(txtpassword.getPassword()).trim();
        String confirmarContraseña = new String(txtConfirmarPassword.getPassword()).trim();

        // Validar que todos los campos estén completos
        if (nombre.isEmpty() || direccion.isEmpty() || tipoDocumento.isEmpty() || numDocumento.isEmpty()
                || email.isEmpty() || telefono.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que las contraseñas coincidan
        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden. Por favor, verifique.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato del correo (básico)
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir idRol a entero (asumimos que el ComboBox contiene el nombre del rol)
        int idRol = obtenerIdRolPorNombre(idRolStr);
        if (idRol == -1) {
            JOptionPane.showMessageDialog(this, "Rol seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Preguntar al usuario si está seguro de registrar el usuario
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de crear el usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);

        // Si el usuario selecciona "Sí", proceder con el registro
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Crear el objeto Usuario con los datos obtenidos
            Usuario usuario = new Usuario(idRol, nombre, tipoDocumento, numDocumento, direccion, telefono, email, contraseña, true);

            // Llamada al controlador para agregar el usuario
            boolean resultado = usuarioControlador.agregarUsuario(usuario);

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar los campos después de la inserción
                limpiarCampos();
                llenarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Si el usuario selecciona "No", no se realiza la acción
            JOptionPane.showMessageDialog(this, "Registro cancelado.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editarUsuario() {
        if (idUsuarioSeleccionado == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los valores de los campos
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String tipoDocumento = txtTipoDocumento.getText().trim();
        String numDocumento = txtNumDocumento.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String idRolStr = cmbRoles.getSelectedItem().toString();

        // Obtener las contraseñas desde los campos de texto (si se cambian)
        String contraseña = new String(txtpassword.getPassword()).trim();
        String confirmarContraseña = new String(txtConfirmarPassword.getPassword()).trim();

        // Validar que todos los campos estén completos
        if (nombre.isEmpty() || direccion.isEmpty() || tipoDocumento.isEmpty() || numDocumento.isEmpty()
                || email.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que las contraseñas coincidan (solo si se cambiaron)
        if (!contraseña.isEmpty() && !contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden. Por favor, verifique.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar el formato del correo
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            JOptionPane.showMessageDialog(this, "El correo electrónico no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir idRol a entero
        int idRol = obtenerIdRolPorNombre(idRolStr);
        if (idRol == -1) {
            JOptionPane.showMessageDialog(this, "Rol seleccionado no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Confirmar la acción antes de proceder
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas editar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;  // Si el usuario no confirma, salir del método
        }

        // Crear el objeto Usuario con los datos obtenidos
        Usuario usuario = new Usuario(idRol, nombre, tipoDocumento, numDocumento, direccion, telefono, email, contraseña, true);
        usuario.setId(idUsuarioSeleccionado);  // Establecer el ID del usuario

        // Llamada al controlador para actualizar el usuario
        boolean resultado = usuarioControlador.actualizarUsuario(usuario);

        if (resultado) {
            JOptionPane.showMessageDialog(this, "Usuario editado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos después de la actualización
            limpiarCampos();
            llenarTabla();  // Volver a llenar la tabla con los datos actualizados
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar el usuario. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarUsuario() {
    if (idUsuarioSeleccionado == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un usuario de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Confirmar la acción antes de proceder
    int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas desactivar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
    if (respuesta != JOptionPane.YES_OPTION) {
        return;  // Si el usuario no confirma, salir del método
    }

    // Llamada al controlador para cambiar el estado del usuario
    boolean resultado = usuarioControlador.eliminarUsuario(idUsuarioSeleccionado);
   

    if (resultado) {
        JOptionPane.showMessageDialog(this, "Usuario desactivado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Volver a llenar la tabla con los datos actualizados
        
        llenarTabla();  
         limpiarCampos();
    
    } else {
        JOptionPane.showMessageDialog(this, "Error al desactivar el usuario. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    
    
    

    public void seleccionarDatosTabla() {

        // Agregar el listener de selección a la tabla
        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {  // Para evitar que se dispare dos veces
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaUsuarios.getSelectedRow();

                if (filaSeleccionada != -1) {
                    // Obtener los valores de las columnas de la fila seleccionada
                    Object id = tablaUsuarios.getValueAt(filaSeleccionada, 0);
                    Object rol = tablaUsuarios.getValueAt(filaSeleccionada, 1);  // Rol// Suponiendo que el ID está en la primera columna
                    Object nombre = tablaUsuarios.getValueAt(filaSeleccionada, 2);  // Nombre
                    Object numDocumento = tablaUsuarios.getValueAt(filaSeleccionada, 3);  // Número de documento
                    Object tipoDocumento = tablaUsuarios.getValueAt(filaSeleccionada, 4);  // Tipo de documento
                    Object direccion = tablaUsuarios.getValueAt(filaSeleccionada, 5);  // Dirección

                    Object telefono = tablaUsuarios.getValueAt(filaSeleccionada, 6);  // Teléfono
                    Object email = tablaUsuarios.getValueAt(filaSeleccionada, 7);  // Correo

                    // Asignar los valores a los campos de texto
                    idUsuarioSeleccionado = (int) id;  // Guardar el ID del usuario seleccionado
                    txtNombre.setText(String.valueOf(nombre));
                    txtDireccion.setText(String.valueOf(direccion));
                    txtTipoDocumento.setText(String.valueOf(tipoDocumento));
                    txtNumDocumento.setText(String.valueOf(numDocumento));
                    txtEmail.setText(String.valueOf(email));
                    txtTelefono.setText(String.valueOf(telefono));
                    cmbRoles.setSelectedItem(rol);  // Asumimos que el ComboBox tiene el nombre del rol

                    String passwordDB = usuarioControlador.obtenerClave(idUsuarioSeleccionado);

                    txtpassword.setText(passwordDB);
                    txtConfirmarPassword.setText(passwordDB);

                    System.out.println("ID de Usuario Seleccionado: " + idUsuarioSeleccionado);  // Mostrar el ID en la consola para verificación
                }
            }
        });
    }

    private void limpiarCampos() {
        // Limpiar los campos de texto
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTipoDocumento.setText("");
        txtNumDocumento.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");

        // Limpiar los campos de contraseña
        txtpassword.setText("");
        txtConfirmarPassword.setText("");

        // Restablecer el ComboBox al primer valor o índice seleccionado
        cmbRoles.setSelectedIndex(0);  // Asegúrate de que el ComboBox tenga una opción predeterminada en el índice 0
    }

    private int obtenerIdRolPorNombre(String nombreRol) {
        // Suponiendo que tienes un método en tu controlador para obtener el ID del rol por su nombre
        return rolesControlador.obtenerIdRolPorNombre(nombreRol);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtTipoDocumento = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtNumDocumento = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        txtDireccion = new javax.swing.JTextField();
        cmbRoles = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        txtConfirmarPassword = new javax.swing.JPasswordField();
        jLabel11 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("Gestión de Usuarios");

        tablaUsuarios.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Rol", "Nombre completo", "Tipo de documento", "Num de documento", "Dirección", "Télefono", "Email"
            }
        ));
        tablaUsuarios.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaUsuarios.setRowHeight(35);
        jScrollPane1.setViewportView(tablaUsuarios);

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Registrar Usuario:");

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setText("Tipo documento:");

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel4.setText("Nombre Completo:");

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setText("Email:");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Dirección :");

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Télefono:");

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel8.setText("Num documento:");

        txtNombre.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtTipoDocumento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtTelefono.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtNumDocumento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(40, 167, 69));
        jButton1.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Registrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 193, 7));
        jButton2.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Editar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(220, 53, 69));
        jButton3.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(108, 117, 125));
        jButton4.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txtDireccion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel9.setText("Asignar rol:");

        jLabel10.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel10.setText("Contraseña:");

        txtpassword.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N

        txtConfirmarPassword.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel11.setText("Confirmar contraseña:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(45, 45, 45)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtConfirmarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1413, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTipoDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNumDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                                .addGap(33, 33, 33)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))))))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtConfirmarPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        registrarUsuario();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarUsuario();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       eliminarUsuario();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      idUsuarioSeleccionado=0;
      limpiarCampos();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbRoles;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JPasswordField txtConfirmarPassword;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumDocumento;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoDocumento;
    private javax.swing.JPasswordField txtpassword;
    // End of variables declaration//GEN-END:variables
}
