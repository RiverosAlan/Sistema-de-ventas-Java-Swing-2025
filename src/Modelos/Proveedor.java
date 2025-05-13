package Modelos;

public class Proveedor {

    private int id;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String email;

    public Proveedor(int id, String razonSocial, String direccion, String telefono, String email) {
        this.id = id;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public Proveedor(String razonSocial, String direccion, String telefono, String email) {
        this.razonSocial = razonSocial;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
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
        return "Proveedor{" + "id=" + id + ", razonSocial=" + razonSocial + ", direccion=" + direccion + ", telefono=" + telefono + ", email=" + email + '}';
    }
    
    

}
