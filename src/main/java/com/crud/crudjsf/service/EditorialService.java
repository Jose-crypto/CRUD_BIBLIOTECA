package com.crud.crudjsf.service;


import com.crud.crudjsf.enitdad.Editoriales;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Stateful
public class EditorialService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public List<Editoriales> obtenerEditoriales() {
        try {
            return em.createQuery("SELECT a FROM Editoriales a where estado='activo' order by id", Editoriales.class).getResultList();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;

    }

    public void crearEditorial(Editoriales editorial) {
        if (editorial!=null) {
            editorial.setEstado("activo");
            em.persist(editorial);
        }
    }

    public void actualizarEditorial(Editoriales editorial) {
        if (editorial != null) {
            editorial.setEstado("activo");
            em.merge(editorial);
        }else{
            System.out.println("Error");
        }


    }

    public void eliminarEditorial(int id) {
        Editoriales editorial = obtenerEditorialPorId(id);
        if (editorial != null) {
            editorial.setEstado("Inactivo");
            em.merge(editorial);
        }
    }

    public Editoriales obtenerEditorialPorId(int id) {
        return em.find(Editoriales.class, id);

    }


    //busqueda de editoriales

    public List<Editoriales> obtenerEditorialesPorNombre(String nombre) {
        return em.createQuery("SELECT e FROM Editoriales e WHERE e.nombre LIKE :nombre and  e.estado='activo'", Editoriales.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }



}
