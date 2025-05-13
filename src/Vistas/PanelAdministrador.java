package Vistas;

import Modelos.Usuario;
import java.awt.Color;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author ALAN
 */
public class PanelAdministrador extends javax.swing.JPanel {

    private Usuario usuario;

    public PanelAdministrador() {
        initComponents();
        lblMensajeBienvenida.setText("Usuario conectado");
        llamarIconos();
        aplicarHoverATodos();

    }

    public PanelAdministrador(Usuario usuario) {
        initComponents();
        this.usuario = usuario;
        mensajeBienvenida();
        llamarIconos();
        aplicarHoverATodos();

    }

    public void mensajeBienvenida() {

        lblMensajeBienvenida.setText("Usuario administrador : " + usuario.getNombre());

    }

    public void llamarIconos() {

        colocarIconos(IconoUsuarios, "/icons/usuario2.png");
        colocarIconos(IconoClientes, "/icons/clientes.png");
        colocarIconos(IconoVentas, "/icons/ventas.png");
        colocarIconos(IconoReportes, "/icons/analitica.png");
        colocarIconos(IconoArticulos, "/icons/articulos.png");
        colocarIconos(IconoCategorias, "/icons/categorias.png");
        colocarIconos(IconoProveedores, "/icons/inventario.png");
        colocarIconos(IconoRoles, "/icons/roles.png");

        iniciarReloj();
    }

