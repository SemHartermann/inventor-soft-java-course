package org.example.dao.sqlimpl.unitofwork;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.GameDao;
import org.example.dao.UserDao;
import org.example.dao.sqlimpl.GameDaoSqlImpl;
import org.example.dao.sqlimpl.UserDaoSqlImpl;

import java.util.function.Supplier;

public class UnitOfWork {
    private static final Logger LOG = LogManager.getLogger(UnitOfWork.class);

    private static final String TRANSACTION_ERROR_MESSAGE = "Error performing JPA operation. Transaction is rolled back";

    private static EntityManagerFactory entityManagerFactory;

    @Getter
    private static GameDao gameDao;

    @Getter
    private static UserDao userDao;

    private UnitOfWork() {
    }

    public static void init(String persistenceUnitName) {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

        gameDao = new GameDaoSqlImpl();
        userDao = new UserDaoSqlImpl();
    }

    public static void close() {
        entityManagerFactory.close();
    }

    public static <T> T performForOnlyGetWithReturning(Supplier<T> operations) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        gameDao.injectEntityManager(entityManager);
        userDao.injectEntityManager(entityManager);

        try {
            return operations.get();
        } catch (Exception e) {
            LOG.error(TRANSACTION_ERROR_MESSAGE, e);
            throw new RuntimeException(TRANSACTION_ERROR_MESSAGE, e);
        } finally {
            entityManager.close();
        }
    }

    public static <T> T performWithReturning(Supplier<T> operations) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        gameDao.injectEntityManager(entityManager);
        userDao.injectEntityManager(entityManager);

        try {
            T result = operations.get();
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            LOG.error(TRANSACTION_ERROR_MESSAGE, e);
            entityManager.getTransaction().rollback();
            throw new RuntimeException(TRANSACTION_ERROR_MESSAGE, e);
        } finally {
            entityManager.close();
        }
    }

    public static void perform(Runnable operations) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        gameDao.injectEntityManager(entityManager);
        userDao.injectEntityManager(entityManager);

        try {
            operations.run();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOG.error(TRANSACTION_ERROR_MESSAGE, e);
            entityManager.getTransaction().rollback();
            throw new RuntimeException(TRANSACTION_ERROR_MESSAGE, e);
        } finally {
            entityManager.close();
        }
    }
}
