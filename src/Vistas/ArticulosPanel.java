package Vistas;

import Controladores.ArticuloControlador;
import Controladores.CategoriaControlador;
import Controladores.ProveedorControlador;
import Modelos.Articulo;
import Modelos.Categoria;
import Modelos.Proveedor;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public final class ArticulosPanel extends javax.swing.JPanel {

    private final ArticuloControlador articuloControlador = new ArticuloControlador();
    private final CategoriaControlador categoriaControlador = new CategoriaControlador();
    private final ProveedorControlador proveedorControlador = new ProveedorControlador();

    private int idArticulo = 0;
    private File imagenSeleccionada;

    public ArticulosPanel() {
        initComponents();
        llenarTablaArticulos();
        llenarComboBoxConCategorias();
        llenarComboBoxConProveedores();
        seleccionarDatosTabla();
    }

    public void llenarTablaArticulos() {

        // Obtener la lista de artículos desde el controlador
        List<Articulo> articulos = articuloControlador.obtenerTodosLosArticulos();

        // Obtener el modelo de la tabla
        DefaultTableModel tableModel = (DefaultTableModel) tablaArticulos.getModel();

        // Limpiar la tabla antes de cargar nuevos datos
        tableModel.setRowCount(0);

        // Agregar las imágenes a las celdas
        tablaArticulos.getColumnModel().getColumn(8).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);

                // Cargar la imagen desde la ruta
                if (value != null && value instanceof String) {
                    String ruta = (String) value;
                    File imagenFile = new File(ruta);
                    if (imagenFile.exists()) {
                        ImageIcon icon = new ImageIcon(ruta);
                        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Ajustar tamaño
                        label.setIcon(new ImageIcon(img));
                    } else {
                        label.setText("No disponible");
                    }
                }
                return label;
            }
        });

        // Llenar la tabla con los datos de la lista
        for (Articulo a : articulos) {
            Object[] fila = {
                a.getId(),
                categoriaControlador.obtenerNombrePorId(a.getIdCategoria()),
                proveedorControlador.obtenerNombrePorId(a.getIdProveedor()),
                a.getCodigo(),
                a.getNombre(),
                a.getPrecioVenta(),
                a.getStock(),
                a.getDescripcion(),
                a.getImagen()
            };

            tableModel.addRow(fila);
        }

    }

    public void registrarArticulo() {
        // Obtener los datos de los campos
        String codigoArticulo = txtCodigoArticulo.getText().trim();
        String nombreArticulo = txtNombreArticulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precio = txtPrecio.getText().trim();
        String stock = txtStock.getText().trim();
        String nombreProveedor = cmbProveedor.getSelectedItem().toString();
        String nombreCategoria = cmbCategoria.getSelectedItem().toString();

        // Validar que los campos obligatorios no estén vacíos
        if (codigoArticulo.isEmpty() || nombreArticulo.isEmpty() || descripcion.isEmpty()
                || precio.isEmpty() || stock.isEmpty() || nombreProveedor.isEmpty() || nombreCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir el precio y stock a los formatos adecuados
        float precioArticulo;
        int stockArticulo;
        try {
            precioArticulo = (float) Double.parseDouble(precio);
            stockArticulo = Integer.parseInt(stock);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio y el stock deben ser valores numéricos válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los IDs del proveedor y categoría
        int idProveedor = proveedorControlador.obtenerIdPorNombre(nombreProveedor);
        int idCategoria = categoriaControlador.obtenerIdPorNombre(nombreCategoria);

        // Validar que los IDs sean válidos
        if (idProveedor == 0 || idCategoria == 0) {
            JOptionPane.showMessageDialog(null, "Proveedor o Categoría inválidos. Verifique sus selecciones.", "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si se seleccionó una imagen
        String rutaImagen = null;
        if (imagenSeleccionada != null) {
            try {
                // Definir la carpeta de destino dentro del proyecto
                String carpetaDestino = "src/imgs"; // Asegúrate de que esta carpeta exista

                // Crear un nuevo nombre para la imagen (opcional, para evitar conflictos)
                String nuevoNombre = System.currentTimeMillis() + "_" + imagenSeleccionada.getName();

                // Crear la ruta completa de destino
                File destino = new File(carpetaDestino, nuevoNombre);

                // Copiar la imagen al destino
                Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Obtener la nueva ruta relativa de la imagen guardada
                rutaImagen = carpetaDestino + "/" + nuevoNombre;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Crear un objeto Articulo
        Articulo nuevoArticulo = new Articulo(idCategoria, idProveedor, codigoArticulo, nombreArticulo, precioArticulo, stockArticulo, descripcion, rutaImagen, true);

        // Confirmar antes de guardar
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea registrar este artículo?\n"
                + "Código: " + codigoArticulo + "\nNombre: " + nombreArticulo + "\nDescripción: " + descripcion
                + "\nPrecio: " + precioArticulo + "\nStock: " + stockArticulo + "\nProveedor: " + nombreProveedor
                + "\nCategoría: " + nombreCategoria,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Guardar el artículo utilizando el controlador
            if (articuloControlador.agregarArticulo(nuevoArticulo)) {
                JOptionPane.showMessageDialog(null, "Artículo registrado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTablaArticulos(); // Método para actualizar la tabla
                limpiarDatos(); // Método para limpiar los campos del formulario
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar el artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Registro de artículo cancelado.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void editarArticulo() {
        // Validar que se haya seleccionado un artículo
        if (idArticulo == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un artículo para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener los datos de los campos
        String codigoArticulo = txtCodigoArticulo.getText().trim();
        String nombreArticulo = txtNombreArticulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precio = txtPrecio.getText().trim();
        String stock = txtStock.getText().trim();
        String nombreProveedor = cmbProveedor.getSelectedItem().toString();
        String nombreCategoria = cmbCategoria.getSelectedItem().toString();

        // Validar que los campos obligatorios no estén vacíos
        if (codigoArticulo.isEmpty() || nombreArticulo.isEmpty() || descripcion.isEmpty()
                || precio.isEmpty() || stock.isEmpty() || nombreProveedor.isEmpty() || nombreCategoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convertir el precio y stock a los formatos adecuados
        float precioArticulo;
        int stockArticulo;
        try {
            precioArticulo = (float) Double.parseDouble(precio);
            stockArticulo = Integer.parseInt(stock);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio y el stock deben ser valores numéricos válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener los IDs del proveedor y categoría
        int idProveedor = proveedorControlador.obtenerIdPorNombre(nombreProveedor);
        int idCategoria = categoriaControlador.obtenerIdPorNombre(nombreCategoria);

        // Validar que los IDs sean válidos
        if (idProveedor == 0 || idCategoria == 0) {
            JOptionPane.showMessageDialog(null, "Proveedor o Categoría inválidos. Verifique sus selecciones.", "Error de selección", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si se seleccionó una imagen
        String rutaImagen = null;
        if (imagenSeleccionada != null) {
            try {
                // Definir la carpeta de destino dentro del proyecto
                String carpetaDestino = "src/imgs"; // Asegúrate de que esta carpeta exista

                // Crear un nuevo nombre para la imagen (opcional, para evitar conflictos)
                String nuevoNombre = System.currentTimeMillis() + "_" + imagenSeleccionada.getName();

                // Crear la ruta completa de destino
                File destino = new File(carpetaDestino, nuevoNombre);

                // Copiar la imagen al destino
                Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Obtener la nueva ruta relativa de la imagen guardada
                rutaImagen = carpetaDestino + "/" + nuevoNombre;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        // Crear un objeto Articulo
        Articulo articuloEditado = new Articulo(idCategoria, idProveedor, codigoArticulo, nombreArticulo, precioArticulo, stockArticulo, descripcion, rutaImagen, true);

        // Confirmar antes de actualizar
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea actualizar este artículo?\n"
                + "Código: " + codigoArticulo + "\nNombre: " + nombreArticulo + "\nDescripción: " + descripcion
                + "\nPrecio: " + precioArticulo + "\nStock: " + stockArticulo + "\nProveedor: " + nombreProveedor
                + "\nCategoría: " + nombreCategoria,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Actualizar el artículo utilizando el controlador
            if (articuloControlador.actualizarArticulo(idArticulo, articuloEditado)) {
                JOptionPane.showMessageDialog(null, "Artículo actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTablaArticulos(); // Método para actualizar la tabla
                limpiarDatos(); // Método para limpiar los campos del formulario
                idArticulo = 0; // Reiniciar la variable global
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo actualizar el artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Edición de artículo cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void eliminarArticulo() {
        // Validar que se haya seleccionado un artículo
        if (idArticulo == 0) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un artículo para desactivar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar antes de desactivar
        int confirmacion = JOptionPane.showConfirmDialog(null,
                "¿Está seguro de que desea desactivar este artículo?\nID: " + idArticulo,
                "Confirmación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamar al método del controlador para desactivar el artículo
            if (articuloControlador.eliminarArticulo(idArticulo)) {
                JOptionPane.showMessageDialog(null, "Artículo desactivado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                llenarTablaArticulos(); // Actualizar la tabla
                limpiarDatos(); // Limpiar los datos del formulario
                idArticulo = 0; // Reiniciar el ID seleccionado
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo desactivar el artículo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Desactivación de artículo cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void seleccionarDatosTabla() {
        // Agregar el listener de selección a la tabla
        tablaArticulos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {  // Para evitar que se dispare dos veces
                // Obtener la fila seleccionada
                int filaSeleccionada = tablaArticulos.getSelectedRow();

                if (filaSeleccionada != -1) {
                    // Obtener los valores de las columnas de la fila seleccionada

                    // Obtener los valores de las columnas de la fila seleccionada
                    Object id = tablaArticulos.getValueAt(filaSeleccionada, 0); // ID del artículo
                    Object categoria = tablaArticulos.getValueAt(filaSeleccionada, 1); // Categoría
                    Object proveedor = tablaArticulos.getValueAt(filaSeleccionada, 2); // Proveedor    
                    Object codigo = tablaArticulos.getValueAt(filaSeleccionada, 3); // Código del artículo                  
                    Object nombre = tablaArticulos.getValueAt(filaSeleccionada, 4); // Nombre del artículo
                    Object precio = tablaArticulos.getValueAt(filaSeleccionada, 5); // Precio
                    Object stock = tablaArticulos.getValueAt(filaSeleccionada, 6); // Stock

                    Object descripcion = tablaArticulos.getValueAt(filaSeleccionada, 7); // Descripción

                    Object rutaImagen = tablaArticulos.getValueAt(filaSeleccionada, 8); // Ruta de la imagen

                    // Asignar los valores a los campos de texto
                    idArticulo = (int) id; // Guardar el ID del artículo seleccionado
                    txtCodigoArticulo.setText(String.valueOf(codigo));
                    txtNombreArticulo.setText(String.valueOf(nombre));
                    txtDescripcion.setText(String.valueOf(descripcion));
                    txtPrecio.setText(String.valueOf(precio));
                    txtStock.setText(String.valueOf(stock));
                    cmbCategoria.setSelectedItem(String.valueOf(categoria)); // Seleccionar categoría en el ComboBox
                    cmbProveedor.setSelectedItem(String.valueOf(proveedor)); // Seleccionar proveedor en el ComboBox

                    // Mostrar la imagen en un JLabel (opcional)
                    if (rutaImagen != null && !String.valueOf(rutaImagen).isEmpty()) {
                        ImageIcon icono = new ImageIcon(new ImageIcon(String.valueOf(rutaImagen))
                                .getImage()
                                .getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH));
                        lblImagen.setIcon(icono);

                        imagenSeleccionada = new File(String.valueOf(rutaImagen));
                        System.out.println(imagenSeleccionada.getAbsolutePath());

                    } else {
                        lblImagen.setIcon(null); // Si no hay imagen, limpiar el JLabel
                    }

                    System.out.println("ID de Artículo Seleccionado: " + idArticulo); // Mostrar el ID en la consola para verificación
                }
            }
        });
    }

    public void llenarComboBoxConCategorias() {
        // Obtener todas las categorías desde el DAO
        List<Categoria> categorias = categoriaControlador.obtenerTodos();

        // Limpiar el JComboBox antes de llenarlo
        cmbCategoria.removeAllItems();

        // Agregar un elemento predeterminado (opcional)
        cmbCategoria.addItem("Seleccione una categoría");

        // Llenar el JComboBox con los nombres de las categorías
        for (Categoria categoria : categorias) {
            cmbCategoria.addItem(categoria.getNombre());
        }
    }

    public void llenarComboBoxConProveedores() {
        // Obtener todos los proveedores desde el DAO
        List<Proveedor> proveedores = proveedorControlador.obtenerTodosLosProveedores();

        // Limpiar el JComboBox antes de llenarlo
        cmbProveedor.removeAllItems();

        // Agregar un elemento predeterminado (opcional)
        cmbProveedor.addItem("Seleccione un proveedor");

        // Llenar el JComboBox con los nombres de los proveedores
        for (Proveedor proveedor : proveedores) {
            cmbProveedor.addItem(proveedor.getRazonSocial());
        }
    }

    public void limpiarDatos() {
        txtCodigoArticulo.setText("");
        txtNombreArticulo.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        cmbProveedor.setSelectedIndex(0);
        cmbCategoria.setSelectedIndex(0);
        lblImagen.setIcon(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaArticulos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoArticulo = new javax.swing.JTextField();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        cmbProveedor = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtNombreArticulo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtStock = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel1.setText("Imagen Artículo");

        tablaArticulos.setFont(new java.awt.Font("Roboto Medium", 0, 19)); // NOI18N
        tablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Categoría", "Proveedor", "Código", "Nombre Artículo", "Precio", "Stock", "Descripción", "Imagen"
            }
        ));
        tablaArticulos.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tablaArticulos.setRowHeight(70);
        jScrollPane1.setViewportView(tablaArticulos);

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("Gestión de Artículos:");

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel4.setText("Registrar nuevo artículo:");

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setText("Código:");

        txtCodigoArticulo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel9.setText("Categoría:");

        jLabel10.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel10.setText("Proveedor:");

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setText("Nombre artículo:");

        txtNombreArticulo.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel8.setText("Precio:");

        txtPrecio.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel11.setText("Stock:");

        txtStock.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel12.setText("Descripción:");

        txtDescripcion.setFont(new java.awt.Font("Roboto Light", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel13.setText("Imagen:");

        jButton4.setBackground(new java.awt.Color(108, 117, 125));
        jButton4.setFont(new java.awt.Font("Roboto Black", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Subir Imagen");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCodigoArticulo)
                                        .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(29, 29, 29)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNombreArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtPrecio))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addComponent(lblImagen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(30, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cmbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtPrecio))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStock)
                            .addComponent(cmbProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );
    }// </editor-fold>//GEN-END:initComponents

    //boton para seleccionar imagen
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Crear un JFileChooser
        JFileChooser fileChooser = new JFileChooser();

        // Filtrar solo imágenes
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "gif"));

        // Mostrar el cuadro de diálogo
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el archivo seleccionado
            File file = fileChooser.getSelectedFile();

            imagenSeleccionada = fileChooser.getSelectedFile();

            System.out.println("Archivo seleccionado: " + file.getAbsolutePath());

            // Crear un ImageIcon a partir del archivo
            ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());

            // Obtener las dimensiones del JLabel
            int labelWidth = lblImagen.getWidth();
            int labelHeight = lblImagen.getHeight();

            // Escalar la imagen al tamaño del JLabel
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

            // Crear un nuevo ImageIcon con la imagen escalada
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Establecer la imagen en el JLabel
            lblImagen.setIcon(scaledIcon);
            lblImagen.setText(""); // Eliminar el texto del JLabel una vez que se muestra la imagen
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        registrarArticulo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        editarArticulo();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        eliminarArticulo();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbCategoria;
    private javax.swing.JComboBox<String> cmbProveedor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTable tablaArticulos;
    private javax.swing.JTextField txtCodigoArticulo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombreArticulo;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
