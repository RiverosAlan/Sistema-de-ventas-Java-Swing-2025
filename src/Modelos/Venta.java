package Modelos;

public class Venta {

    private int id;
    private int idCliente;
    private int idUsuario;
    private String tipoComprobante;
    private String numComprobante;
    private String fecha;
    private float total;
    private String estado;
    
    private Cliente cliente;

    public Venta() {
    }

    public Venta(int id, int idCliente, int idUsuario, String tipoComprobante, String numComprobante, String fecha, float total, String estado) {
        this.id = id;
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.tipoComprobante = tipoComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public Venta(int idCliente, int idUsuario, String tipoComprobante, String numComprobante, String fecha, float total, String estado) {
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.tipoComprobante = tipoComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Venta(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
    
    
    
    
    
    
    


 
}
