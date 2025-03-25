package com.crud.crudjsf.service;


import com.crud.crudjsf.enitdad.Autores;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;


@Stateful
public class AutoresService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    public List<Autores> obtenerTodosAutores() {
        try {
            return em.createQuery("SELECT a FROM Autores a where estado='activo' order by id", Autores.class).getResultList();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;

    }

    public void crearAutores(Autores autor) {
        if (autor!=null) {
            autor.setEstado("activo");
            em.persist(autor);
        }
    }

    public void actualizarAutor(Autores autor) {
        if (autor != null) {
            autor.setEstado("activo");
            em.merge(autor);
        }else{
            System.out.println("Error");
        }


    }

    public void eliminarAutor(int id) {
        Autores autor = obtenerAutorPorId(id);
        if (autor != null) {
            autor.setEstado("Inactivo");
            em.merge(autor);
        }
    }

    public Autores obtenerAutorPorId(int id) {
        return em.find(Autores.class, id);

    }


    //busqueda de editoriales

    public List<Autores> obtenerAutoresPorNombre(String nombre) {
        return em.createQuery("SELECT e FROM Autores e WHERE e.nombre LIKE :nombre and  e.estado='activo'", Autores.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }
}
