package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 3.2.6. DabaBase в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]
 * PostDBStore. хранилище объектов Post в базе данных PSQL.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2022
 */
@Repository
public class PostDBStore implements Store<Post> {
    private final BasicDataSource pool;

    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Добавление нового объекта Post в БД.
     *
     * @param post Post.
     * @return Post.
     */
    @Override
    public Post create(Post post) {
        String sql = "INSERT INTO post(name, visible, description, city_id, created)"
                + "VALUES(?, ?, ?, ?, ?)";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setBoolean(2, post.isVisible());
            ps.setString(3, post.getDescription());
            ps.setInt(4, post.getCity().getId());
            ps.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt("post_id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Обновление записи в базе данных.
     *
     * @param post Post
     * @return Post
     */
    @Override
    public Post update(Post post) {
        Post result = null;
        String sql = "UPDATE post SET "
                + "name = ?, visible = ?, description = ?, "
                + "city_id = ?, created = ? "
                + "WHERE post_id = ?";
        try (Connection connect = pool.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.setString(1, post.getName());
            statement.setBoolean(2, post.isVisible());
            statement.setString(3, post.getDescription());
            statement.setInt(4, post.getCity().getId());
            statement.setTimestamp(5, Timestamp.valueOf(post.getCreated()));
            statement.setInt(6, post.getId());
            if (statement.executeUpdate() > 0) {
                result = post;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск записи Post по id.
     *
     * @param id Integer post_id.
     * @return Post.
     */
    @Override
    public Post findById(int id) {
        Post post = null;
        String sql = "SELECT "
                + "p.post_id, p.name, p.visible, p.description, "
                + "c.city_id, c.name AS city_name, p.created "
                + "FROM post AS p "
                + "INNER JOIN city c "
                + "ON c.city_id = p.city_id AND p.post_id = ?";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    post = getPost(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    /**
     * Возвращает все посты из таблицы пост.
     *
     * @return List.
     */
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT "
                + "p.post_id, p.name, p.visible, p.description, "
                + "c.city_id, c.name AS city_name, p.created "
                + "FROM post AS p "
                + "INNER JOIN city c "
                + "ON c.city_id = p.city_id";
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPost(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Метод возвращает Post из ResultSet.
     *
     * @param resultSet ResultSet
     * @return Post
     * @throws SQLException exception.
     */
    private Post getPost(ResultSet resultSet) throws SQLException {
        Post post = new Post(resultSet.getInt("post_id"),
                resultSet.getString("name"),
                resultSet.getBoolean("visible"),
                resultSet.getString("description"),
                new City(resultSet.getInt("city_id"), resultSet.getString("city_name")));
        post.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        return post;
    }
}
