package Modelos;

public class Usuario {

    private int id;
    private int idRol;
    private String nombre;
    private String tipoDocumento;
    private String numDocumento;
    private String direccion;
    private String telefono;
    private String email;
    private String clave;
    private boolean estado;

    public Usuario() {
    }

    public Usuario(int id, int idRol, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email, String clave, boolean estado) {
        this.id = id;
        this.idRol = idRol;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.estado = estado;
    }

    public Usuario(int idRol, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email, String clave, boolean estado) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.estado = estado;
    }
    
    
  

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    

    @Override
    public String toString() {
        return "Usuarios{" + "id=" + id + ", idRol=" + idRol + ", tipoDocumento=" + tipoDocumento + ", numDocumento=" + numDocumento + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + ", clave=" + clave + ", estado=" + estado + '}';
    }
    
    

}
