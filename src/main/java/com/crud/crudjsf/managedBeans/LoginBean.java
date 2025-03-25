package com.crud.crudjsf.managedBeans;

import com.crud.crudjsf.enitdad.Usuarios;
import com.crud.crudjsf.service.LoginService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private int id;
    private String mensaje;

    @Inject
    private LoginService loginService;  // Inject the LoginService


    private boolean modalVisible = false;


    // Action method for login
    public String login() {
        System.out.println("username: " + username + " password: " + password+ " id: " + id);

        // Intentamos autenticar al usuario
        if (loginService.authenticateUser(username, password)) {
            // Obtenemos el rol del usuario desde la sesión
            String rol = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rol");
            int idusuario = (int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userId"); // Assuming getId() returns the user's ID

            System.out.println("rol: " + rol);
            System.out.println("idusuario: " + idusuario);
            this.id = idusuario;
            if ("admin".equals(rol)) {
                return "index.xhtml?faces-redirect=true"; // Redirigir a la página de administrador
            } else if ("user".equals(rol)) {
                return "userPage.xhtml?faces-redirect=true"; // Redirigir a la página de usuario
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Rol no reconocido"));
                return null; // En caso de un rol no reconocido
            }
        } else {
            // Si la autenticación falla, mostramos un mensaje de error
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos"));
            return null; // Mantener en la misma página si la autenticación falla
        }

    }

    // Método para crear un nuevo usuario
    public void createUser() {
        Usuarios user  = new Usuarios();
        //System.out.println("username: " + username + " password: " + password);
        user.setUsername(username);
        user.setPassword(password);
        user.setEstado("activo");
        user.setRol("user");
        loginService.createUser(user);
        this.mensaje="Usuario Registrado Correctamente";
        FacesMessage message = new FacesMessage("Usuario Registrado Correctamente");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.modalVisible = false;
        this.username = null;
        this.password = null;
    }


    public String logout() {
        username = "";
        password = "";
        id = 0;
        return "login?faces-redirect=true";
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isModalVisible() {
        return modalVisible;
    }

    public void setModalVisible(boolean modalVisible) {
        this.modalVisible = modalVisible;
    }

    public String getMensaje() {
        return mensaje;
    }
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


}
