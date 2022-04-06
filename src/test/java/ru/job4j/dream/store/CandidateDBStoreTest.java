package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.dream.Main;
import ru.job4j.dream.model.Candidate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * 3.2.6. DataBase в Web
 * 3. Тестирование базы данных. Liquibase H2 [#504862]
 * CandidateDBStore. TEST.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
public class CandidateDBStoreTest {

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
    public void wipeTableCandidate() throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM candidate;"
                     + "ALTER TABLE candidate ALTER COLUMN candidate_id RESTART WITH 1")) {
            statement.execute();
        }
    }

    @Test
    public void whenCreateCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(0, "Nikita Tomato", "I am tomato senior tomato", new byte[]{1, 2, 3});
        store.create(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
    }

    @Test
    public void whenCreateTwoCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(0, "Nikita Tomato", "I am tomato senior tomato", new byte[]{1, 2, 3});
        Candidate candidate1 = new Candidate(0, "Sasha Junior", "500$ my pay", new byte[]{1, 2, 3});
        store.create(candidate);
        store.create(candidate1);
        Candidate candidateInDb = store.findById(candidate.getId());
        Candidate candidateInDb2 = store.findById(candidate1.getId());
        assertThat(candidateInDb.getName(), is(candidate.getName()));
        assertThat(candidateInDb2.getName(), is(candidate1.getName()));
    }

    @Test
    public void whenUpdateCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(0, "Nikita Tomato", "I am tomato senior tomato", new byte[]{1, 2, 3});
        store.create(candidate);
        candidate.setDescription("java java jav");
        store.update(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getDescription(), is(candidate.getDescription()));
    }

    @Test
    public void whenFindByIdCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(0, "Nikita Tomato", "I am tomato senior tomato", new byte[]{1, 2, 3});
        Candidate candidate1 = new Candidate(0, "Sasha Junior", "500$ my pay", new byte[]{1, 2, 3});
        store.create(candidate);
        store.create(candidate1);
        Candidate candidateInDb = store.findById(candidate.getId());
        Candidate candidateInDb2 = store.findById(candidate1.getId());
        assertThat(candidateInDb.getId(), is(candidate.getId()));
        assertThat(candidateInDb2.getId(), is(candidate1.getId()));
    }

    @Test
    public void whenFindAllCandidate() {
        CandidateDBStore store = new CandidateDBStore(pool);
        Candidate candidate = new Candidate(0, "Nikita Tomato", "I am tomato senior tomato", new byte[]{1, 2, 3});
        Candidate candidate1 = new Candidate(0, "Sasha Junior", "500$ my pay", new byte[]{1, 2, 3});
        store.create(candidate);
        store.create(candidate1);
        Collection<Candidate> expected = List.of(candidate, candidate1);
        Collection<Candidate> result = store.findAll();
        assertThat(result, is(expected));
    }
}