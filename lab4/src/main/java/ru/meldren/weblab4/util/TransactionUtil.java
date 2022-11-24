package ru.meldren.weblab4.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class TransactionUtil {

    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    public <T> T initWithCallback(Function<EntityManager, T> transaction) {
        EntityManager manager = entityManagerFactory.createEntityManager();
        try {
            manager.getTransaction().begin();
            T object = transaction.apply(manager);
            manager.getTransaction().commit();
            return object;
        } catch (Exception ex) {
            if (manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            System.out.println("An exception occurred during the transaction.");
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return null;
    }

    public void init(Consumer<EntityManager> transaction) {
        initWithCallback(manager -> {
            transaction.accept(manager);
            return null;
        });
    }

}
