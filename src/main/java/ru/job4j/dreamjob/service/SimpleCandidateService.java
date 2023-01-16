package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.MemoryCandidateStore;

import java.util.Collection;
import java.util.Optional;

public class SimpleCandidateService implements CandidateService {
    private static final SimpleCandidateService INSTANCE = new SimpleCandidateService();

    private final MemoryCandidateStore candidateStore = MemoryCandidateStore.instOf();

    public static SimpleCandidateService getInstance() {
        return INSTANCE;
    }

    @Override
    public Candidate save(Candidate candidate) {
        return candidateStore.save(candidate);
    }

    @Override
    public boolean deleteById(int id) {
        return candidateStore.deleteById(id);
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidateStore.update(candidate);
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return candidateStore.findById(id);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }
}
