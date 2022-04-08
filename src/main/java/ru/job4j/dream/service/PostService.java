package ru.job4j.dream.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.PostDBStore;
import ru.job4j.dream.store.PostStore;
import ru.job4j.dream.store.Store;

import java.util.Collection;

/**
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 * 2. Связь слоев через Spring DI. [#504856]
 * 3.2.6. DabaBase в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.04.2022
 */
@Service
@ThreadSafe
public class PostService {
    private final Store<Post> postStore;

    public PostService(PostDBStore store) {
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
