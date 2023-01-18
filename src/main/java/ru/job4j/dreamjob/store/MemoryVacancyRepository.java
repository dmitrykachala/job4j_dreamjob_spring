package ru.job4j.dreamjob.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final Map<Integer, Vacancy> vacancies = new ConcurrentHashMap<>();

    private final AtomicInteger atomicVacancyId = new AtomicInteger(5);

    private MemoryVacancyRepository() {
        vacancies.put(1, new Vacancy(1, "Junior Java Job", "origin", true, 1));
        vacancies.put(2, new Vacancy(2, "Middle Java Job", "current", true, 1));
        vacancies.put(3, new Vacancy(3, "Senior Java Job", "future", true, 1));
        vacancies.put(4, new Vacancy(4, "Junior Java Job"));
    }

    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(atomicVacancyId.incrementAndGet());
        return vacancies.put(vacancy.getId(), vacancy);
    }

    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(
                    oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getVisible(), vacancy.getCityId()
            );
        }) != null;
    }

    public boolean deleteById(int id) {
        return vacancies.remove(id, vacancies.get(id));
    }

}
