package org.example.dao.sqlimpl;

import jakarta.persistence.EntityManager;
import org.example.dao.UserDao;
import org.example.entity.User;

import java.util.List;

public class UserDaoSqlImpl implements UserDao {
    private EntityManager entityManager;

    public void injectEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User addUser(User user) {
        entityManager.persist(user);

        return user;
    }

    @Override
    public List<User> findUsers() {
        return entityManager
                .createQuery("select g from User g", User.class)
                .getResultList();
    }

    @Override
    public User findUserByEmail(String email) {
        return entityManager
                .createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void deleteUser(User user) {
        User mergedUser = entityManager.merge(user);

        entityManager.remove(mergedUser);
    }
}
