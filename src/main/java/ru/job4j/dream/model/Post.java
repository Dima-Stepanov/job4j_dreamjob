package ru.job4j.dream.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841]
 * 3.2.5. Формы
 * 1. Формы. Поля ввода.   [#504853 #283619]
 * 2. Формы. Списки. [#504854]
 * Post. Модель данных ВАКАНСИЯ.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
public class Post implements Serializable {
    private int id;
    private String name;
    private Boolean visible;
    private String description;
    private City city;
    private LocalDateTime created = LocalDateTime.now().withNano(0);


    public Post(int id, String name, Boolean visible, String description, City city) {
        this.id = id;
        this.name = name;
        this.visible = visible;
        this.description = description;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isVisible() {
        if (visible == null) {
            visible = false;
        }
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
