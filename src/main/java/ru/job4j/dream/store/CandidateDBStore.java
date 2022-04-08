package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.Candidate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 3.2.6. DabaBase в Web
 * 2. CandidateDbStore. Хранение кандидатов. [#504861]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 06.04.2022
 */
@Repository
public class CandidateDBStore implements Store<Candidate> {

    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    /**
     * Метод добавляет модель в хранилище БД в таблицу candidate.
     *
     * @param candidate Candidate.
     * @return Candidate.
     */
    @Override
    public Candidate create(Candidate candidate) {
        String sql = "INSERT INTO candidate(name, description, photo, created)"
                + "VALUES (?, ?, ?, ?);";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, candidate.getName());
            statement.setString(2, candidate.getDescription());
            statement.setBytes(3, candidate.getPhoto());
            statement.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            statement.execute();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    candidate.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    /**
     * Метод обновляет модель в хранилище БД в таблицу candidate.
     *
     * @param candidate
     * @return
     */
    @Override
    public Candidate update(Candidate candidate) {
        Candidate result = null;
        String sql = "UPDATE candidate SET "
                + "name = ?, description = ?, photo =?, created = ?"
                + "WHERE candidate_id = ?;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, candidate.getName());
            statement.setString(2, candidate.getDescription());
            statement.setBytes(3, candidate.getPhoto());
            statement.setTimestamp(4, Timestamp.valueOf(candidate.getCreated()));
            statement.setInt(5, candidate.getId());
            if (statement.executeUpdate() > 0) {
                result = candidate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск модели Candidate по id в БД в таблице candidate.
     *
     * @param id Integer
     * @return Candidate.
     */
    @Override
    public Candidate findById(int id) {
        Candidate candidate = null;
        String sql = "SELECT * FROM candidate WHERE candidate_id = ?;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeQuery();
            try (ResultSet resultSet = statement.getResultSet()) {
                if (resultSet.next()) {
                    candidate = getCandidate(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    /**
     * Возвращает все записи из БД таблицы candidate.
     *
     * @return Collection.
     */
    @Override
    public Collection<Candidate> findAll() {
        Collection<Candidate> result = new ArrayList<>();
        String sql = "SELECT * FROM candidate;";
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeQuery();
            try (ResultSet resultSet = statement.getResultSet()) {
                while (resultSet.next()) {
                    result.add(getCandidate(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод создает модель Candidate из результата запроса ResultSet.
     *
     * @param resultSet ResultSet.
     * @return Candidate.
     * @throws SQLException exception.
     */
    private Candidate getCandidate(ResultSet resultSet) throws SQLException {
        Candidate candidate = new Candidate(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getBytes(4)
        );
        candidate.setCreated(resultSet.getTimestamp(5).toLocalDateTime());
        return candidate;
    }
}
