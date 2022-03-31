package ru.job4j.dream.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список Вакансий [#504842]
 * Candidate. Модель данных кандидатов.
 * 5. Создания и редактирования кандидатов. [#504858]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
public class Candidate {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    public Candidate(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
