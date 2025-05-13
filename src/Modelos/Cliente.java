package Modelos;

public class Cliente {

    private int id;
    private String tipoCliente;
    private String nombre;
    private String tipoDocumento;
    private String numDocumento;
    private String direccion;
    private String telefono;
    private String email;

    public Cliente() {
    }
    
    

    public Cliente(int id, String tipoCliente, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email) {
        this.id = id;
        this.tipoCliente = tipoCliente;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }


    public Cliente(String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", tipoCliente=" + tipoCliente + ", nombre=" + nombre + ", tipoDocumento=" + tipoDocumento + ", numDocumento=" + numDocumento + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + '}';
    }

}
