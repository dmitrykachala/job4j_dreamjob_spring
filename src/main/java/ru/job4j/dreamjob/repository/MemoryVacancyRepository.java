package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
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
        save(new Vacancy(0, "Intern Java Developer", "Стажер Java разработчик", true, 1, 0));
        save(new Vacancy(0, "Junior Java Developer", "Младший Java разработчик", true, 1, 0));
        save(new Vacancy(0, "Junior+ Java Developer", "Java разработчик", true, 2, 0));
        save(new Vacancy(0, "Middle Java Developer", "Старший Java разработчик", true, 2, 0));
        save(new Vacancy(0, "Middle+ Java Developer", "Ведущий Java разработчик", true, 2, 0));
        save(new Vacancy(0, "Senior Java Developer", "Главный Java разработчик", true, 3, 0));
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
                    vacancy.getVisible(), vacancy.getCityId(), vacancy.getFileId()
            );
        }) != null;
    }

    public boolean deleteById(int id) {
        return vacancies.remove(id, vacancies.get(id));
    }

}
