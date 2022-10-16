package com.mykyta.restapi.repository.rest;

import com.mykyta.restapi.model.File;
import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.UserRepository;
import com.mykyta.restapi.util.SessionFactoryInit;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RestUserRepository implements UserRepository {
    @Override
    public List<User> getAll() {
        Query query;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            query = session.createQuery("FROM User", User.class);
            return query.getResultList();
        }
    }

    @Override
    public User getById(Integer id) {
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            return session.get(User.class, id);
        }
    }

    @Override
    public User create(User entity) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public User update(User entity) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            Query q=session.createQuery("update User set name=:n where id=:i");
            q.setParameter("n",entity.getName());
            q.setParameter("i",entity.getId());
            q.executeUpdate();
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void deleteById(Integer id) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            User user = new User();
            user.setId(id);
            session.remove(user);
            transaction.commit();
        }
    }
}
