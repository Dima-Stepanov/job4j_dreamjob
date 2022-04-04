package ru.job4j.dream.store;

import org.springframework.stereotype.Repository;
import ru.job4j.dream.model.City;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 3.2.5. Формы
 * 2. Формы. Списки. [#504854]
 * CityStore слой persistence хранилище City.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 04.04.2022
 */
@Repository
public class CityStore {
    private final ConcurrentHashMap<Integer, City> cities = new ConcurrentHashMap<>();
    private final AtomicInteger key = new AtomicInteger();

    public CityStore() {
        cities.computeIfAbsent(key.incrementAndGet(),
                k -> new City(k, "Москва"));
        cities.computeIfAbsent(key.incrementAndGet(),
                k -> new City(k, "СПб"));
        cities.computeIfAbsent(key.incrementAndGet(),
                k -> new City(k, "Краснодар"));
    }

    public City created(City city) {
        return cities.computeIfAbsent(key.incrementAndGet(),
                k -> new City(k, city.getName()));
    }

    public City update(City city) {
        return cities.replace(city.getId(), city);
    }

    public City findById(int id) {
        return cities.get(id);
    }

    public Collection<City> getAllCities() {
        return cities.values();
    }
}
