package com.crud.crudjsf.enitdad;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_libro", referencedColumnName = "id")
    private Libros libro;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuarios usuario; // Asume que tienes una entidad Usuarios para manejar los usuarios

    @Temporal(TemporalType.DATE)
    private Date fechaPrestamo;

    @Temporal(TemporalType.DATE)
    private Date fechaDevolucion;

    private Boolean devuelto = false;  // Si el libro fue devuelto o no
    private int cantidad;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Libros getLibro() {
        return libro;
    }

    public void setLibro(Libros libro) {
        this.libro = libro;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Boolean getDevuelto() {
        return devuelto;
    }

    public void setDevuelto(Boolean devuelto) {
        this.devuelto = devuelto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}