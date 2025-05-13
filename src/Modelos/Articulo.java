package Modelos;

public class Articulo {

    private int id;
    private int idCategoria;
    private int idProveedor; // Relación directa con proveedor
    private String codigo;
    private String nombre;
    private float precioVenta;
    private int stock;
    private String descripcion;
    private String imagen;
    private boolean estado;

    public Articulo() {
    }
    
    
    

    public Articulo(int id, int idCategoria, int idProveedor, String codigo, String nombre, float precioVenta, int stock, String descripcion, String imagen, boolean estado) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
    }

    public Articulo(int idCategoria, int idProveedor, String codigo, String nombre, float precioVenta, int stock, String descripcion, String imagen, boolean estado) {
        this.idCategoria = idCategoria;
        this.idProveedor = idProveedor;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioVenta = precioVenta;
        this.stock = stock;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Articulo{" + "id=" + id + ", idCategoria=" + idCategoria + ", idProveedor=" + idProveedor + ", codigo=" + codigo + ", nombre=" + nombre + ", precioVenta=" + precioVenta + ", stock=" + stock + ", descripcion=" + descripcion + ", imagen=" + imagen + ", estado=" + estado + '}';
    }

}
