package ru.job4j.dream.store;

import java.util.Collection;

/**
 * 3.2.6. DabaBase в Web
 * 1. Подключение к базе в веб приложении. Хранение вакансий. [#504859]
 * Store. интерфейс описывает поведение хранилища слой 'Persistence'.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 05.04.2022
 */
public interface Store<T> {
    T create(T type);

    T update(T type);

    T findById(int id);

    Collection<T> findAll();
}
