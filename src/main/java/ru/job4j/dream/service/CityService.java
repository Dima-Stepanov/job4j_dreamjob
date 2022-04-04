package ru.job4j.dream.service;

import org.springframework.stereotype.Service;
import ru.job4j.dream.model.City;
import ru.job4j.dream.store.CityStore;

import java.util.Collection;

/**
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854]
 * CityService слой сервиса City.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2022
 */
@Service
public class CityService {
    private final CityStore cityStore;

    public CityService(CityStore cityStore) {
        this.cityStore = cityStore;
    }

    public City create(City city) {
        return cityStore.created(city);
    }

    public City update(City city) {
        return cityStore.update(city);
    }


    public City findById(int id) {
        return cityStore.findById(id);
    }

    public Collection<City> findAll() {
        return cityStore.getAllCities();
    }
}
