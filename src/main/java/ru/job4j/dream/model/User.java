package ru.job4j.dream.model;

import java.util.Objects;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
public class User {
    private int id;
    private String name;


    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
