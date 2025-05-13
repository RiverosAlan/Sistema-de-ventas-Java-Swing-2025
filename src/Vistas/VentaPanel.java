package Vistas;

import Controladores.ArticuloControlador;
import Controladores.ClienteControlador;
import Controladores.DetalleVentaControlador;
import Controladores.VentaControlador;
import Modelos.Articulo;
import Modelos.Cliente;
import Modelos.DetalleVenta;
import Modelos.Usuario;
import Modelos.Venta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class VentaPanel extends javax.swing.JPanel {

    private final ClienteControlador clienteControlador = new ClienteControlador();
    private final ArticuloControlador articuloControlador = new ArticuloControlador();
    private final VentaControlador ventaControlador = new VentaControlador();
    private final DetalleVentaControlador detalleVentaControlador = new DetalleVentaControlador();

    private Usuario usuario;
    private Cliente cliente;
    private Articulo articulo;

    List<Articulo> listaArticulos = new ArrayList<>();

    public VentaPanel() {
        initComponents();
        mostraHorayFecha();
        //metodos clientes
        llenarComboBoxConClientes();
        seleccionarCliente();
        //metodos articulos
        llenarComboBoxConArticulos();
        seleccionarArticulo();
        eliminarArticuloTabla();
        restablecerCmbArticulos();
        restablecerCmbClientes();
        mostrarNumeroVenta();
    }

    public VentaPanel(Usuario usuario) {
        initComponents();
        this.usuario = usuario;
        mostraHorayFecha();
        lblUsuario.setText("Usuario conectado : " + usuario.getNombre());
        //metodo clientes
        llenarComboBoxConClientes();
        seleccionarCliente();
        //metodos articulos
        llenarComboBoxConArticulos();
        seleccionarArticulo();

        eliminarArticuloTabla();
        restablecerCmbArticulos();
        restablecerCmbClientes();
        mostrarNumeroVenta();
    }

    public void mostraHorayFecha() {
        // Formateador para la fecha y hora
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Actualización inicial inmediata
        LocalDateTime now = LocalDateTime.now();
        txtDireccion2.setText(now.format(formatter)); // Actualiza el JTextField con la fecha y hora actuales

        // Método para actualizar el JTextField con la hora y fecha local cada segundo
        ActionListener updateDateTime = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener la fecha y hora actuales
                LocalDateTime now = LocalDateTime.now();
                // Formatear la fecha y hora
                String formattedDateTime = now.format(formatter);
                // Actualizar el JTextField
                txtDireccion2.setText(formattedDateTime);
            }
        };

        // Configurar un Timer para actualizar cada segundo
        Timer timer = new Timer(1000, updateDateTime);
        timer.start();
    }

    private void actualizarComboBoxClientes(List<Cliente> clientes) {
        // Limpiar el JComboBox antes de llenarlo
        cmbClientes.removeAllItems();

        // Agregar un elemento predeterminado
        cmbClientes.addItem("Seleccione un cliente");

        // Llenar el JComboBox con los clientes encontrados
        for (Cliente cliente : clientes) {
            cmbClientes.addItem(cliente.getNombre()); // Suponiendo que el nombre es relevante
        }
    }

    public void llenarComboBoxConClientes() {

        // Obtener todos los clientes desde el DAO
        List<Cliente> clientes = clienteControlador.obtenerTodosLosClientes();

        // Limpiar el JComboBox antes de llenarlo
        cmbClientes.removeAllItems();

        // Agregar un elemento predeterminado (opcional)
        cmbClientes.addItem("Seleccione un cliente");

        // Llenar el JComboBox con los nombres de los clientes (o cualquier campo relevante)
        for (Cliente cliente : clientes) {
            cmbClientes.addItem(cliente.getNombre()); // Suponiendo que `getNombre()` obtiene el nombre del cliente
        }
    }

    public void seleccionarCliente() {
        // Suponiendo que tienes un JComboBox llamado cmbClientes
        cmbClientes.addActionListener(e -> {
            // Obtener el nombre seleccionado en el combo box
            String nombreSeleccionado = (String) cmbClientes.getSelectedItem();

            // Verificar que no sea el valor predeterminado o nulo
            if (nombreSeleccionado != null && !nombreSeleccionado.equals("Seleccione un cliente")) {
                // Llamar al método del controlador para obtener el cliente
                cliente = clienteControlador.buscarClientePorNombre(nombreSeleccionado);

                if (cliente != null) {

                    txtNit.setText(cliente.getNumDocumento());
                    System.out.println(cliente.getId());

                } else {
                    JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
                }
            }
        });

    }

    public void llenarComboBoxConArticulos() {
        // Obtener todos los artículos desde el controlador
        List<Articulo> articulos = articuloControlador.obtenerTodosLosArticulos();

        // Limpiar el JComboBox antes de llenarlo
        cmbArticulos.removeAllItems();

        // Agregar un elemento predeterminado (opcional)
        cmbArticulos.addItem("Seleccione un artículo");

        // Llenar el JComboBox solo con los artículos cuyos ID no estén en listaArticulos
        for (Articulo art : articulos) {
            boolean estaEnLista = listaArticulos.stream().anyMatch(a -> a.getId() == art.getId());
            System.out.println(estaEnLista);
            if (!estaEnLista) {
                cmbArticulos.addItem(art.getNombre());
            }
        }
    }

    public void seleccionarArticulo() {
        // Suponiendo que tienes un JComboBox llamado cmbArticulos
        cmbArticulos.addActionListener(e -> {
            // Obtener el nombre seleccionado en el combo box
            String nombreSeleccionado = (String) cmbArticulos.getSelectedItem();

            // Verificar que no sea el valor predeterminado o nulo
            if (nombreSeleccionado != null && !nombreSeleccionado.equals("Seleccione un artículo")) {

                // Llamar al método del controlador para obtener el artículo
                articulo = articuloControlador.buscarArticuloPorNombreExacto(nombreSeleccionado);

                if (articulo != null) {
                    txtStock.setText(String.valueOf(articulo.getStock()));
                    txtPrecio.setText(String.valueOf(articulo.getPrecioVenta()));
                    txtDescripcionArticulo.setText(articulo.getDescripcion());
                    System.out.println(articulo.getId());
                } else {
                    JOptionPane.showMessageDialog(null, "Artículo no encontrado.");
                }
            }
        });

    }

    private void actualizarComboBoxArticulos(List<Articulo> articulos) {
        // Limpiar el JComboBox antes de llenarlo
        cmbArticulos.removeAllItems();

        // Agregar un elemento predeterminado
        cmbArticulos.addItem("Seleccione un artículo");

        // Llenar el JComboBox con los artículos encontrados
        for (Articulo art : articulos) {
            cmbArticulos.addItem(art.getNombre()); // Suponiendo que `getNombre()` obtiene el nombre del artículo
        }

        for (Articulo art : listaArticulos) {
            System.out.println(art);
        }

    }

    private void agregarDetalleVenta() {

        if (articulo == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un artículo");
            return;
        }

        if (articulo.getStock() < 1) {
            JOptionPane.showMessageDialog(null, "No hay articulos disponibles");
            return;
        }

        // Obtener los datos del formulario
        String codigoArticulo = String.valueOf(articulo.getId());
        String nombreArticulo = articulo.getNombre();

        int cantidad;
        double precioUnitario;

        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
            precioUnitario = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese valores válidos para cantidad y precio.");
            return;
        }

        int stockIndicado = Integer.valueOf(txtCantidad.getText());
        int stockEnDB = Integer.valueOf(txtStock.getText());
        
        
        if(stockIndicado> stockEnDB){
            JOptionPane.showMessageDialog(null, "Stock del articulo insuficiente");
            return;
        }
        

        // Calcular el subtotal
        double subtotal = cantidad * precioUnitario;

        // Obtener el modelo de la tabla
        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();

        // Obtener el número de la secuencia (número de fila actual + 1)
        int num = model.getRowCount() + 1;

        // Agregar la fila a la tabla
        model.addRow(new Object[]{
            num,
            codigoArticulo,
            nombreArticulo,
            cantidad,
            precioUnitario,
            subtotal,
            "Eliminar" // Esto puede ser un botón o un texto para eliminar
        });

        listaArticulos.add(articulo);

        ajustarTamanosColumnas();
        calcularTotal();

        // Limpiar los campos del formulario después de agregar
        limpiarDatosArticulos();
    }

    public void eliminarArticuloTabla() {
        tblDetalleVenta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblDetalleVenta.rowAtPoint(e.getPoint());
                int column = tblDetalleVenta.columnAtPoint(e.getPoint());

                // Verifica si el clic fue en la columna de "Eliminar"
                if (column == 6) { // La columna de "Eliminar"
                    // Verifica que la fila sea válida
                    if (row >= 0) {
                        // Obtener el modelo de la tabla
                        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();

                        // Obtener el id de la fila seleccionada (suponiendo que está en la columna 0)
                        Object id = model.getValueAt(row, 1);

                        // Convierte el id a String o al tipo que necesites
                        int idInt = id instanceof String ? Integer.parseInt((String) id) : (int) id;

                        // Verificar y obtener los valores de las columnas
                        try {

                            // Confirmar eliminación
                            int confirm = JOptionPane.showConfirmDialog(
                                    null,
                                    "¿Estás seguro de que deseas eliminar estos artículos?",
                                    "Confirmar eliminación",
                                    JOptionPane.YES_NO_OPTION
                            );

                            if (confirm == JOptionPane.YES_OPTION) {
                                // Eliminar la fila seleccionada
                                model.removeRow(row);
                                calcularTotal();
                                limpiarDatosArticulos();
                                eliminarArticuloPorId(listaArticulos, idInt);
                                llenarComboBoxConArticulos();

                                // Renumerar la primera columna
                                for (int i = 0; i < model.getRowCount(); i++) {
                                    model.setValueAt(i + 1, i, 0); // Asignar el nuevo número (i + 1) en la columna 0
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Error al obtener los datos de la fila. Verifica el contenido de las columnas.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void calcularTotal() {
        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();
        double total = 0.0;

        // Iterar sobre las filas para sumar los valores de la columna 5
        for (int i = 0; i < model.getRowCount(); i++) {
            Object value = model.getValueAt(i, 5); // Columna 5
            if (value != null) {
                try {
                    total += Double.parseDouble(value.toString());
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir el valor en la fila " + i + " columna 5 a número.");
                }
            }
        }

        // Actualizar el campo de texto con el total calculado
        txtTotalVenta.setText(String.format("%.2f", total));
    }

    public void limpiarDatosArticulos() {

        txtPrecio.setText("");
        txtCantidad.setText("");
        txtCantidad.setText("");
        cmbArticulos.setSelectedIndex(0);
        txtDescripcionArticulo.setText("");
        txtStock.setText("");

        //limpiamos el objeto articulo para que seleccione otro
        articulo = null;

    }

    private void restablecerCmbArticulos() {
        txtArticulo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)
                        && txtArticulo.getText().isEmpty()) {

                    llenarComboBoxConArticulos();

                }
            }
        });
    }

    private void restablecerCmbClientes() {
        txtCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_BACK_SPACE || e.getKeyCode() == KeyEvent.VK_DELETE)
                        && txtCliente.getText().isEmpty()) {

                    llenarComboBoxConClientes();
                }
            }
        });
    }

    private void ajustarTamanosColumnas() {
        // Obtener el modelo de columnas
        TableColumnModel columnModel = tblDetalleVenta.getColumnModel();

        // Configurar tamaños de columnas
        columnModel.getColumn(0).setPreferredWidth(30);  // Número
        columnModel.getColumn(1).setPreferredWidth(80); // Código del artículo
        columnModel.getColumn(2).setPreferredWidth(250); // Nombre del artículo
        columnModel.getColumn(3).setPreferredWidth(80);  // Cantidad
        columnModel.getColumn(4).setPreferredWidth(100); // Precio unitario
        columnModel.getColumn(5).setPreferredWidth(120); // Subtotal
        columnModel.getColumn(6).setPreferredWidth(80);  // Eliminar
    }

    public void reducirStock(int idArticulo, int cantidad) {
        articuloControlador.reducirStock(idArticulo, cantidad);
    }

    public void devolverStock(int idArticulo, int cantidad) {
        articuloControlador.incrementarStock(idArticulo, cantidad);
    }

    public void mostrarNumeroVenta() {
        int numeroVenta = ventaControlador.obtenerNumeroDeVentas() + 1;
        txtNumVentas.setText(String.valueOf(numeroVenta));

    }

    public void limpiarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public void limpiarCamposCliente() {
        cmbClientes.setSelectedIndex(0);
        txtCliente.setText("");
        txtNit.setText("");
        cliente = null;
    }

    private void eliminarElementoDeComboBoxPorNombre(JComboBox<String> cmbArticulos, List<Articulo> listaArticulos) {
        // Iterar sobre los elementos del JComboBox
        for (int i = 0; i < cmbArticulos.getItemCount(); i++) {
            String nombreArticulo = cmbArticulos.getItemAt(i);

            // Buscar el artículo correspondiente en la lista de artículos
            for (Articulo art : listaArticulos) {
                if (art.getNombre().equals(nombreArticulo)) {
                    // Eliminar el elemento del JComboBox
                    cmbArticulos.removeItemAt(i);
                    // Eliminar el artículo de la lista
                    listaArticulos.remove(articulo);
                    break; // Salir del bucle cuando se encuentra el artículo
                }
            }
        }
    }

    public static void eliminarArticuloPorId(List<Articulo> lista, int id) {
        lista.removeIf(articulo -> articulo.getId() == id);
    }

    public void registrarVenta() {

        if (cliente == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblDetalleVenta.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No selecciono articulos");
            return;
        }

        try {
            // Obtener los valores desde los campos de texto
            int idCliente = cliente.getId();
            int idUsuario = usuario.getId();

            String tipoComprobante = cmbTipoComprobatne.getSelectedItem().toString();

            String numComprobante = String.valueOf(ventaControlador.obtenerNumeroDeVentas());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String fecha = LocalDateTime.now().format(formatter);

            String totalStr = txtTotalVenta.getText().trim().replace(",", ".");
            float total = Float.parseFloat(totalStr);

            String estado = "1";

            // Crear un objeto Venta con los datos obtenidos
            Venta venta = new Venta();
            venta.setIdCliente(idCliente);
            venta.setIdUsuario(idUsuario);
            venta.setTipoComprobante(tipoComprobante);
            venta.setNumComprobante(numComprobante);
            venta.setFecha(fecha);
            venta.setTotal(total);
            venta.setEstado(estado);

            // Llamar al método agregarVenta en el controlador
            boolean ventaRegistrada = ventaControlador.agregarVenta(venta); // Método para agregar la venta

            if (!ventaRegistrada) {
                JOptionPane.showMessageDialog(null, "Error al registrar la venta");
                return;
            }

            // Obtener el último ID de venta insertado
            int idUtimaVenta = ventaControlador.obtenerUltimoIdVenta();
            System.out.println("idUtimaVenta = " + idUtimaVenta);

            // Ahora insertar el detalle de la venta
            // Obtener los valores de la tabla de manera segura
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    // Convertir id_articulo a Integer
                    String idArticuloStr = model.getValueAt(i, 1).toString();
                    int idArticulo = Integer.parseInt(idArticuloStr.trim());

                    // Convertir cantidad a Integer
                    String cantidadStr = model.getValueAt(i, 3).toString();
                    int cantidad = Integer.parseInt(cantidadStr.trim());

                    //reducimos stock
                    reducirStock(idArticulo, cantidad);

                    // Convertir precio a Float
                    String precioStr = model.getValueAt(i, 4).toString();
                    float precio = Float.parseFloat(precioStr.trim());

                    // Convertir descuento a Float
                    String descuentoStr = model.getValueAt(i, 5).toString();
                    float descuento = Float.parseFloat(descuentoStr.trim());

                    DetalleVenta detalleVenta = new DetalleVenta();
                    // Crear un objeto DetalleVenta
                    detalleVenta.setIdVenta(idUtimaVenta); // Utilizando el último ID de venta
                    detalleVenta.setIdArticulo(idArticulo);
                    detalleVenta.setCantidad(cantidad);
                    detalleVenta.setPrecio(precio);
                    detalleVenta.setDescuento(descuento);

                    // Llamar al método para insertar el detalle de la venta
                    if (!detalleVentaControlador.agregarDetalleVenta(detalleVenta)) {
                        JOptionPane.showMessageDialog(null, "Error al registrar el detalle de la venta");
                        return;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Error al convertir los datos en la fila " + (i + 1), "Error de formato", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            JOptionPane.showMessageDialog(null, "Venta registrada con éxito");
            limpiarTabla();
            limpiarCamposCliente();
            limpiarDatosArticulos();
            listaArticulos.clear();
            llenarComboBoxConArticulos();
            mostrarNumeroVenta();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa los valores correctamente", "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar la venta", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalleVenta = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        txtTotalVenta = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cmbTipoComprobatne = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNumVentas = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        txtDireccion2 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cmbArticulos = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtDescripcionArticulo = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtArticulo = new javax.swing.JTextField();

        setPreferredSize(new java.awt.Dimension(1480, 817));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("Realizar venta :");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 16, 190, 69));

        tblDetalleVenta.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tblDetalleVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num", "Codigo Artículo", "Nombre Artículo", "Cantidad", "Precio Unitario", "Sub total", "Opciones"
            }
        ));
        tblDetalleVenta.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDetalleVenta.setRowHeight(35);
        jScrollPane1.setViewportView(tblDetalleVenta);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 850, 439));

        jLabel11.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel11.setText("Total:");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 570, 127, 40));

        txtTotalVenta.setEditable(false);
        txtTotalVenta.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        add(txtTotalVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 580, 280, 30));

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel12.setText("Tipo de Pago:");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 640, 127, 40));

        cmbTipoComprobatne.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta", "Pago QR" }));
        add(cmbTipoComprobatne, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 640, 276, 42));

        jButton1.setBackground(new java.awt.Color(40, 167, 69));
        jButton1.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Realizar venta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 710, 144, 42));

        jButton3.setBackground(new java.awt.Color(220, 53, 69));
        jButton3.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1310, 710, 144, 42));

        lblUsuario.setFont(new java.awt.Font("Roboto Medium", 0, 20)); // NOI18N
        add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(1023, 16, 391, 69));

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Número de venta :");

        txtNumVentas.setEditable(false);
        txtNumVentas.setFont(new java.awt.Font("Roboto Light", 0, 16)); // NOI18N

        jButton4.setBackground(new java.awt.Color(0, 123, 255));
        jButton4.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Registrar nuevo cliente");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel13.setText("Buscar Cliente");

        txtCliente.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClienteKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel4.setText("Cliente:");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("NIT / CI :");

        txtNit.setEditable(false);
        txtNit.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtDireccion2.setEditable(false);
        txtDireccion2.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel8.setText("Fecha - Hora :");

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setText("Articulos:");

        cmbArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbArticulosMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel9.setText("Precio:");

        jLabel14.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel14.setText("Descripcion:");

        txtDescripcionArticulo.setEditable(false);
        txtDescripcionArticulo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtPrecio.setEditable(false);
        txtPrecio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtStock.setEditable(false);
        txtStock.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        txtCantidad.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jButton5.setBackground(new java.awt.Color(108, 117, 125));
        jButton5.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Limpiar Datos");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(23, 162, 184));
        jButton6.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Agregar Articulo");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel15.setText("Stock:");

        jLabel16.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel16.setText("cantidad:");

        jLabel17.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel17.setText("Buscar Articulo:");

        txtArticulo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N
        txtArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtArticuloKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtNumVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(146, 146, 146)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(cmbArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtNumVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(txtDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 97, 568, 630));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        // Obtener el JFrame contenedor
        JFrame ventanaPadre = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Verificar el tipo de ventana y actuar en consecuencia
        if (ventanaPadre instanceof VistaAdministrador) {
            ((VistaAdministrador) ventanaPadre).abrirPanelCliente();
        } else if (ventanaPadre instanceof VistaVendedor) {
            ((VistaVendedor) ventanaPadre).abrirPanelCliente();
        }


    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "¿Estás seguro de que deseas registrar la venta?",
                "Confirmar Venta",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Si el usuario confirma, registra la venta
            registrarVenta();
        } else {
            // Si el usuario no confirma, puedes hacer algo opcional, como mostrar un mensaje
            JOptionPane.showMessageDialog(null, "La venta no fue registrada");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limpiarCamposCliente();
        limpiarDatosArticulos();
        limpiarTabla();
        listaArticulos.clear();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        limpiarCamposCliente();
        limpiarDatosArticulos();
        limpiarTabla();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        agregarDetalleVenta();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteKeyTyped
        String nombreCliente = txtCliente.getText().trim();

        // Verificar que no esté vacío antes de buscar
        if (!nombreCliente.isEmpty()) {
            // Buscar clientes por el texto ingresado
            List<Cliente> clientes = clienteControlador.obtenerClientesPorNombre(nombreCliente);

            // Actualizar el JComboBox
            actualizarComboBoxClientes(clientes);
        }

    }//GEN-LAST:event_txtClienteKeyTyped

    private void txtArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtArticuloKeyTyped

        String nombreArticulo = txtArticulo.getText().trim();

        // Verificar que no esté vacío antes de buscar
        if (!nombreArticulo.isEmpty()) {
            // Buscar artículos por el texto ingresado
            List<Articulo> articulos = articuloControlador.buscarArticulosPorNombre(nombreArticulo);

            // Actualizar el JComboBox
            actualizarComboBoxArticulos(articulos);

        }


    }//GEN-LAST:event_txtArticuloKeyTyped

    private void cmbArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbArticulosMouseClicked
        eliminarElementoDeComboBoxPorNombre(cmbArticulos, listaArticulos);
    }//GEN-LAST:event_cmbArticulosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbArticulos;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JComboBox<String> cmbTipoComprobatne;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblDetalleVenta;
    private javax.swing.JTextField txtArticulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDescripcionArticulo;
    private javax.swing.JTextField txtDireccion2;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtNumVentas;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTotalVenta;
    // End of variables declaration//GEN-END:variables
}
