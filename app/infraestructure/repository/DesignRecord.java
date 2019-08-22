package infraestructure.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class DesignRecord {

    private int id;
    private String nombre;
    private String autor;
    private BigDecimal precio;
    private String fileName;
    private byte[] original;
    private byte[] procesado;
    private String estado;
    private Timestamp fecha;

    public DesignRecord(int id, String nombre, String autor, BigDecimal precio, String fileName, byte[] original, byte[] procesado, String estado, Timestamp fecha) {
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

    public byte[] getOriginal() {
        return original;
    }

    public void setOriginal(byte[] original) {
        this.original = original;
    }

    public byte[] getProcesado() {
        return procesado;
    }

    public void setProcesado(byte[] procesado) {
        this.procesado = procesado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
