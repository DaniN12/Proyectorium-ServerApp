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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import proyectorium.crud.entities.CategoryEntity;
import proyectorium.crud.entities.MovieEntity;
import proyectorium.crud.exceptions.CreateException;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("proyectorium.crud.entities.categoryentity")
public class CategoryEntityFacadeREST extends AbstractFacade<CategoryEntity> {

    @PersistenceContext(unitName = "CRUDProyectoriumPU")
    private EntityManager em;

    public CategoryEntityFacadeREST() {
        super(CategoryEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(CategoryEntity entity) {
        try {
            super.create(entity);
        } catch (CreateException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, CategoryEntity entity) {
        try {
            super.edit(entity);
        } catch (UpdateException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        try {
            CategoryEntity category = super.find(id);

            if (category != null) {
                // Eliminar relaciones en la tabla intermedia movie_category
                em.createNativeQuery("DELETE FROM proyectorium.movie_category WHERE category_id = ?")
                        .setParameter(1, id)
                        .executeUpdate();

                // Ahora eliminamos la categoría
                super.remove(category);
            }
        } catch (DeleteException | ReadException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException("Error deleting category: " + ex.getMessage());
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public CategoryEntity find(@PathParam("id") Integer id) {
        try {
            return super.find(id);
        } catch (ReadException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryEntity> findAll() {
        try {
            return super.findAll();
        } catch (ReadException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        try {
            return super.findRange(new int[]{from, to});
        } catch (ReadException ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @GET
    @Path("listCategoriesbyPegi")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryEntity> listCategoriesbyPegi() {
        try {
            return em.createNamedQuery("listCategoriesbyPegi", CategoryEntity.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("listCategoriesbyCreationDate")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryEntity> listCategoriesbyCreationDate() {
        try {
            return em.createNamedQuery("listCategoriesbyCreationDate", CategoryEntity.class).getResultList();
        } catch (Exception ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @GET
    @Path("listCategoriesByDescriptionAndPegi18")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<CategoryEntity> listCategoriesByDescriptionAndPegi18() {
        try {
            return em.createNamedQuery("findCategoriesByDescriptionLengthAndPegi18", CategoryEntity.class)
                    .getResultList();
        } catch (Exception ex) {
            Logger.getLogger(CategoryEntityFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
