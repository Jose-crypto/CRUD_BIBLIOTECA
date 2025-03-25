package com.crud.crudjsf.service;


import com.crud.crudjsf.enitdad.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Stateless
public class PrestamoService  implements Serializable {

    @PersistenceContext
    private EntityManager em;

    // Crear un nuevo préstamo
    public void crearPrestamo(Prestamo prestamo) {
        em.persist(prestamo);
    }

    // Obtener todos los préstamos
    public List<Prestamo> obtenerPrestamos() {
        return em.createQuery("SELECT p FROM Prestamo p", Prestamo.class).getResultList();
    }

    // Obtener todas los Libros
    public List<Libros> getAllLibros() {
        return em.createQuery("SELECT e FROM Libros e where e.estado='activo'", Libros.class).getResultList();
    }
    // Obtener todas los Libros
    public List<Usuarios> getAllUsuarios() {
        return em.createQuery("SELECT e FROM Usuarios e where e.estado='activo' and e.rol='user'", Usuarios.class).getResultList();
    }


    // Obtener los préstamos pendientes (no devueltos)
    public List<Prestamo> obtenerPrestamosPendientes() {
        return em.createQuery("SELECT p FROM Prestamo p WHERE p.devuelto = false", Prestamo.class).getResultList();
    }

    // Actualizar el estado del préstamo (devuelto)
    public void devolverPrestamo(int prestamoId, int cantidad) {
        Prestamo prestamo = obtenerprestamoPorId(prestamoId);
        if (prestamo != null) {
            prestamo.setDevuelto(true);
            em.merge(prestamo);
        }

    }

    public Prestamo obtenerprestamoPorId(int id) {
        return em.find(Prestamo.class, id);

    }



    // Obtener todos los préstamos por libro
    public List<Prestamo> obtenerPrestamosByLibro(String nombreLibro) {
        String jpql = "SELECT p FROM Prestamo p JOIN p.libro l WHERE LOWER(l.title) LIKE LOWER(:nombreLibro)";
        return em.createQuery(jpql, Prestamo.class)
                .setParameter("nombreLibro", nombreLibro)
                .getResultList();
    }


    // Obtener préstamos por usuario
    public List<Prestamo> obtenerPrestamosPorUsuario(int usuarioId) {
        return em.createQuery("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId", Prestamo.class)
                .setParameter("usuarioId", usuarioId)
                .getResultList();
    }


    // Obtener by ID los libros
    public Libros obtenerLibrosPorId(int id) {
        return em.find(Libros.class, id);
    }

    // Obtener by ID las usuarios
    public Usuarios obtenerUsersPorId(int id) {
        return em.find(Usuarios.class, id);
    }
}