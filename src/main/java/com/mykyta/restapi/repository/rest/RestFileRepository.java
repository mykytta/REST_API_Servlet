package com.mykyta.restapi.repository.rest;

import com.mykyta.restapi.model.File;
import com.mykyta.restapi.repository.FileRepository;
import com.mykyta.restapi.util.SessionFactoryInit;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RestFileRepository implements FileRepository {
    @Override
    public List<File> getAll() {
        Query query;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            query = session.createQuery("FROM File", File.class);
            return query.getResultList();
        }
    }

    @Override
    public File getById(Integer id) {
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            return session.get(File.class, id);
        }
    }

    @Override
    public File create(File entity) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public File update(File entity) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public void deleteById(Integer id) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            File file = new File();
            file.setId(id);
            session.remove(file);
            transaction.commit();
        }
    }
}
