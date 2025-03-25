package com.crud.crudjsf.managedBeans;


import com.crud.crudjsf.enitdad.Autores;
import com.crud.crudjsf.service.AutoresService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named
@SessionScoped
public class AutoresBeans implements Serializable {

    private Autores autor = new Autores();
    private List<Autores> autores;
    private String mensaje;
    private String nombrebusqueda;


    @Inject
    private AutoresService autorService;


    // Métodos para interactuar con la base de datos

    public List<Autores> getAutores() {
        if (autores == null) {
            autores = autorService.obtenerTodosAutores();
        }
        return autores;
    }

    public void crearAutor() {
        try {
            autor.setNombre(autor.getNombre());
            autor.setApellido(autor.getApellido());
            autorService.crearAutores(autor);
            this.mensaje="Autor Agregado Correctamente";
            autor = new Autores(); // Limpiar el formulario
            autores = autorService.obtenerTodosAutores();
            FacesMessage message = new FacesMessage("Autor Agregado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void actualizarAutor() {
        try {
            autorService.actualizarAutor(autor);
            this.mensaje="Autor modificado Correctamente";
            autor = new Autores(); // Limpiar el formulario*/
            autorService.obtenerTodosAutores();
            FacesMessage message = new FacesMessage("Autor modificado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }


    }


    public void eliminarAutor(Autores autor) {
        try{
            autorService.eliminarAutor(autor.getId());
            this.mensaje="Autor Eliminado Correctamente";
            autor = new Autores(); // Limpiar el formulario
            autores = autorService.obtenerTodosAutores();
            FacesMessage message = new FacesMessage("Autor Eliminado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void cargarDatos(Autores autor) {
        try {
            this.autor = autor;

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    //busquedas
    public String getNombrebusqueda(){
        return nombrebusqueda;
    }

    public void setNombrebusqueda(String nombrebusqueda){
        this.nombrebusqueda = nombrebusqueda;
    }
    public void filtrosEditorial(){
        autores = autorService.obtenerAutoresPorNombre(this.nombrebusqueda);

    }

    // Getters y setters
    public Autores getAutor() {
        return autor;
    }

    public void setAutor(Autores autor) {
        this.autor = autor;
    }


}
