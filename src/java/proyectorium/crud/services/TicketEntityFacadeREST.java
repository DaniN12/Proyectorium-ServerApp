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
import proyectorium.crud.entities.TicketEntity;
import proyectorium.crud.exceptions.CreateException;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;

/**
 *
 * @author kbilb
 */
@Stateless
@Path("proyectorium.crud.entities.ticket")
public class TicketEntityFacadeREST extends AbstractFacade<TicketEntity> {

    @PersistenceContext(unitName = "CRUDProyectoriumPU")
    private EntityManager em;

    public TicketEntityFacadeREST() {
        super(TicketEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(TicketEntity entity) {
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Integer id, TicketEntity entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            try {
                super.remove(super.find(id));
            } catch (DeleteException ex) {
                Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ReadException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public TicketEntity find(@PathParam("id") Integer id) {
        try {
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<TicketEntity> findAll() {
        try {
            return super.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<TicketEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("by-movie")
    @Produces({MediaType.APPLICATION_XML})
    public List<TicketEntity> listByMovieASC() {
        try {
            return em.createNamedQuery("listByMovieASC", TicketEntity.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("by-buy-date")
    @Produces({MediaType.APPLICATION_XML})
    public List<TicketEntity> listByBuyDateASC() {
        try {
            return em.createNamedQuery("listByBuyDateASC", TicketEntity.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("by-price")
    @Produces({MediaType.APPLICATION_XML})
    public List<TicketEntity> listByPriceASC() {
        try {
            return em.createNamedQuery("listByPriceASC", TicketEntity.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(TicketEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
