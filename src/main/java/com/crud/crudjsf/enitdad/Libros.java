package com.crud.crudjsf.enitdad;


import javax.persistence.*;

//entidad JPA
@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int stock;
    private String estado;
    private String imagen;

    @ManyToOne
    private Autores autores;

    @ManyToOne
    private Editoriales editoriales;

    public Editoriales getEditoriales() {
        return editoriales;
    }

    public void setEditoriales(Editoriales editoriales) {
        this.editoriales = editoriales;
    }

    public Autores getAutores() {
        return autores;
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


}
