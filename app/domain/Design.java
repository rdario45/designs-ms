package domain;

import org.joda.time.DateTime;

import java.io.File;
import java.math.BigDecimal;

public class Design {

    private int id;
    private String nombre;
    private String autor;
    private BigDecimal precio;
    private String fileName;
    private File original;
    private File procesado;
    private Estado estado;
    private DateTime fecha;

    public Design(int id, String nombre, String autor, BigDecimal precio, String fileName, File original, File procesado, Estado estado, DateTime fecha) {
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

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getOriginal() {
        return original;
    }

    public void setOriginal(File original) {
        this.original = original;
    }

    public File getProcesado() {
        return procesado;
    }

    public void setProcesado(File procesado) {
        this.procesado = procesado;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public Design asignarProcesado(File procesado) {
        this.procesado = procesado;
        this.estado = Estado.DISPONIBLE;
        return this;
    }
}
