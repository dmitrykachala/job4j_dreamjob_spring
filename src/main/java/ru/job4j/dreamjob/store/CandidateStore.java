package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Optional;

public interface CandidateStore {
    Collection<Candidate> findAll();

    Candidate save(Candidate candidate);

    Optional<Candidate> findById(int id);

    boolean update(Candidate candidate);

    boolean deleteById(int id);
}
