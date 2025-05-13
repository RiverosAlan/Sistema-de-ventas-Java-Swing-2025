package Vistas;

import Controladores.ProveedorControlador;
import Modelos.Proveedor;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProveedoresPanel extends javax.swing.JPanel {

    ProveedorControlador proveedorControlador = new ProveedorControlador();

    int idProveedor = 0;

    public ProveedoresPanel() {
        initComponents();
        llenarTablaProveedores();
        seleccionarDatosTablaProveedores();
    }

    public void llenarTablaProveedores() {

        // Obtener la lista de proveedores desde el controlador
        List<Proveedor> proveedores = proveedorControlador.obtenerTodosLosProveedores();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaProveedores.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Proveedor proveedor : proveedores) {
            Object[] fila = {
                proveedor.getId(),
                proveedor.getRazonSocial(),
                proveedor.getDireccion(),
                proveedor.getTelefono(),
                proveedor.getEmail()
            };

            tableModel.addRow(fila);
        }
    }

    public void registrarProveedor() {

        String razonSocial = txtRazonSocial.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String email = txtEmail.getText().trim();

        // Validar que los campos no estén vacíos
        if (razonSocial.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            // Confirmar si el usuario desea crear el proveedor
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea crear este Proveedor?\n"
                    + "Razón Social: " + razonSocial + "\nTeléfono: " + telefono + "\nDirección: " + direccion + "\nEmail: " + email,
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Si el usuario confirma, se procede a crear el proveedor
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Crear el objeto Proveedor con los datos ingresados
                Proveedor proveedor = new Proveedor(razonSocial, telefono, direccion, email);

                // Agregar el proveedor usando el controlador
                if (proveedorControlador.agregarProveedor(proveedor)) {
                    JOptionPane.showMessageDialog(null, "Proveedor creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    llenarTablaProveedores();
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Creación de Proveedor cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void editarProveedor() {

        String razonSocial = txtRazonSocial.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String email = txtEmail.getText().trim();

        // Validar que los campos no estén vacíos
        if (razonSocial.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            // Confirmar si el usuario desea editar el proveedor
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea editar este Proveedor?\n"
                    + "Razón Social: " + razonSocial + "\nTeléfono: " + telefono + "\nDirección: " + direccion + "\nEmail: " + email,
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Si el usuario confirma, se procede a editar el proveedor
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Crear el objeto Proveedor con los datos editados
                Proveedor proveedor = new Proveedor(idProveedor, razonSocial, telefono, direccion, email);

                // Actualizar el proveedor usando el controlador
                if (proveedorControlador.actualizarProveedor(proveedor)) {
                    JOptionPane.showMessageDialog(null, "Proveedor editado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    llenarTablaProveedores();
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo editar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Edición de Proveedor cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public void seleccionarDatosTablaProveedores() {

        tablaProveedores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaProveedores.getSelectedRow();

                if (filaSeleccionada != -1) {
                    // Obtener los valores de las columnas de la fila seleccionada
                    Object id = tablaProveedores.getValueAt(filaSeleccionada, 0);
                    Object razonSocial = tablaProveedores.getValueAt(filaSeleccionada, 1);
                    Object telefono = tablaProveedores.getValueAt(filaSeleccionada, 2);
                    Object direccion = tablaProveedores.getValueAt(filaSeleccionada, 3);
                    Object email = tablaProveedores.getValueAt(filaSeleccionada, 4);

                    // Asignar los valores a los campos de texto
                    idProveedor = (int) id;
                    txtRazonSocial.setText(String.valueOf(razonSocial));
                    txtTelefono.setText(String.valueOf(telefono));
                    txtDireccion.setText(String.valueOf(direccion));
                    txtEmail.setText(String.valueOf(email));

                    System.out.println(idProveedor);
                }
            }
        });
    }

    public void eliminarProveedor() {

        // Confirmar si el usuario desea eliminar el proveedor
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este Proveedor?\n"
                + "Razón Social: " + txtRazonSocial.getText().trim(),
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Si el usuario confirma, se procede a eliminar el proveedor
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Crear el objeto Proveedor con los datos a eliminar

            // Eliminar el proveedor usando el controlador
            if (proveedorControlador.eliminarProveedor(idProveedor)) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTablaProveedores();
                limpiarDatos();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Eliminación de Proveedor cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void limpiarDatos() {
        txtRazonSocial.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");

        idProveedor = 0;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("Gestión de proveedores");

        tablaProveedores.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Razón Social", "Dirección", "Télefono", "Email"
            }
        ));
        tablaProveedores.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaProveedores.setRowHeight(35);
        jScrollPane1.setViewportView(tablaProveedores);
        if (tablaProveedores.getColumnModel().getColumnCount() > 0) {
            tablaProveedores.getColumnModel().getColumn(4).setResizable(false);
        }

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Registrar nuevo proveedor:");

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel4.setText("Razón Social:");

        txtRazonSocial.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setText("Dirección:");

        txtDireccion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Télefono:");

        txtTelefono.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Email");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(39, 39, 39)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(310, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(131, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        registrarProveedor();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarProveedor();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        eliminarProveedor();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
