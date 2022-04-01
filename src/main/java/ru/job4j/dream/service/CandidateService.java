package ru.job4j.dream.service;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.persistence.Store;

import java.util.Collection;

/**
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.04.2022
 */
public class CandidateService implements Service<Candidate> {
    private final Store<Candidate> storeCandidate;

    public CandidateService(Store<Candidate> store) {
        this.storeCandidate = store;
    }

    @Override
    public Candidate create(Candidate type) {
        return storeCandidate.create(type);
    }

    @Override
    public Candidate update(Candidate type) {
        return storeCandidate.update(type);
    }

    @Override
    public Candidate findById(int id) {
        return storeCandidate.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return storeCandidate.findAll();
    }
}
