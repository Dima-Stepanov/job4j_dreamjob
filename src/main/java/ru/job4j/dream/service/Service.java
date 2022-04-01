package ru.job4j.dream.service;

import java.util.Collection;

/**
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 01.04.2022
 */
public interface Service<T> {

    T create(T type);

    T update(T type);

    T findById(int id);

    Collection<T> findAll();
}
