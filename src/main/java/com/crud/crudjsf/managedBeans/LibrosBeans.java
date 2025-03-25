package com.crud.crudjsf.managedBeans;


import com.crud.crudjsf.enitdad.Autores;
import com.crud.crudjsf.enitdad.Editoriales;
import com.crud.crudjsf.enitdad.Libros;
import com.crud.crudjsf.service.LibrosService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LibrosBeans implements Serializable {

    private Libros libro = new Libros();
    private List<Libros> libros;
    private String mensaje;
    private String nombrebusqueda;
    private List<Editoriales> editorialesList;
    private List<Autores> autoresList;
    private Integer editorialId;
    private Integer autorId;
    private String rutaImagen;



    @Inject
    private LibrosService librosService;


    // Métodos para interactuar con la base de datos
    public List<Libros> getLibros() {
        if (libros == null) {
            libros = librosService.obtenerLibros();
        }
        return libros;
    }


    // Inicializar los datos
    @PostConstruct
    public void init() {
        editorialesList = librosService.getAllEditoriales();
        autoresList = librosService.getAllAutores();
    }



    public void crearLibros() {
        try {
            Editoriales editorialSeleccionada = librosService.obtenerEditorialPorId(editorialId);
            Autores autorSeleccionado= librosService.obtenerAutoresPorId(autorId);

            libro.setEditoriales(editorialSeleccionada);
            libro.setAutores(autorSeleccionado);
            librosService.crearLibro(libro);

            this.mensaje="Libro Agregado Correctamente";
            libro = new Libros(); // Limpiar el formulario
            editorialId = null;
            autorId = null;
            libros = librosService.obtenerLibros();
            FacesMessage message = new FacesMessage("Libro Agregado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void eliminarLibros(Libros libro) {
        try{
            librosService.eliminarLibro(libro.getId());
            this.mensaje="Libro Eliminado Correctamente";
            libros = librosService.obtenerLibros();
            FacesMessage message = new FacesMessage("Libro Eliminado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void actualizarLibros() {
        try {

            Editoriales editorialSeleccionada = librosService.obtenerEditorialPorId(editorialId);
            Autores autorSeleccionado= librosService.obtenerAutoresPorId(autorId);
            libro.setEditoriales(editorialSeleccionada);
            libro.setAutores(autorSeleccionado);
            librosService.actualizarLibro(libro);
            this.mensaje="Libro modificado Correctamente";
            libro = new Libros(); // Limpiar el formulario
            editorialId = null;
            autorId = null;
            libros = librosService.obtenerLibros();
            FacesMessage message = new FacesMessage("Liibro modificado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void cargarDatosLibros(Libros libro) {
        try {
            this.libro = libro;
            this.editorialId = libro.getEditoriales().getIdEditorial();
            this.autorId = libro.getAutores().getId();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void limpiarFormulario() {
        libro = new Libros(); // Limpiar el formulario
        editorialId = null;
        autorId = null;
    }

    public void cargarAutores_Editoriales() {
        editorialesList = librosService.getAllEditoriales();
        autoresList = librosService.getAllAutores();
    }


    public void subirImagen(FileUploadEvent event) {
        try {
            // Obtener la imagen seleccionada
            UploadedFile uploadedFile = event.getFile();
            String nombreArchivo = uploadedFile.getFileName();

            // Suponiendo que tienes una carpeta 'imagenes' en tu servidor para guardar las imágenes
            String ruta = "resources/img/" + nombreArchivo;

            // Guardar la ruta de la imagen en la propiedad rutaImagen
            rutaImagen = ruta;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getter y Setter para libro
    public Libros getLibro() {
        return libro;
    }

    public void setLibro(Libros libro) {
        this.libro = libro;
    }


    // getter setter object editoriales
    public List<Editoriales> getEditorialesList() {
        return editorialesList;
    }

    public void setEditorialesList(List<Editoriales> editorialesList) {
        this.editorialesList = editorialesList;
    }


    // Getter y Setter para editorialId
    public Integer getEditorialId() {
        return editorialId;
    }

    public void setEditorialId(Integer editorialId) {
        this.editorialId = editorialId;
    }

    // getter setter object autoreslist
    public List<Autores> getAutoresList() {
        return autoresList;
    }

    public void setAutoresList(List<Autores> editorialesList) {
        this.autoresList = editorialesList;
    }

    // Getter y Setter para autorId
    public Integer getAutorId() {
        return autorId;
    }
    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    //busquedas
    public String getNombrebusqueda(){
        return nombrebusqueda;
    }

    public void setNombrebusqueda(String nombrebusqueda){
        this.nombrebusqueda = nombrebusqueda;
    }
    public void filtrosLibros(){
        libros = librosService.obtenerLibrosPorNombre(this.nombrebusqueda);

    }





}
