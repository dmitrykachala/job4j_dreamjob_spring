package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VacancyRepository {
    private static final VacancyRepository INST = new VacancyRepository();

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

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
}
