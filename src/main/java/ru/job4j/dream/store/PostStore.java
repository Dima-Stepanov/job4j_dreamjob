package ru.job4j.dream.store;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.dream.model.Post;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841]
 * PostStore. Хранилище Post. Singleton.
 * 4. PostController.savePost. Редактирование вакансии. [#504850]
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 * 2. Связь слоев через Spring DI. [#504856]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
@Repository
@ThreadSafe
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger key = new AtomicInteger();

    private PostStore() {
        posts.computeIfAbsent(key.incrementAndGet(),
                k -> new Post(k, "Junior Java Job", true, "salary 1000$"));
        posts.computeIfAbsent(key.incrementAndGet(),
                k -> new Post(k, "Middle Java Job", false, "salary 2000$"));
        posts.computeIfAbsent(key.incrementAndGet(),
                k -> new Post(k, "Senior Java Job", false, "salary 4000$"));
    }

    public Post create(Post post) {
        return posts.computeIfAbsent(key.incrementAndGet(),
                k -> new Post(k, post.getName(), post.isVisible(), post.getDescription()));
    }

    public Post update(Post post) {
        return posts.replace(post.getId(), post);
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
