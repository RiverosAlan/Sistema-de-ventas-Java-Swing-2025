package Vistas;

import Modelos.Usuario;
import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class VistaAdministrador extends javax.swing.JFrame {

    private Usuario usuario;

    public VistaAdministrador() {
        initComponents();
        this.setTitle("Sistema de ventas");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //colocamos el index al panel pricipal

        PanelAdministrador panel = new PanelAdministrador();

        mostrarPanel(panel);

    }

    public VistaAdministrador(Usuario usuario) {
        initComponents();
        //Usuario que loguea en el sistema
        this.usuario = usuario;
        this.setTitle("Sistema de ventas");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //colocamos el index al panel pricipal      
        PanelAdministrador panel = new PanelAdministrador(usuario);

        mostrarPanel(panel);

    }

    private void mostrarPanel(JPanel panel) {

        //Damos dimension a panel 
        panel.setSize(1480, 817);

        panel.setLocation(0, 0);

        //borramos el contenido actual 
        IndexPanel.removeAll();

        //agreamos el panel que recibimos
        IndexPanel.add(panel, BorderLayout.CENTER);

        //repintamos el panel
        IndexPanel.revalidate();
        IndexPanel.repaint();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        IndexPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu5 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuArticulos = new javax.swing.JMenuItem();
        menuCategorias = new javax.swing.JMenuItem();
        menuProveedores = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        menuClientes = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout IndexPanelLayout = new javax.swing.GroupLayout(IndexPanel);
        IndexPanel.setLayout(IndexPanelLayout);
        IndexPanelLayout.setHorizontalGroup(
            IndexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1480, Short.MAX_VALUE)
        );
        IndexPanelLayout.setVerticalGroup(
            IndexPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 819, Short.MAX_VALUE)
        );

        jMenu5.setText("Inicio");
        jMenu5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu5MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu5);

        jMenu1.setText("Usuarios");

        jMenuItem1.setText("Usuarios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Roles de usuario");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Artículos");

        menuArticulos.setText("Artículos");
        menuArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuArticulosActionPerformed(evt);
            }
        });
        jMenu2.add(menuArticulos);

        menuCategorias.setText("Categorías");
        menuCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCategoriasActionPerformed(evt);
            }
        });
        jMenu2.add(menuCategorias);

        menuProveedores.setText("Proveedores");
        menuProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProveedoresActionPerformed(evt);
            }
        });
        jMenu2.add(menuProveedores);

        jMenuBar1.add(jMenu2);

        jMenu6.setText("Clientes");

        menuClientes.setText("Clientes");
        menuClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClientesActionPerformed(evt);
            }
        });
        jMenu6.add(menuClientes);

        jMenuBar1.add(jMenu6);

        jMenu3.setText("Ventas");

        jMenuItem3.setText("Nueva venta");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");

        jMenuItem4.setText("Ventas realizadas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar1.add(jMenu4);

        jMenu8.setText("Cerrar sesión");
        jMenu8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu8MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(IndexPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(IndexPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        UsuariosPanel panel = new UsuariosPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        RolesPanel panel = new RolesPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void menuArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuArticulosActionPerformed
        ArticulosPanel panel = new ArticulosPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_menuArticulosActionPerformed

    private void menuCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCategoriasActionPerformed
        CategoriasPanel panel = new CategoriasPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_menuCategoriasActionPerformed

    private void menuProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProveedoresActionPerformed
        ProveedoresPanel panel = new ProveedoresPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_menuProveedoresActionPerformed

    private void menuClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClientesActionPerformed
        ClientesPanel panel = new ClientesPanel();
        mostrarPanel(panel);
    }//GEN-LAST:event_menuClientesActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        if (usuario != null) {
            VentaPanel panel = new VentaPanel(usuario);
            mostrarPanel(panel);

        } else {
            VentaPanel panel = new VentaPanel();
            mostrarPanel(panel);

        }

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    public void abrirPanelCliente() {
        ClientesPanel panel = new ClientesPanel();
        mostrarPanel(panel);

    }

    public void abrirPanelUsuario() {

        UsuariosPanel panel = new UsuariosPanel();
        mostrarPanel(panel);

    }

    public void abrirPanelVentas() {

        VentaPanel panel = new VentaPanel();
        mostrarPanel(panel);
    }

    public void abrirPanelReportes() throws SQLException {

        ReportesPanel panel = new ReportesPanel();
        mostrarPanel(panel);
    }

    public void abrirPanelArticulos() throws SQLException {

        ArticulosPanel panel = new ArticulosPanel();
        mostrarPanel(panel);
    }

    public void abrirPanelCategorias() throws SQLException {

        CategoriasPanel panel = new CategoriasPanel();
        mostrarPanel(panel);
    }

    public void abrirPanelProveedores() throws SQLException {

        ProveedoresPanel panel = new ProveedoresPanel();
        mostrarPanel(panel);
    }

    public void abrirPanelRoles() throws SQLException {

        RolesPanel panel = new RolesPanel();
        mostrarPanel(panel);
    }


    private void jMenu8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu8MouseClicked
        // Cerrar la ventana actual
        this.dispose();

        // Crear una nueva instancia de la ventana de login
        Login login = new Login();

        JOptionPane.showMessageDialog(null, "Sesión terminada", "Información", JOptionPane.INFORMATION_MESSAGE);

        // Hacerla visible
        login.setVisible(true);

    }//GEN-LAST:event_jMenu8MouseClicked

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            ReportesPanel panel = new ReportesPanel();
            mostrarPanel(panel);
        } catch (SQLException ex) {
            Logger.getLogger(VistaAdministrador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu5MouseClicked
        PanelAdministrador panel = new PanelAdministrador();
        mostrarPanel(panel);
    }//GEN-LAST:event_jMenu5MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel IndexPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem menuArticulos;
    private javax.swing.JMenuItem menuCategorias;
    private javax.swing.JMenuItem menuClientes;
    private javax.swing.JMenuItem menuProveedores;
    // End of variables declaration//GEN-END:variables
}
