package com.crud.crudjsf.managedBeans;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class NavegacionBeans implements Serializable {

    // Método que redirige a la vista de libros
    public String irALibros() {
        return "libros?faces-redirect=true";  // Redirige a libros.xhtml
    }

    // Método que redirige a la vista de Autores
    public String irAutores() {
        return "autores?faces-redirect=true";  // Redirige a libros.xhtml
    }

    // Método que redirige a la vista de Editoriales
    public String irEditoriales() {
        return "editoriales?faces-redirect=true";  // Redirige a libros.xhtml
    }

    // Método que redirige a la vista de Editoriales
    public String irPrestamos() {
        return "index?faces-redirect=true";  // Redirige a index.xhtml
    }

}

