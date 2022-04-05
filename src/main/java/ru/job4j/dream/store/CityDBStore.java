package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 3.2.6. DabaBase в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]
 * CityDBStore. хранилище объектов City в базе данных PSQL.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 05.04.2022
 */
@Repository
public class CityDBStore implements Store<City> {
    private final BasicDataSource pool;

    public CityDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление города в базу данных.
     *
     * @param city City.
     * @return City.
     */
    @Override
    public City create(City city) {
        String sql = "INSETR INTTO city(name) VALUES(?)";
        try (Connection connection = pool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, city.getName());
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    city.setId(resultSet.getInt("city_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * Обновление города.
     *
     * @param city City.
     * @return City.
     */
    @Override
    public City update(City city) {
        City result = null;
        String sql = "UPDATE city SET name = ? WHERE city_id = ?";
        try (Connection connection = pool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, city.getName());
            statement.setInt(2, city.getId());
            if (statement.executeUpdate() > 0) {
                result = city;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск города по ID
     *
     * @param id Integer ID
     * @return City.
     */
    @Override
    public City findById(int id) {
        City city = new City();
        String sql = "SELECT * FROM city WHERE city_id = ?";
        try (Connection connection = pool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    city = getCity(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * Поиск всех City в таблице.
     *
     * @return Collection.
     */
    @Override
    public Collection<City> findAll() {
        Collection<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM city";
        try (Connection connection = pool.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cities.add(getCity(resultSet));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    /**
     * Метод возвращает City из ResultSet.
     *
     * @param resultSet
     * @return City.
     * @throws SQLException exception.
     */
    private City getCity(ResultSet resultSet) throws SQLException {
        City city = new City(resultSet.getInt("city_id"), resultSet.getString("name"));
        return city;
    }
}
