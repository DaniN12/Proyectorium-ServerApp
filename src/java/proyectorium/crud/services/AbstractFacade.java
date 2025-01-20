/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectorium.crud.services;

import java.util.List;
import javax.persistence.EntityManager;
import proyectorium.crud.exceptions.CreateException;
import proyectorium.crud.exceptions.DeleteException;
import proyectorium.crud.exceptions.ReadException;
import proyectorium.crud.exceptions.UpdateException;

/**
 *
 * @author kbilb
 * @author 2dam
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) throws CreateException{
        getEntityManager().persist(entity);
    }

    public void edit(T entity) throws UpdateException{
        getEntityManager().merge(entity);
    }

    public void remove(T entity) throws DeleteException{
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) throws ReadException{
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() throws ReadException{
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) throws ReadException{
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() throws ReadException{
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
