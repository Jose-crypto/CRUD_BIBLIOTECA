package com.crud.crudjsf.managedBeans;


import com.crud.crudjsf.enitdad.Editoriales;
import com.crud.crudjsf.service.EditorialService;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EditorialBeans implements Serializable {

    private Editoriales editorial = new Editoriales();
    private List<Editoriales> editoriales;
    private String mensaje;
    private String nombrebusqueda;


    @Inject
    private EditorialService editorialService;


    // Métodos para interactuar con la base de datos

    public List<Editoriales> getEditoriales() {
        if (editoriales == null) {
            editoriales = editorialService.obtenerEditoriales();
        }
        return editoriales;
    }

    public void crearEditorial() {
        try {
            editorialService.crearEditorial(editorial);
            this.mensaje="Editorial Agregado Correctamente";
            editorial = new Editoriales(); // Limpiar el formulario
            editoriales = editorialService.obtenerEditoriales();
            FacesMessage message = new FacesMessage("Editorial Agregado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void actualizarEditorial() {
        try {
            editorialService.actualizarEditorial(editorial);
            this.mensaje="Editorial modificado Correctamente";
            editorial = new Editoriales(); // Limpiar el formulario
            editoriales = editorialService.obtenerEditoriales();
            FacesMessage message = new FacesMessage("Editorial modificado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }


    public void eliminarEditorial(Editoriales editorial) {
        try{
            editorialService.eliminarEditorial(editorial.getIdEditorial());
            this.mensaje="Editorial Eliminado Correctamente";
            editoriales = editorialService.obtenerEditoriales();
            FacesMessage message = new FacesMessage("Editorial Eliminado Correctamente");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }catch (Exception e) {

            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void cargarDatosEditorial(Editoriales editorial) {
        try {
            this.editorial = editorial;

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }



    // Getters y setters
    public Editoriales getEditorial() {
        return editorial;
    }

    public void setEditorial(Editoriales  editorial) {
        this.editorial = editorial;
    }


    //busquedas
    public String getNombrebusqueda(){
        return nombrebusqueda;
    }

    public void setNombrebusqueda(String nombrebusqueda){
        this.nombrebusqueda = nombrebusqueda;
    }
    public void filtrosEditorial(){
        editoriales = editorialService.obtenerEditorialesPorNombre(this.nombrebusqueda);

    }
}
