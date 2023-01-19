package ru.job4j.dreamjob.repository;

import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Optional;

public interface VacancyRepository {
    Collection<Vacancy> findAll();

    Optional<Vacancy> findById(int id);

    Vacancy save(Vacancy vacancy);

    boolean update(Vacancy vacancy);

    boolean deleteById(int id);
}
