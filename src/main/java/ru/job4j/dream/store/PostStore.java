package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841]
 * PostStore. Хранилище Post. Singleton.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */

public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "salary 1000$"));
        posts.put(2, new Post(2, "Middle Java Job", "salary 2000$"));
        posts.put(3, new Post(3, "Senior Java Job", "salary 4000$"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }
}
