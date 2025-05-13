package Vistas;

import java.io.File;
import java.io.IOException;

import Controladores.ArticuloControlador;
import Controladores.DetalleVentaControlador;
import Controladores.VentaControlador;
import Modelos.Articulo;
import Modelos.Conexion;
import Modelos.DetalleVenta;
import Modelos.Venta;
import java.awt.Desktop;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.sql.Connection;

import net.sf.jasperreports.engine.JasperCompileManager;

public class ReportesPanel extends javax.swing.JPanel {

    private final VentaControlador ventaControlador = new VentaControlador();
    private final DetalleVentaControlador detalleVentaControlador = new DetalleVentaControlador();
    private final ArticuloControlador articuloControlador = new ArticuloControlador();

    int idVentaSeleccionada = 0;

    public ReportesPanel() throws SQLException {
        initComponents();
        llenarTablaVentas();
        seleccionarDatosTablaVentas();
    }

    public void llenarTablaVentas() throws SQLException {
        // Obtener la lista de ventas desde el DAO
        List<Venta> ventas = ventaControlador.obtenerVentasdelDia();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaVentas.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Venta v : ventas) {
            Object[] fila = {
                v.getId(), // IDVenta
                v.getCliente().getNombre(), // Razón Social
                v.getCliente().getNumDocumento(), // NIT/CI
                v.getNumComprobante(), // Num Documento
                v.getFecha(), // Fecha
                v.getTotal(), // Total Venta
                "Detalles" // Opciones (botón o texto estático)
            };

            tableModel.addRow(fila);
        }
        actualizarTotalVentas();
    }
    
    
    
        public void llenarTodaTablaVentas() throws SQLException {
        // Obtener la lista de ventas desde el DAO
        List<Venta> ventas = ventaControlador.obtenerVentasConCliente();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaVentas.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Venta v : ventas) {
            Object[] fila = {
                v.getId(), // IDVenta
                v.getCliente().getNombre(), // Razón Social
                v.getCliente().getNumDocumento(), // NIT/CI
                v.getNumComprobante(), // Num Documento
                v.getFecha(), // Fecha
                v.getTotal(), // Total Venta
                "Detalles" // Opciones (botón o texto estático)
            };

            tableModel.addRow(fila);
        }
        actualizarTotalVentas();
    }

    public void llenarTablaVentasPorRango() throws SQLException {

        Date fechaInicio = fecha1.getDate(); // fecha1 es el JDateChooser para la fecha inicial
        Date fechaFin = fecha2.getDate();   // fecha2 es el JDateChooser para la fecha final

        // Validar que ambas fechas estén seleccionadas
        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Por favor seleccione ambas fechas.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date sqlFechaFin = new java.sql.Date(fechaFin.getTime());

        // Obtener la lista de ventas desde el DAO
        List<Venta> ventas = ventaControlador.obtenerVentasPorRangoFechas(sqlFechaInicio, sqlFechaFin);

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaVentas.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (Venta v : ventas) {
            Object[] fila = {
                v.getId(), // IDVenta
                v.getCliente().getNombre(), // Razón Social
                v.getCliente().getNumDocumento(), // NIT/CI
                v.getNumComprobante(), // Num Documento
                v.getFecha(), // Fecha
                v.getTotal(), // Total Venta
                "Detalles" // Opciones (botón o texto estático)
            };

            tableModel.addRow(fila);
        }
        actualizarTotalVentas();
    }

    public void actualizarTotalVentas() {
        DefaultTableModel model = (DefaultTableModel) tablaVentas.getModel();
        float total = 0;

        // Recorrer todas las filas de la tabla
        for (int i = 0; i < model.getRowCount(); i++) {
            // Obtener el total de cada venta (suponiendo que está en la columna 5)
            total += (float) model.getValueAt(i, 5); // Cambia el índice según la columna correcta
        }

        // Establecer el total en el campo de texto
        txtMontoTotal.setText(String.format("%.2f", total));
    }

    public void seleccionarDatosTablaVentas() {
        tablaVentas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaVentas.getSelectedRow();

                if (filaSeleccionada != -1) {
                    try {
                        // Obtener los valores de las columnas de la fila seleccionada
                        Object idVenta = tablaVentas.getValueAt(filaSeleccionada, 0);
                        Object razonSocial = tablaVentas.getValueAt(filaSeleccionada, 1);
                        Object fecha = tablaVentas.getValueAt(filaSeleccionada, 4);
                        
                        idVentaSeleccionada = (int) idVenta;
                        
                        // Asignar los valores a los campos de texto
                        txtIDventa.setText(String.valueOf(idVenta));
                        txtRazonSocial.setText(String.valueOf(razonSocial));
                        txtFecha.setText(String.valueOf(fecha));
                        
                        llenarTablaDetalles(idVentaSeleccionada);
                        
                        System.out.println("ID Venta: " + idVenta);
                    } catch (SQLException ex) {
                        Logger.getLogger(ReportesPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public void llenarTablaDetalles(int idVenta) throws SQLException {
        // Obtener la lista de detalles de venta desde el DAO
        List<DetalleVenta> detalles = detalleVentaControlador.obtenerDetallesVentaPorId(idVenta);

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaDetalles.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Llenar la tabla con los datos de la lista
        for (DetalleVenta detalle : detalles) {

            Articulo articulo = new ArticuloControlador().obtenerArticuloPorId(detalle.getIdArticulo());

            Object[] fila = {
                articulo.getNombre(),
                detalle.getCantidad(), // Cantidad
                detalle.getPrecio() // Precio unitario
            };

            tableModel.addRow(fila);
        }
    }

    public void generarPdf(int idVenta) {
        Connection conexion = Conexion.getConnection();

        try {
            // Rutas de los archivos
            String jrxmlFilePath = "C:\\Users\\ALAN\\JaspersoftWorkspace\\MyReports\\Invoice.jrxml";
            String jasperFilePath = "C:\\Users\\ALAN\\JaspersoftWorkspace\\MyReports\\Reportes\\MiReporte.jasper";

            // Compilar el archivo .jrxml a .jasper si no está compilado
            JasperCompileManager.compileReportToFile(jrxmlFilePath, jasperFilePath);
            System.out.println("Reporte compilado exitosamente!");

            // Crear parámetros para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("venta_id", idVenta); // Valor del parámetro

            // Llenar el reporte con datos de la base de datos
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilePath, parameters, conexion);

            // Exportar el reporte a PDF
            String pdfFilePath = "C:\\Users\\ALAN\\JaspersoftWorkspace\\MyReports\\Reportes\\reporte.pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, pdfFilePath);
            System.out.println("PDF generado exitosamente en: " + pdfFilePath);

            // Abrir el PDF automáticamente
            abrirPdf(pdfFilePath);

        } catch (JRException e) {
            System.out.println("Error al generar el reporte con JasperReports.");
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            if (conexion != null) {
                try {
                    conexion.close();
                    System.out.println("Conexión a la base de datos cerrada.");
                } catch (SQLException e) {
                    System.out.println("Error al cerrar la conexión a la base de datos.");
                    e.printStackTrace();
                }
            }
        }
    }

    public static void abrirPdf(String archivo) {
        try {
            // Intentar abrir el PDF dependiendo del sistema operativo
            File pdf = new File(archivo);
            if (pdf.exists()) {
                // Windows
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Desktop.getDesktop().open(pdf);
                } // MacOS
                else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec("open " + archivo);
                } // Linux
                else if (System.getProperty("os.name").toLowerCase().contains("nix") || System.getProperty("os.name").toLowerCase().contains("nux")) {
                    Runtime.getRuntime().exec("xdg-open " + archivo);
                }
            } else {
                System.out.println("El archivo PDF no existe.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaVentas = new javax.swing.JTable();
        fecha1 = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fecha2 = new com.toedter.calendar.JDateChooser();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtIDventa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtMontoTotal = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Reporte de ventas:");

        tablaVentas.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Razon Social", "NIT/CI", "Num Comprobante", "Fecha y Hora", "Total Venta", "Opciones"
            }
        ));
        tablaVentas.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaVentas.setRowHeight(35);
        jScrollPane1.setViewportView(tablaVentas);
        if (tablaVentas.getColumnModel().getColumnCount() > 0) {
            tablaVentas.getColumnModel().getColumn(3).setHeaderValue("Num Comprobante");
            tablaVentas.getColumnModel().getColumn(4).setHeaderValue("Fecha y Hora");
            tablaVentas.getColumnModel().getColumn(5).setHeaderValue("Total Venta");
            tablaVentas.getColumnModel().getColumn(6).setHeaderValue("Opciones");
        }

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel3.setText("Reporte de ventas:");

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel4.setText("Reporte de ventas:");

        jButton3.setBackground(new java.awt.Color(40, 167, 69));
        jButton3.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Filtrar ventas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(23, 162, 184));
        jButton4.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Todas las ventas");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setText("ID :");

        txtIDventa.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Rázon social:");

        txtRazonSocial.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtFecha.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Fecha:");

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel8.setText("Detalles");

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel9.setText("Monto total de ventas:");

        txtMontoTotal.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jButton5.setBackground(new java.awt.Color(0, 123, 255));
        jButton5.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Ver detalles");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tablaDetalles.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre Articulo", "Cantidad", "Precio Unitario"
            }
        ));
        tablaDetalles.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaDetalles.setRowHeight(35);
        jScrollPane2.setViewportView(tablaDetalles);

        jButton9.setBackground(new java.awt.Color(108, 117, 125));
        jButton9.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Ver PDF");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtIDventa, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))))
                            .addGap(37, 37, 37)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(32, 32, 32)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE))
                            .addGap(944, 944, 944)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(320, 320, 320)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(993, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtIDventa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(29, 29, 29)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(43, 43, 43)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(740, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            llenarTablaVentasPorRango();
        } catch (SQLException ex) {
            Logger.getLogger(ReportesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            llenarTodaTablaVentas();
        } catch (SQLException ex) {
            Logger.getLogger(ReportesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        if (idVentaSeleccionada != 0) {
            try {
                llenarTablaDetalles(idVentaSeleccionada);
            } catch (SQLException ex) {
                Logger.getLogger(ReportesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una venta ", "Advertencia", JOptionPane.WARNING_MESSAGE);

        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        final int idVenta = Integer.parseInt(txtIDventa.getText());
        generarPdf(idVenta);
    }//GEN-LAST:event_jButton9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser fecha1;
    private com.toedter.calendar.JDateChooser fecha2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaVentas;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtIDventa;
    private javax.swing.JTextField txtMontoTotal;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables
}
