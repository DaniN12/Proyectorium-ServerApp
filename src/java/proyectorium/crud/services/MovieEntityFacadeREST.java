/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import proyectorium.crud.entities.MovieEntity;
import proyectorium.crud.entities.MovieHour;
import proyectorium.crud.exceptions.CreateException;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;
import proyectorium.crud.services.AbstractFacade;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("proyectorium.crud.entities.movie")
public class MovieEntityFacadeREST extends AbstractFacade<MovieEntity> {

    @PersistenceContext(unitName = "CRUDProyectoriumPU")
    private EntityManager em;

    public MovieEntityFacadeREST() {
        super(MovieEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(MovieEntity entity) {
        try {
            //MovieEntity m = new MovieEntity();
            //em.persist(entity);
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, MovieEntity entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) throws ReadException {
        try {
            super.remove(super.find(id));
        } catch (DeleteException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public MovieEntity find(@PathParam("id") Integer id) {
        try {
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<MovieEntity> findAll() {
        try {
            return super.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<MovieEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        try {
            return String.valueOf(super.count());
        } catch (ReadException ex) {
            Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("releaseDate")
    @Produces({MediaType.APPLICATION_XML})
    public List<MovieEntity> listByReleaseDate(@QueryParam("releaseDate") String releaseDate) {
        if (releaseDate == null || releaseDate.isEmpty()) {
            throw new IllegalArgumentException("Release date must be provided.");
        }

        return em.createNamedQuery("listByReleaseDate", MovieEntity.class)
                .setParameter("releaseDate", releaseDate)
                .getResultList();
    }

    @GET
    @Path("provider/{provider}")
    @Produces({MediaType.APPLICATION_XML})
    public List<MovieEntity> listByProvider(@PathParam("provider") String provider) {
        return em.createNamedQuery("listByProvider", MovieEntity.class)
                .setParameter("provider", provider)
                .getResultList();
    }

    @GET
    @Path("movieHour/{movieHour}")
    @Produces({MediaType.APPLICATION_XML})
    public List<MovieEntity> listByMovieHour(@PathParam("movieHour") String movieHour) {
        return em.createNamedQuery("listByMovieHour", MovieEntity.class)
                .setParameter("movieHour", MovieHour.valueOf(movieHour))
                .getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
