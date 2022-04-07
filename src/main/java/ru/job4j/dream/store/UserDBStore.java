package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 * UserDBStore. Хранилище объектов User в базе данных PSQL.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
@Repository
public class UserDBStore implements Store<User> {
    BasicDataSource pool;

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление нового объекта User в БД.
     *
     * @param user User.
     * @return User.
     */
    @Override
    public User create(User user) {
        User result = null;
        String sql = "INSERT INTO users(name) VALUES(?);";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getName());
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("user_id"));
                    result = user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Обновление объекта Post по id в БД.
     *
     * @param user User
     * @return User.
     */
    @Override
    public User update(User user) {
        User result = null;
        String sql = "UPDATE users SET name = ? WHERE user_id = ?;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setInt(2, user.getId());
            if (statement.executeUpdate() > 0) {
                result = user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск объекта Post в БД по Id.
     *
     * @param id int
     * @return User.
     */
    @Override
    public User findById(int id) {
        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = getUser(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Возвращает все модели User из таблиц users.
     *
     * @return Collection.
     */
    @Override
    public Collection<User> findAll() {
        Collection<User> result = new ArrayList<>();
        String sql = "SELECT * FROM users;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getUser(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод возвращает модель User из ResultSet.
     *
     * @param resultSet ResultSet
     * @return User
     * @throws SQLException exception.
     */
    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("user_id"),
                resultSet.getString("name")
        );
    }
}
