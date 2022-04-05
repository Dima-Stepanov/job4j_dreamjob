package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.CandidateStore;
import ru.job4j.dream.store.Store;

import java.util.Collection;

/**
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 * 2. Связь слоев через Spring DI. [#504856]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.04.2022
 */
@Service
@ThreadSafe
public class CandidateService {
    private final Store<Candidate> storeCandidate;

    public CandidateService(CandidateStore store) {
        this.storeCandidate = store;
    }

    public Candidate create(Candidate type) {
        return storeCandidate.create(type);
    }

    public Candidate update(Candidate type) {
        return storeCandidate.update(type);
    }

    public Candidate findById(int id) {
        return storeCandidate.findById(id);
    }

    public Collection<Candidate> findAll() {
        return storeCandidate.findAll();
    }
}
