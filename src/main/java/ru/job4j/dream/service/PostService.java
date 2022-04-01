package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PostStore;

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
public class PostService {
    private final PostStore postStore;

    public PostService(PostStore store) {
        this.postStore = store;
    }

    public Post create(Post type) {
        return postStore.create(type);
    }

    public Post update(Post type) {
        return postStore.update(type);
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public Collection<Post> findAll() {
        return postStore.findAll();
    }
}