    public void colocarIconos(JLabel label, String ruta) {
        IconoUsuarios.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                try {
                    // Cargar la imagen
                    ImageIcon imagenOriginal = new ImageIcon(getClass().getResource(ruta));

                    // Redimensionar la imagen al tamaño actual del JLabel
                    Image imagenRedimensionada = imagenOriginal.getImage().getScaledInstance(
                            label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH
                    );

                    // Asignar la imagen al JLabel
                    label.setIcon(new ImageIcon(imagenRedimensionada));
                } catch (Exception ex) {
                    System.err.println("Error al cargar la imagen: " + ex.getMessage());
                }
            }
        });
    }

    public void iniciarReloj() {
        // Formato para la fecha y hora
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Actualizar el JLabel inmediatamente con la hora actual
        lblFechaHora.setText(LocalDateTime.now().format(formato));

        // Timer que se ejecuta cada segundo
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fecha y hora local
                LocalDateTime ahora = LocalDateTime.now();
                String fechaHoraActual = ahora.format(formato);

                // Actualizar el JLabel
                lblFechaHora.setText(fechaHoraActual);
            }
        });

        timer.start(); // Iniciar el timer
    }

    
        public void aplicarHoverATodos() {
        
        aplicarEfectoHover(IconoClientes);
        aplicarEfectoHover(IconoArticulos);
        aplicarEfectoHover(IconoClientes);
        aplicarEfectoHover(IconoProveedores);
        aplicarEfectoHover(IconoReportes);
        aplicarEfectoHover(IconoVentas);
        aplicarEfectoHover(IconoCategorias);
        aplicarEfectoHover(IconoRoles);
        aplicarEfectoHover(IconoUsuarios);
        

    }

    public void aplicarEfectoHover(JLabel label) {
        // Colores predeterminados
        Color originalBackground = new Color(60, 63, 65); // Fondo inicial
        Color hoverBackground = new Color(80, 80, 80); // Fondo al hacer hover
        Color hoverForeground = Color.WHITE; // Texto al hacer hover

        // Configuración inicial
        label.setOpaque(true);
        label.setBackground(originalBackground);

        // Agregar eventos de mouse
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(hoverBackground);
                label.setForeground(hoverForeground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(originalBackground);
            }
        });
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMensajeBienvenida = new javax.swing.JLabel();
        IconoClientes = new javax.swing.JLabel();
        IconoReportes = new javax.swing.JLabel();
        IconoUsuarios = new javax.swing.JLabel();
        lblMensajeBienvenida1 = new javax.swing.JLabel();
        lblMensajeBienvenida4 = new javax.swing.JLabel();
        lblMensajeBienvenida5 = new javax.swing.JLabel();
        lblna = new javax.swing.JLabel();
        IconoVentas = new javax.swing.JLabel();
        IconoArticulos = new javax.swing.JLabel();
        lblMensajeBienvenida2 = new javax.swing.JLabel();
        IconoCategorias = new javax.swing.JLabel();
        lblMensajeBienvenida6 = new javax.swing.JLabel();
        IconoProveedores = new javax.swing.JLabel();
        Proveedores = new javax.swing.JLabel();
        IconoRoles = new javax.swing.JLabel();
        lblMensajeBienvenida7 = new javax.swing.JLabel();
        lblFechaHora = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1480, 817));

        lblMensajeBienvenida.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N

        IconoClientes.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoClientesMouseClicked(evt);
            }
        });

        IconoReportes.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoReportesMouseClicked(evt);
            }
        });

        IconoUsuarios.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoUsuariosMouseClicked(evt);
            }
        });

        lblMensajeBienvenida1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida1.setText("Usuarios");

        lblMensajeBienvenida4.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida4.setText("Clientes");

        lblMensajeBienvenida5.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida5.setText("Reportes");

        lblna.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblna.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblna.setText("Ventas");

        IconoVentas.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoVentasMouseClicked(evt);
            }
        });

        IconoArticulos.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoArticulos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoArticulosMouseClicked(evt);
            }
        });

        lblMensajeBienvenida2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida2.setText("Artículos");

        IconoCategorias.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoCategorias.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoCategoriasMouseClicked(evt);
            }
        });

        lblMensajeBienvenida6.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida6.setText("Categorias");

        IconoProveedores.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoProveedores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoProveedoresMouseClicked(evt);
            }
        });

        Proveedores.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        Proveedores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Proveedores.setText("Proveedores");

        IconoRoles.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        IconoRoles.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        IconoRoles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconoRolesMouseClicked(evt);
            }
        });

        lblMensajeBienvenida7.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        lblMensajeBienvenida7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensajeBienvenida7.setText("Roles de Usuario");

        lblFechaHora.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblMensajeBienvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMensajeBienvenida1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IconoUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblMensajeBienvenida2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(IconoArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IconoCategorias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMensajeBienvenida6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IconoProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IconoClientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMensajeBienvenida4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(119, 119, 119)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(IconoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblna, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(130, 130, 130)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(IconoReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMensajeBienvenida5, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(IconoRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMensajeBienvenida7, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(109, 109, 109))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMensajeBienvenida, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(IconoUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMensajeBienvenida1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFechaHora, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IconoVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblna, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IconoReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMensajeBienvenida5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(IconoClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMensajeBienvenida4)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(IconoCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblMensajeBienvenida6))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(IconoArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblMensajeBienvenida2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(IconoProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(Proveedores, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(IconoRoles, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMensajeBienvenida7)))
                .addGap(86, 86, 86))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void IconoUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoUsuariosMouseClicked

        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            ((VistaAdministrador) ventanaPadre).abrirPanelUsuario();
        }

    }//GEN-LAST:event_IconoUsuariosMouseClicked

    private void IconoClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoClientesMouseClicked

        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            ((VistaAdministrador) ventanaPadre).abrirPanelCliente();
        }
    }//GEN-LAST:event_IconoClientesMouseClicked

    private void IconoVentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoVentasMouseClicked
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            ((VistaAdministrador) ventanaPadre).abrirPanelVentas();
        }
    }//GEN-LAST:event_IconoVentasMouseClicked

    private void IconoReportesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoReportesMouseClicked
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            try {
                ((VistaAdministrador) ventanaPadre).abrirPanelReportes();
            } catch (SQLException ex) {
                Logger.getLogger(PanelAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_IconoReportesMouseClicked

    private void IconoArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoArticulosMouseClicked

        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            try {
                ((VistaAdministrador) ventanaPadre).abrirPanelArticulos();
            } catch (SQLException ex) {
                Logger.getLogger(PanelAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_IconoArticulosMouseClicked

    private void IconoCategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoCategoriasMouseClicked

        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            try {
                ((VistaAdministrador) ventanaPadre).abrirPanelCategorias();
            } catch (SQLException ex) {
                Logger.getLogger(PanelAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_IconoCategoriasMouseClicked

    private void IconoProveedoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoProveedoresMouseClicked
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            try {
                ((VistaAdministrador) ventanaPadre).abrirPanelProveedores();
            } catch (SQLException ex) {
                Logger.getLogger(PanelAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_IconoProveedoresMouseClicked

    private void IconoRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconoRolesMouseClicked
       
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            try {
                ((VistaAdministrador) ventanaPadre).abrirPanelRoles();
            } catch (SQLException ex) {
                Logger.getLogger(PanelAdministrador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_IconoRolesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IconoArticulos;
    private javax.swing.JLabel IconoCategorias;
    private javax.swing.JLabel IconoClientes;
    private javax.swing.JLabel IconoProveedores;
    private javax.swing.JLabel IconoReportes;
    private javax.swing.JLabel IconoRoles;
    private javax.swing.JLabel IconoUsuarios;
    private javax.swing.JLabel IconoVentas;
    private javax.swing.JLabel Proveedores;
    private javax.swing.JLabel lblFechaHora;
    private javax.swing.JLabel lblMensajeBienvenida;
    private javax.swing.JLabel lblMensajeBienvenida1;
    private javax.swing.JLabel lblMensajeBienvenida2;
    private javax.swing.JLabel lblMensajeBienvenida4;
    private javax.swing.JLabel lblMensajeBienvenida5;
    private javax.swing.JLabel lblMensajeBienvenida6;
    private javax.swing.JLabel lblMensajeBienvenida7;
    private javax.swing.JLabel lblna;
    // End of variables declaration//GEN-END:variables
}
