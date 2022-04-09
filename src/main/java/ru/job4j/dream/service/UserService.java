package ru.job4j.dream.service;

import org.springframework.stereotype.Service;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.Store;
import ru.job4j.dream.store.UserDBStore;

import java.util.Collection;
import java.util.Optional;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 * 3.2.7. Авторизация и аутентификация
 * 1. Страница login.html [#504863]
 * UserService слой service User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
@Service
public class UserService {
    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public Optional<User> create(User user) {
        return store.create(user);
    }

    public User update(User user) {
        return store.update(user);
    }

    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    public Collection<User> findAll() {
        return store.findAll();
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        return store.findUserByEmailAndPwd(email, password);
    }
}
