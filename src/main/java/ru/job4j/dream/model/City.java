package ru.job4j.dream.model;

import java.util.Objects;

/**
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854]
 * City. модель данных Город.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2022
 */
public class City {
    private int id;
    private String name;

    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        City city = (City) o;
        return id == city.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
