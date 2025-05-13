package Modelos;

public class Roles {
    
    private int id;
    private String nombre;
    private String descripcion;

    public Roles() {
    }
    
    

    public Roles(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Roles(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
