package com.crud.crudjsf.managedBeans;

import com.crud.crudjsf.enitdad.*;
import com.crud.crudjsf.service.LibrosService;
import com.crud.crudjsf.service.PrestamoService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class PrestamoBeans implements Serializable {

    private Prestamo prestamo = new Prestamo();
    private List<Prestamo> prestamos;
    private List<Libros> libros;
    private List<Usuarios> usuarios;
    private Integer libroId;
    private Integer usuarioId;
    private Libros libroSeleccionado;
    private Usuarios usuarioSeleccionado;
    private String mensaje;
    private String nombrebusqueda;
    private  Libros libro;



    @Inject
    private PrestamoService prestamoService;
    private LibrosService libroService;

    @PostConstruct
    public void init() {
        // Inicialización si es necesario
        libros = prestamoService.getAllLibros();
        usuarios= prestamoService.getAllUsuarios();
        prestamos = prestamoService.obtenerPrestamos();
    }

    // Crear un nuevo préstamo
    public void crearPrestamo() {
        try {
            //this.usuarioId = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId");
            Libros libroSeleccionado = prestamoService.obtenerLibrosPorId(libroId);
            Usuarios userSeleccionado= prestamoService.obtenerUsersPorId(usuarioId);
            if (libroSeleccionado.getStock()>prestamo.getCantidad()){
                prestamo.setLibro(libroSeleccionado);
                prestamo.setUsuario(userSeleccionado);
                prestamoService.crearPrestamo(prestamo);

                System.out.println("ID LIBRO: "+libroSeleccionado.getId() + "Cantidad: "+ prestamo.getCantidad());
                // Actualiza el stock de libros disponibles
                //libroSeleccionado.setStock(libroSeleccionado.getStock()-prestamo.getCantidad());
                System.out.println("EL STOCK ES: "+libroSeleccionado.getStock());
                //setCantidadDisponibles(libro.getCantidadDisponibles() - cantidadSolicitada);
                libroService.disminuirStockK(libroSeleccionado);
                //libroService.disminuirStockK(libro, prestamo.getCantidad());
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Préstamo Registrado Correctamente",
                        "El préstamo del libro '" + libroSeleccionado.getTitle() + "' se ha realizado con éxito.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                prestamo = new Prestamo(); // Limpiar el formulario
                libroId = null;
                usuarioId = null;
                prestamos = prestamoService.obtenerPrestamos();
            }else{

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Advertencia: Stock Insuficiente",
                        "Actualmente existe: " + libroSeleccionado.getStock() + " libros en stock.");
                FacesContext.getCurrentInstance().addMessage(null, message);

            }


        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al registrar el préstamo",
                    "Hubo un error al intentar registrar el préstamo. Inténtelo nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();
        }
    }

    public void eliminarPrestamo(Prestamo prestamo) {
        try{
            prestamoService.devolverPrestamo(prestamo.getId(),prestamo.getCantidad());
            this.mensaje="Libro devuelto correctamente";
            prestamo = new Prestamo(); // Limpiar el formulario
            prestamos = prestamoService.obtenerPrestamos();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Libro Devuelto Correctamente",
                    "El libro '" + libroSeleccionado.getTitle() + "' ha sido devuelto con éxito.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            FacesContext.getCurrentInstance().addMessage(null, message);
        }catch (Exception e){
            FacesMessage message = new FacesMessage("Error al devolver el Libro");
            FacesContext.getCurrentInstance().addMessage(null, message);
            e.printStackTrace();
        }
    }

    public List<Prestamo> consultarPrestamos(int idusuario) {
        prestamos = prestamoService.obtenerPrestamosPorUsuario(idusuario);
        return prestamos;
    }

    public void cargarLibros_Autores() {
        libros = prestamoService.getAllLibros();
        usuarios= prestamoService.getAllUsuarios();
    }



    // Getters y Setters
    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    // Getter y Setter para LibrosID
    public Integer getLibrosID() {
        return libroId;
    }

    public void setLibrosID(Integer librosId) {
        this.libroId = librosId;
    }

    public Integer getUsuariosID() {
        return usuarioId;
    }

    // Getter y Setter para usersID
    public void setUsuariosID(Integer usuariosId) {
        this.usuarioId = usuariosId;
    }




    // getter setter object LibroslIsr
    public List<Libros> getLibrosList() {
        return libros;
    }

    public void setLibrosList(List<Libros> librolista) {
        this.libros = librolista;
    }


    public Libros getLibroSeleccionado() {
        return libroSeleccionado;
    }

    public void setLibroSeleccionado(Libros libroSeleccionado) {
        this.libroSeleccionado = libroSeleccionado;
    }

    // getter setter object usuarioslIsr
    public List<Usuarios> getUsuariosList() {
        return usuarios;
    }

    public void setUsuariosList(List<Usuarios> Usuarioslista) {
        this.usuarios = Usuarioslista;
    }

    public Usuarios getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuarios usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }


    //busquedas
    public String getNombrebusqueda(){
        return nombrebusqueda;
    }

    public void setNombrebusqueda(String nombrebusqueda){
        this.nombrebusqueda = nombrebusqueda;
    }

    public void filtrosPrestamos(int valor){
        System.out.println("filtrosPrestamos: "+ this.nombrebusqueda);

        if (valor==2){
            prestamos = prestamoService.obtenerPrestamos();

        }else{
            this.nombrebusqueda = this.nombrebusqueda.trim();
            prestamos = prestamoService.obtenerPrestamosByLibro(this.nombrebusqueda);

        }


    }


}