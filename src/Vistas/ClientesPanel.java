package Vistas;

import Controladores.ClienteControlador;
import Modelos.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClientesPanel extends javax.swing.JPanel {

    private ClienteControlador clienteControlador = new ClienteControlador();

    int idCliente = 0;

    public ClientesPanel() {
        initComponents();
        llenarTablaClientes();
        seleccionarDatosTablaClientes();
    }

    public void llenarTablaClientes() {

        // Obtener la lista de clientes desde el controlador
        List<Cliente> clientes = clienteControlador.obtenerTodosLosClientes();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaClientes.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Cliente c : clientes) {
            Object[] fila = {
                c.getId(),
                c.getNombre(),
                c.getTipoDocumento(),
                c.getNumDocumento(),
                c.getDireccion(),
                c.getTelefono(),
                c.getEmail()
            };

            tableModel.addRow(fila);
        }
    }

    public void registrarCliente() {

        String nombreCliente = txtNombreCliente.getText().trim();
        String tipoDocumento = txtTipoDocumento.getText().trim();
        String numDocumento = txtNumDocumento.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        Cliente cliente = new Cliente(nombreCliente, tipoDocumento, numDocumento, direccion, telefono, email);

        // Validar que los campos no estén vacíos
        if (nombreCliente.isEmpty() || tipoDocumento.isEmpty() || numDocumento.isEmpty() || direccion.isEmpty()
                || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            // Confirmar si el usuario desea crear el cliente
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea crear este Cliente?\n"
                    + "Nombre: " + nombreCliente + "\nTipo Documento: " + tipoDocumento
                    + "\nNúmero Documento: " + numDocumento + "\nDirección: " + direccion
                    + "\nTeléfono: " + telefono + "\nEmail: " + email,
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Si el usuario confirma, se procede a crear el cliente
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (clienteControlador.agregarCliente(cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    llenarTablaClientes();
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Creación de cliente cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void editarCliente() {

        String nombreCliente = txtNombreCliente.getText().trim();
        String tipoDocumento = txtTipoDocumento.getText().trim();
        String numDocumento = txtNumDocumento.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();

        // Validar que los campos no estén vacíos
        if (nombreCliente.isEmpty() || tipoDocumento.isEmpty() || numDocumento.isEmpty() || direccion.isEmpty()
                || telefono.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            // Confirmar si el usuario desea editar el cliente
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea editar este Cliente?\n"
                    + "Nombre: " + nombreCliente + "\nTipo Documento: " + tipoDocumento
                    + "\nNúmero Documento: " + numDocumento + "\nDirección: " + direccion
                    + "\nTeléfono: " + telefono + "\nEmail: " + email,
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Si el usuario confirma, se procede a editar el cliente
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Crear un objeto Cliente con los nuevos datos
                Cliente cliente = new Cliente(nombreCliente, tipoDocumento, numDocumento, direccion, telefono, email);

                if (clienteControlador.actualizarCliente(idCliente, cliente)) {
                    JOptionPane.showMessageDialog(null, "Cliente editado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    llenarTablaClientes();  // Refrescar la tabla con los nuevos datos
                    limpiarDatos();  // Si necesitas limpiar los campos después de editar
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo editar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Edición de cliente cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void eliminarCliente() {
        // Verificar que se haya seleccionado un cliente
        if (idCliente == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un cliente para eliminar.", "Cliente no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar si el usuario desea eliminar el cliente
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este Cliente?\n"
                + "Esta acción marcará el cliente como inactivo.",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamar al controlador para actualizar el estado del cliente a inactivo
            if (clienteControlador.eliminarCliente(idCliente)) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente (marcado como inactivo).", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTablaClientes();
                limpiarDatos();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Eliminación de cliente cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void limpiarDatos() {

        txtDireccion.setText("");
        txtEmail.setText("");
        txtNombreCliente.setText("");
        txtNumDocumento.setText("");
        txtTelefono.setText("");
        txtTipoDocumento.setText("");

        idCliente = 0;

    }

    public void seleccionarDatosTablaClientes() {

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaClientes.getSelectedRow();

                if (filaSeleccionada != -1) {
                    // Obtener los valores de las columnas de la fila seleccionada
                    Object id = tablaClientes.getValueAt(filaSeleccionada, 0);
                    Object nombre = tablaClientes.getValueAt(filaSeleccionada, 1);
                    Object tipoDocumento = tablaClientes.getValueAt(filaSeleccionada, 2);
                    Object numDocumento = tablaClientes.getValueAt(filaSeleccionada, 3);
                    Object direccion = tablaClientes.getValueAt(filaSeleccionada, 4);
                    Object telefono = tablaClientes.getValueAt(filaSeleccionada, 5);
                    Object email = tablaClientes.getValueAt(filaSeleccionada, 6);

                    // Asignar los valores a los campos de texto
                    idCliente = (int) id;
                    txtNombreCliente.setText(String.valueOf(nombre));
                    txtTipoDocumento.setText(String.valueOf(tipoDocumento));
                    txtNumDocumento.setText(String.valueOf(numDocumento));
                    txtDireccion.setText(String.valueOf(direccion));
                    txtTelefono.setText(String.valueOf(telefono));
                    txtEmail.setText(String.valueOf(email));

                    System.out.println("ID Cliente: " + idCliente);
                }
            }
        });
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTipoDocumento = new javax.swing.JTextField();
        txtNumDocumento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Gestión de Clientes:");

        tablaClientes.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Razon Social", "Tipo Documento", "Num Documento", "Dirección", "Télefono", "Email"
            }
        ));
        tablaClientes.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaClientes.setRowHeight(35);
        jScrollPane1.setViewportView(tablaClientes);

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel4.setText("Registrar Cliente:");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Rázon Social:");

        txtNombreCliente.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Tipo Documento:");

        txtTipoDocumento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtNumDocumento.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel8.setText("Num Documento:");

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel9.setText("Dirección:");

        txtDireccion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel10.setText("Télefono:");

        txtTelefono.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel11.setText("Email:");

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

        jButton5.setBackground(new java.awt.Color(108, 117, 125));
        jButton5.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Cancelar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNumDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1428, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                                .addComponent(txtNombreCliente))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumDocumento, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(128, 128, 128))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        registrarCliente();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarCliente();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        eliminarCliente();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumDocumento;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoDocumento;
    // End of variables declaration//GEN-END:variables
}
