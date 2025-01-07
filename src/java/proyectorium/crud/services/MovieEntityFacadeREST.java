/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.services;

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
import javax.ws.rs.core.MediaType;
import proyectorium.crud.entities.MovieEntity;
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
@Path("listByContractInit")
@Produces({MediaType.APPLICATION_XML})
public List<MovieEntity> listByContractInit() {
    try {
        return getEntityManager().createNamedQuery("listByContractInit", MovieEntity.class).getResultList();
    } catch (Exception ex) {
        Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
}

@GET
@Path("listByContractEnd")
@Produces({MediaType.APPLICATION_XML})
public List<MovieEntity> listByContractEnd() {
    try {
        return getEntityManager().createNamedQuery("listByContractEnd", MovieEntity.class).getResultList();
    } catch (Exception ex) {
        Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
}

@GET
@Path("listByPrice")
@Produces({MediaType.APPLICATION_XML})
public List<MovieEntity> listByPrice() {
    try {
        return getEntityManager().createNamedQuery("listByPrice", MovieEntity.class).getResultList();
    } catch (Exception ex) {
        Logger.getLogger(MovieEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        return null;
    }
}


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
