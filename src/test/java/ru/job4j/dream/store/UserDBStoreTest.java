package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.Main;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 * 3.2.7. Авторизация и аутентификация
 * 1. Страница login.html [#504863]
 * UserDBStore. Test.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
public class UserDBStoreTest {
    static BasicDataSource pool;

    @BeforeClass
    public static void initPool() {
        pool = new Main().loadPool();
    }

    @AfterClass
    public static void closePool() throws SQLException {
        pool.close();
    }

    @After
    public void wipeTableUsers() throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM users;"
                     + "ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateUser() {
        User user = new User(0, "java@java.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        User userInDb = store.findById(user.getId()).get();
        assertThat(userInDb.getEmail(), is(user.getEmail()));
    }

    @Test
    public void whenUpdateUser() {
        User user = new User(0, "java@java.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        user.setEmail("yandex@yandex.ru");
        store.update(user);
        User userInDb = store.findById(user.getId()).get();
        assertThat(userInDb.getEmail(), is(user.getEmail()));
    }

    @Test
    public void whenFindByIdUser() {
        User user = new User(0, "java@java.ru", "qwerty");
        User user1 = new User(0, "mail@mail.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        store.create(user1);
        User userInDb1 = store.findById(user1.getId()).get();
        assertThat(userInDb1.getId(), is(user1.getId()));
    }

    @Test
    public void whenFindAllUsers() {
        User user = new User(0, "java@java.ru", "qwerty");
        User user1 = new User(0, "mail@mail.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        store.create(user1);
        Collection<User> expected = List.of(user, user1);
        Collection<User> result = store.findAll();
        assertThat(result, is(expected));
    }

    @Test
    public void whenFidUserByEmailAndPasswordThanOk() {
        User user = new User(0, "java@java.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        Optional<User> userFind = store.findUserByEmailAndPwd("java@java.ru", "qwerty");
        assertThat(userFind.get().getEmail(), is(user.getEmail()));
        assertThat(userFind.get().getPassword(), is(user.getPassword()));
    }

    @Test
    public void whenFindUserByEmailAndPasswordThenEmailOkPasswordFailResultIsEmpty() {
        User user = new User(0, "java@java.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        Optional<User> userFind = store.findUserByEmailAndPwd("java@java.ru", "1234");
        assertFalse(userFind.isPresent());
    }

    @Test
    public void whenFindUserByEmailAndPasswordThenEmailFailPasswordOkResultIsEmpty() {
        User user = new User(0, "java@java.ru", "qwerty");
        UserDBStore store = new UserDBStore(pool);
        store.create(user);
        Optional<User> userFind = store.findUserByEmailAndPwd("mail@mail.com", "qwerty");
        assertFalse(userFind.isPresent());
    }
}