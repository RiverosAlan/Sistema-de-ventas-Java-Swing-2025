package Modelos;

public class DetalleVenta {

    private int id;
    private int idVenta;
    private int idArticulo;
    private int cantidad;
    private float precio;
    private float descuento;

    public DetalleVenta() {
    }
    
    
    

    public DetalleVenta(int id, int idVenta, int idArticulo, int cantidad, float precio, float descuento) {
        this.id = id;
        this.idVenta = idVenta;
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

    public DetalleVenta(int idVenta, int idArticulo, int cantidad, float precio, float descuento) {
        this.idVenta = idVenta;
        this.idArticulo = idArticulo;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descuento = descuento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" + "id=" + id + ", idVenta=" + idVenta + ", idArticulo=" + idArticulo + ", cantidad=" + cantidad + ", precio=" + precio + ", descuento=" + descuento + '}';
    }

}
