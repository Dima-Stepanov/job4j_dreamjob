package ru.job4j.dream.service;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.persistence.PostStore;
import ru.job4j.dream.persistence.Store;

import java.util.Collection;

/**
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.04.2022
 */
public class PostService implements Service<Post> {
    private final Store<Post> postStore;

    public PostService(PostStore store) {
        this.postStore = store;
    }

    @Override
    public Post create(Post type) {
        return postStore.create(type);
    }

    @Override
    public Post update(Post type) {
        return postStore.update(type);
    }

    @Override
    public Post findById(int id) {
        return postStore.findById(id);
    }

    @Override
    public Collection<Post> findAll() {
        return postStore.findAll();
    }
}
