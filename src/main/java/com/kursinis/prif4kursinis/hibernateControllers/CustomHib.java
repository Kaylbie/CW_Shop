package com.kursinis.prif4kursinis.hibernateControllers;

import com.kursinis.prif4kursinis.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class CustomHib extends GenericHib {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;
    public CustomHib(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
        this.entityManagerFactory = entityManagerFactory;
    }

    public User getUserByCredentials(String login, String password) {
        EntityManager em = null;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
            Query q;

            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }
    public void beginTransaction() {
        if (entityManager == null || !entityManager.isOpen()) {
            entityManager = entityManagerFactory.createEntityManager();
        }
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    public void commitTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
        if (entityManager != null) {
            entityManager.close();
        }
    }

    public void rollbackTransaction() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        if (entityManager != null) {
            entityManager.close();
        }
    }
    public Cart findCartByUserId(int userId) {
        EntityManager em = null;
        Cart result = null;
        try {
            em = getEntityManager();
            TypedQuery<Cart> query = em.createQuery(
                    "SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class);
            query.setParameter("userId", userId);
            List<Cart> carts = query.getResultList();
            if (!carts.isEmpty()) {
                // Assuming each user has only one cart
                result = carts.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }
    public Cart findActiveCartByUserId(int userId) {
        Cart activeCart = null;
        EntityManager em = null;
        try {
            em = getEntityManager();
            String queryStr = "SELECT c FROM Cart c WHERE c.user.id = :userId AND c.status = 'Active'";
            TypedQuery<Cart> query = em.createQuery(queryStr, Cart.class);
            query.setParameter("userId", userId);
            List<Cart> carts = query.getResultList();
            if (!carts.isEmpty()) {
                // Assuming there's only one active cart per user
                activeCart = carts.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return activeCart;
    }
    public List<Cart> findCartsByUserIdAndStatuses(int userId, List<String> statuses) {
        List<Cart> carts = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            String queryStr = "SELECT c FROM Cart c WHERE c.user.id = :userId AND c.status IN :statuses";
            TypedQuery<Cart> query = em.createQuery(queryStr, Cart.class);
            query.setParameter("userId", userId);
            query.setParameter("statuses", statuses);
            carts = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return carts;
    }
    public List<Cart> findCartsByUserIdAndStatus(int userId, String status) {
        List<Cart> carts = new ArrayList<>();
        EntityManager em = null;
        try {
            em = getEntityManager();
            String queryStr = "SELECT c FROM Cart c WHERE c.user.id = :userId AND c.status = :status";
            TypedQuery<Cart> query = em.createQuery(queryStr, Cart.class);
            query.setParameter("userId", userId);
            query.setParameter("status", status);
            carts = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return carts;
    }
    public List<Comment> getTopLevelComments(int productId) {
        EntityManager em = null;
        List<Comment> comments = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comment> cq = cb.createQuery(Comment.class);
            Root<Comment> root = cq.from(Comment.class);
            cq.select(root).where(cb.and(
                    cb.isNull(root.get("parentComment")),
                    cb.equal(root.get("product").get("id"), productId)
            ));
            comments = em.createQuery(cq).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return comments;
    }
    public Cart findCartById(int cartId) {
        EntityManager em = null;
        Cart cart = null;
        try {
            em = getEntityManager();
            cart = em.find(Cart.class, cartId);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle or log the exception as needed
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return cart;
    }


    public void deleteProduct(int id) {

        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var product = em.find(Product.class, id);
            //Kai turiu objekta, galiu ji "nulinkint"

            Warehouse warehouse = product.getWarehouse();
            if (warehouse != null) {
                warehouse.getInStockProducts().remove(product);
                em.merge(warehouse);
            }

            em.remove(product);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
