package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateStore;

import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleCandidateService implements CandidateService {

    private final CandidateStore candidateStore;

    public SimpleCandidateService(CandidateStore candidateStore) {
        this.candidateStore = candidateStore;
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
