package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.Main;
import ru.job4j.dream.model.City;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 3.2.6. DataBase в Web
 * 3. Тестирование базы данных. Liquibase H2 [#504862]
 * CityDBStore. TEST.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.04.2022
 */
public class CityDBStoreTest {

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
    public void wipeTableCity() throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM city;"
                     + "ALTER TABLE city ALTER COLUMN city_id RESTART WITH 1;")) {
            statement.execute();
        }
    }


    @Test
    public void whenCreateCity() {
        CityDBStore store = new CityDBStore(pool);
        City city = new City(0, "Москва");
        store.create(city);
        City cityInDb = store.findById(city.getId());
        assertThat(cityInDb.getName(), is(city.getName()));
    }

    @Test
    public void whenCreateTwoCiteThenGetIdTwo() {
        CityDBStore store = new CityDBStore(pool);
        City city = new City(0, "Москва");
        City city1 = new City(0, "Караганда");
        store.create(city);
        store.create(city1);
        assertThat(store.findById(city1.getId()).getName(), is(city1.getName()));
    }

    @Test
    public void whenUpdateCity() {
        CityDBStore store = new CityDBStore(pool);
        City city = new City(0, "Москва");
        store.create(city);
        city.setName("Спб");
        store.update(city);
        assertThat(store.findById(city.getId()).getName(), is("Спб"));
    }

    @Test
    public void whenFindByIdCity() {
        CityDBStore store = new CityDBStore(pool);
        City city = new City(0, "Москва");
        City city1 = new City(0, "Караганда");
        City city2 = new City(0, "Томск");
        store.create(city);
        store.create(city1);
        store.create(city2);
        assertThat(store.findById(city2.getId()), is(city2));
    }

    @Test
    public void whenFindAllCity() {
        CityDBStore store = new CityDBStore(pool);
        City city = new City(0, "Москва");
        City city1 = new City(0, "Караганда");
        City city2 = new City(0, "Томск");
        store.create(city);
        store.create(city1);
        store.create(city2);
        Collection<City> expected = List.of(city, city1, city2);
        Collection<City> result = store.findAll();
        assertThat(result, is(expected));
    }
}