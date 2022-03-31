package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841]
 * PostStore. Хранилище Post. Singleton.
 * 4. PostController.savePost. Редактирование вакансии. [#504850]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger key = new AtomicInteger();

    private PostStore() {
        posts.put(1, new Post(key.incrementAndGet(), "Junior Java Job", "salary 1000$"));
        posts.put(2, new Post(key.incrementAndGet(), "Middle Java Job", "salary 2000$"));
        posts.put(3, new Post(key.incrementAndGet(), "Senior Java Job", "salary 4000$"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Post create(Post post) {
        return posts.computeIfAbsent(key.incrementAndGet(), k -> {
            post.setId(k);
            return post;
        });
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
