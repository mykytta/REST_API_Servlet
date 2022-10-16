package com.mykyta.restapi.repository.rest;

import com.mykyta.restapi.model.Event;
import com.mykyta.restapi.model.File;
import com.mykyta.restapi.model.User;
import com.mykyta.restapi.repository.EventRepository;
import com.mykyta.restapi.util.SessionFactoryInit;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RestEventRepository implements EventRepository {
    @Override
    public List<Event> getAll() {
        Query query;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            query = session.createQuery("FROM Event ", Event.class);
            return query.getResultList();
        }
    }

    @Override
    public Event getById(Integer id) {
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            return session.get(Event.class, id);
        }
    }

    @Override
    public Event create(Event entity) {
        Transaction transaction;
        try (Session session = SessionFactoryInit.getInstance().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        }
        return entity;
    }

    @Override
    public Event update(Event entity) {
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
            Event event = new Event();
            event.setId(id);
            session.remove(event);
            transaction.commit();
        }
    }
}
