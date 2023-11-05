package org.example.service;

import org.example.dao.sqlimpl.unitofwork.UnitOfWork;
import org.example.entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User addUser(User user){
        user.setId(null);

        return UnitOfWork.performWithReturning(
                () -> UnitOfWork
                        .getUserDao()
                        .addUser(user)
        );
    }

    @Override
    public List<User> findUsers(){
        return UnitOfWork.performForOnlyGetWithReturning(
                () -> UnitOfWork
                        .getUserDao()
                        .findUsers()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        return UnitOfWork.performForOnlyGetWithReturning(
                () -> UnitOfWork
                        .getUserDao()
                        .findUserByEmail(email)
        );
    }

    @Override
    public User findUserById(Long id){
        return UnitOfWork.performForOnlyGetWithReturning(
                () -> UnitOfWork
                        .getUserDao()
                        .findUserById(id)
        );
    }

    @Override
    public void deleteUserById(Long id){

        User user = UnitOfWork.performWithReturning(
                () -> UnitOfWork
                        .getUserDao()
                        .findUserById(id)
        );

        UnitOfWork.perform(
                () -> UnitOfWork
                        .getUserDao()
                        .deleteUser(user)
        );
    }

    @Override
    public User updateUser(User user){
        UnitOfWork.perform(
                () -> UnitOfWork
                        .getUserDao()
                        .updateUser(user)
        );

        return user;
    }
}
