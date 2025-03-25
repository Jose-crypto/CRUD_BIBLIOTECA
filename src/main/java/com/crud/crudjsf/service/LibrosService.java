package com.crud.crudjsf.service;

import com.crud.crudjsf.enitdad.Autores;
import com.crud.crudjsf.enitdad.Editoriales;
import com.crud.crudjsf.enitdad.Libros;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Stateless
public class LibrosService implements Serializable {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<Libros> obtenerLibros() {
        try {
            return em.createQuery("SELECT a FROM Libros a where a.estado='activo' order by id", Libros.class).getResultList();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return null;

    }



    @Transactional
    public void crearLibro(Libros libro) {
        if (libro!=null) {
            libro.setEstado("activo");
            em.persist(libro);
        }
    }

    // Obtener todas las editoriales
    public List<Editoriales> getAllEditoriales() {
        return em.createQuery("SELECT e FROM Editoriales e where e.estado='activo'", Editoriales.class).getResultList();
    }

    // Obtener by ID las editoriales
    public Editoriales obtenerEditorialPorId(int id) {
        return em.find(Editoriales.class, id);
    }

    // Obtener todas los autoes
    public List<Autores> getAllAutores() {
        return em.createQuery("SELECT e FROM Autores e where e.estado='activo'", Autores.class).getResultList();
    }

    // Obtener by ID las autores
    public Autores obtenerAutoresPorId(int id) {
        return em.find(Autores.class, id);
    }

    //busqueda de libros filter
    public List<Libros> obtenerLibrosPorNombre(String nombre) {
        return em.createQuery("SELECT e FROM Libros e WHERE e.title LIKE :nombre AND e.estado = 'activo' ", Libros.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public void eliminarLibro(int id) {
        Libros libro = obtenerLibroPorId(id);
        if (libro != null) {
            libro.setEstado("Inactivo");
            em.merge(libro);
        }
    }
    public Libros obtenerLibroPorId(int id) {
        return em.find(Libros.class, id);

    }

    public void actualizarLibro(Libros libro) {
        if (libro != null) {
            libro.setEstado("activo");
            em.merge(libro);
        }else{
            System.out.println("Error");
        }
    }


    // Disminuir el stock del libro cuando se realiza un préstamo

    @Transactional
    public void disminuirStockK(Libros libro) {
        if (libro != null) {
            libro.setStock(libro.getStock());
            em.merge(libro);
        }else{
            System.out.println("Error");
        }
    }

}

