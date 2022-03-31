package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список Вакансий [#504842]
 * PostStore. Хранилище Post. Singleton.
 * 4. PostController.savePost. Редактирование вакансии. [#504850]
 * 5. Создания и редактирования кандидатов. [#504858]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger key = new AtomicInteger();

    private CandidateStore() {
        candidates.put(1, new Candidate(key.incrementAndGet(), "Dmitriy Petrov", "Junior Developer"));
        candidates.put(2, new Candidate(key.incrementAndGet(), "Ivan Ivanov", "Middle Developer"));
        candidates.put(3, new Candidate(key.incrementAndGet(), "Sergey Galkin", "Senior Developer"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Candidate create(Candidate candidate) {
        return candidates.computeIfAbsent(key.incrementAndGet(), k -> {
            candidate.setId(k);
            return candidate;
        });
    }

    public Candidate update(Candidate candidate) {
        return candidates.replace(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
