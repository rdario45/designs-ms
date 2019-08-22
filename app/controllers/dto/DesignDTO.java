package controllers.dto;

public class DesignDTO {

    private int id;
    private String nombre;
    private String autor;
    private String precio;
    private String fileName;
    private String original;
    private String procesado;
    private String estado;
    private String fecha;

    public DesignDTO() {
        // to json serialize
    }

    public DesignDTO(String nombre, String autor, String precio, String fileName, String original) {
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
        this.fileName = fileName;
        this.original = original;
    }

    public DesignDTO(int id, String nombre, String autor, String precio, String fileName, String original, String procesado, String estado, String fecha) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.precio = precio;
        this.fileName = fileName;
        this.original = original;
        this.procesado = procesado;
        this.estado = estado;
        this.fecha = fecha;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getProcesado() {
        return procesado;
    }

    public void setProcesado(String procesado) {
        this.procesado = procesado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
