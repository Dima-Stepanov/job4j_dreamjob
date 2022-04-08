package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.Main;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 3.2.6. DataBase в Web
 * 3. Тестирование базы данных. Liquibase H2 [#504862]
 * PostDBStore. TEST.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
public class PostDBStoreTest {

    static BasicDataSource pool;

    @BeforeClass
    public static void initPool() throws Exception {
        pool = new Main().loadPool();
        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = Files.readString(Path.of("db/scripts", "insert_citys.sql"));
            statement.execute(sql);
        }
    }

    @AfterClass
    public static void closePool() throws SQLException {
        pool.close();
    }

    @After
    public void wipeTablePost() throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM post;"
                     + "ALTER TABLE post ALTER COLUMN post_id RESTART WITH 1")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreatePost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        store.create(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenCreateTwoPost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        Post post1 = new Post(0, "Dream Job Java", true, "5000$", new City(1, ""));
        store.create(post);
        store.create(post1);
        Post postInDb = store.findById(post.getId());
        Post postInDb1 = store.findById(post1.getId());
        assertThat(postInDb1.getName(), is(post1.getName()));
        assertThat(postInDb.getName(), is(post.getName()));
    }

    @Test
    public void whenUpdatePost() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        store.create(post);
        post.setName("new Java JOB");
        post.setVisible(false);
        store.update(post);
        Post postInDb = store.findById(post.getId());
        assertThat(postInDb.getName(), is(post.getName()));
        assertThat(postInDb.isVisible(), is(post.isVisible()));
    }

    @Test
    public void whenUpdatePostAddTwoPostUpdateSecond() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        Post post1 = new Post(0, "Dream Job Java", true, "5000$", new City(1, ""));
        store.create(post);
        store.create(post1);
        post1.setName("new Dream Job");
        post1.setVisible(false);
        store.update(post1);
        Post post2InDb = store.findById(post1.getId());
        assertThat(post2InDb.getName(), is(post1.getName()));
        assertThat(post2InDb.isVisible(), is(post1.isVisible()));
    }

    @Test
    public void findById() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        Post post1 = new Post(0, "Dream Job Java", true, "5000$", new City(1, ""));
        store.create(post);
        store.create(post1);
        Post postInDb = store.findById(post.getId());
        Post postInDb1 = store.findById(post1.getId());
        assertThat(postInDb1.getId(), is(post1.getId()));
        assertThat(postInDb.getId(), is(post.getId()));
    }

    @Test
    public void findAll() {
        PostDBStore store = new PostDBStore(pool);
        Post post = new Post(0, "Java Job", true, "1000$", new City(2, ""));
        Post post1 = new Post(0, "Dream Job Java", true, "5000$", new City(1, ""));
        store.create(post);
        store.create(post1);
        Collection<Post> expected = List.of(post, post1);
        Collection<Post> result = store.findAll();
        assertThat(result, is(expected));
    }
}