package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class VacancyRepository {
    private static final VacancyRepository INST = new VacancyRepository();

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private final AtomicInteger atomicVacancyId = new AtomicInteger(5);

    private VacancyRepository() {
        vacancies.put(1, new Vacancy(1, "Junior Java Job", "origin"));
        vacancies.put(2, new Vacancy(2, "Middle Java Job", "current"));
        vacancies.put(3, new Vacancy(3, "Senior Java Job", "future"));
        vacancies.put(4, new Vacancy(4, "Junior Java Job"));
    }

    public static VacancyRepository instOf() {
        return INST;
    }

    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    public void save(Vacancy vacancy) {
        vacancy.setId(atomicVacancyId.incrementAndGet());
        vacancies.put(vacancy.getId(), vacancy);
    }

    public boolean update(Vacancy vacancy) {
        return vacancies.replace(vacancy.getId(),
                vacancies.get(vacancy.getId()), vacancy);
    }

    public boolean deleteById(int id) {
        return vacancies.remove(id, vacancies.get(id));
    }

}
