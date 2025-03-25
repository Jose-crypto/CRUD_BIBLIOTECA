package com.crud.crudjsf.service;

import com.crud.crudjsf.enitdad.Usuarios;
import com.crud.crudjsf.util.BCryptUtil;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Stateless
public class LoginService implements Serializable {

    @PersistenceContext(unitName = "jsf-crud-unit")
    private EntityManager em;

    // Crear un nuevo usuario
    public void createUser(Usuarios user) {
        // Encriptar la contraseña
        user.setPassword(BCryptUtil.hashPassword(user.getPassword()));
        // Persistir el usuario en la base de datos
        em.persist(user);
        System.out.println("EL ID ES-------------------: " + user.getId());



    }


    // Method to authenticate a user
    public Boolean authenticateUser(String username, String password) {
        // Query to find the user by username
        TypedQuery<Usuarios> query = em.createQuery("SELECT u FROM Usuarios u WHERE u.username = :username and  u.estado='activo'", Usuarios.class);
        query.setParameter("username", username);
        Usuarios user = query.getResultList().stream().findFirst().orElse(null);

        if (user != null) {
            // Verificar la contraseña encriptada con la ingresada
            if (BCryptUtil.checkPassword(password, user.getPassword())) {
                // Guardar el usuario y su rol en la sesión
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userId", user.getId()); // Assuming getId() returns the user's ID
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rol", user.getRol());

                return true; // Autenticación exitosa
            }
        }
        return false; // Usuario no encontrado o contraseña incorrecta
    }


}
