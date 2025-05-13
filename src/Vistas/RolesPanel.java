package Vistas;

import Controladores.RolesControlador;
import Modelos.Roles;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RolesPanel extends javax.swing.JPanel {

    private RolesControlador rolesControlador = new RolesControlador();
    private int idRol = 0;

    public RolesPanel() {
        initComponents();
        llenarTabla();
        seleccionarDatosTabla();
    }

    public void limpiarDatos() {
        txtDescripcion.setText("");
        txtNombreRol.setText("");
    }

    //mostrar datos 
    public void llenarTabla() {

        // Obtener la lista de roles desde el controlador
        List<Roles> roles = new RolesControlador().obtenerTodosLosRoles();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaRoles.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Desactivar el modo de redimensionamiento automático
        tablaRoles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Ajustar el ancho de la primera columna
        tablaRoles.getColumnModel().getColumn(0).setPreferredWidth(50); // Ancho preferido
        tablaRoles.getColumnModel().getColumn(1).setPreferredWidth(500);
        tablaRoles.getColumnModel().getColumn(2).setPreferredWidth(510);

        // Llenar la tabla con los datos de la lista
        for (Roles rol : roles) {
            Object[] fila = {
                rol.getId(),
                rol.getNombre(), // Corregido: el método debe ser "getNombre()"
                rol.getDescripcion() // Corregido: el método debe ser "getDescripcion()"
            };

            tableModel.addRow(fila);
        }
    }

    public void registrarRol() {

        String nombreRol = txtNombreRol.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        // Validar que los campos no estén vacíos
        if (nombreRol.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        } else {
            // Confirmar si el usuario desea crear el rol
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Está seguro de que desea crear este rol?\n"
                    + "Nombre: " + nombreRol + "\nDescripción: " + descripcion,
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            // Si el usuario confirma, se procede a crear el rol
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (rolesControlador.crearRol(nombreRol, descripcion)) {
                    JOptionPane.showMessageDialog(null, "Rol creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    llenarTabla();
                    limpiarDatos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo crear el rol.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Creación de rol cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    public void editarRol() {
        String nombreRol = txtNombreRol.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        // Validar que los campos no estén vacíos
        if (nombreRol.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar si el usuario desea editar el rol
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea editar este rol?\n"
                + "Nombre: " + nombreRol + "\nDescripción: " + descripcion,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamar al método del controlador para editar el rol
            if (rolesControlador.editarRol(idRol, nombreRol, descripcion)) {
                JOptionPane.showMessageDialog(null, "Rol editado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTabla(); // Actualizar la tabla con los nuevos datos
                limpiarDatos(); // Limpiar los campos de texto
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo editar el rol.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Edición de rol cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void eliminarRol() {
        // Verificar que hay un ID de rol seleccionado
        if (idRol <= 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un rol para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar si el usuario desea eliminar el rol
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea eliminar este rol?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamar al método del controlador para eliminar el rol
            if (rolesControlador.eliminarRol(idRol)) {
                JOptionPane.showMessageDialog(null, "Rol eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTabla(); // Actualizar la tabla para reflejar los cambios
                limpiarDatos(); // Limpiar los campos de texto
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el rol.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Eliminación de rol cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void seleccionarDatosTabla() {

        tablaRoles.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaRoles.getSelectedRow();

                if (filaSeleccionada != -1) {
                    // Obtener los valores de las columnas de la fila seleccionada
                    Object id = tablaRoles.getValueAt(filaSeleccionada, 0);
                    Object nombre = tablaRoles.getValueAt(filaSeleccionada, 1);
                    Object descripcion = tablaRoles.getValueAt(filaSeleccionada, 2);

                    // También puedes asignarlos a los campos de texto, por ejemplo:
                    idRol = (int) id;
                    txtNombreRol.setText(String.valueOf(nombre));
                    txtDescripcion.setText(String.valueOf(descripcion));

                    System.out.println(idRol);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRoles = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombreRol = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("Roles del sistema");

        tablaRoles.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaRoles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Descripcion"
            }
        ));
        tablaRoles.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaRoles.setRowHeight(35);
        jScrollPane1.setViewportView(tablaRoles);

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Registrar nuevo rol:");

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel4.setText("Nombre de rol:");

        txtDescripcion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Descripcion:");

        txtNombreRol.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

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
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(39, 39, 39)
                            .addComponent(txtNombreRol, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1063, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(385, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombreRol, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(99, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        registrarRol();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        limpiarDatos();
        idRol=0;
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarRol();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       eliminarRol();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRoles;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombreRol;
    // End of variables declaration//GEN-END:variables
}
