package ru.job4j.dream.service;

import org.springframework.stereotype.Service;
import ru.job4j.dream.model.User;
import ru.job4j.dream.store.Store;

import java.util.Collection;
import java.util.Optional;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 * UserService слой service User.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
@Service
public class UserService {
    private final Store<User> store;

    public UserService(Store<User> store) {
        this.store = store;
    }

    public Optional<User> create(User user) {
        return Optional.ofNullable(store.create(user));
    }

    public User update(User user) {
        return store.update(user);
    }

    public User findById(int id) {
        return store.findById(id);
    }

    public Collection<User> findAll() {
        return store.findAll();
    }
}
