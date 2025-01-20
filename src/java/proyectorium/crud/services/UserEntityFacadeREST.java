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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import proyectorium.crud.entities.UserEntity;
import proyectorium.crud.exceptions.CreateException;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.IncorrectCredentialsException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;
import proyectorium.crud.exceptions.UserAlreadyExistException;
import proyectorium.crud.exceptions.UserDoesntExistException;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("proyectorium.crud.entities.userentity")
public class UserEntityFacadeREST extends AbstractFacade<UserEntity> {

    @PersistenceContext(unitName = "CRUDProyectoriumPU")
    private EntityManager em;

    public UserEntityFacadeREST() {
        super(UserEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(UserEntity entity) {
        try {
            //Hasheo
            
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, UserEntity entity) {
        try {
            //hasheo
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) throws UserDoesntExistException, IncorrectCredentialsException {
        try {
            try {
                super.remove(super.find(id));
            } catch (DeleteException ex) {
                Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public UserEntity find(@PathParam("id") Long id) {
        try {
            //hasheo
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> findAll() {
        try {
            //hasheo
            return super.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException ex) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @POST
    @Path("signIn")
    @Consumes({MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_XML})
    public UserEntity signIn(UserEntity credentials) throws IncorrectCredentialsException, UserDoesntExistException {
        try {
            // Busca al usuario por email
            UserEntity user = em.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity.class)
                    .setParameter("email", credentials.getEmail())
                    .getSingleResult();

            // Verifica la contraseña
            if (!user.getPassword().equals(credentials.getPassword())) {
                throw new IncorrectCredentialsException("Incorrect password.");
            }

            return user;
        } catch (javax.persistence.NoResultException e) {
            throw new UserDoesntExistException("User with email " + credentials.getEmail() + " does not exist.");
        } catch (Exception e) {
            Logger.getLogger(UserEntityFacadeREST.class.getName()).log(Level.SEVERE, null, e);
            throw new RuntimeException("An error occurred during sign-in.");
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
