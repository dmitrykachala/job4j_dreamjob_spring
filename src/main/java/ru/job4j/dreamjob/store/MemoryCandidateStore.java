package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryCandidateStore {
    private static final MemoryCandidateStore INST = new MemoryCandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger atomicCandidateId = new AtomicInteger(5);

    private MemoryCandidateStore() {
        candidates.put(1, new Candidate(1, "Jabba Hut", "worm"));
        candidates.put(2, new Candidate(2, "Eneken Skywalker", "toddler"));
        candidates.put(3, new Candidate(3, "Obi Van Kenobi", "wolf"));
        candidates.put(4, new Candidate(4, "Yoda"));
    }

    public static MemoryCandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate save(Candidate candidate) {
        candidate.setId(atomicCandidateId.incrementAndGet());
        return candidates.put(candidate.getId(), candidate);
    }

    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    public boolean update(Candidate candidate) {
        return candidates.replace(candidate.getId(),
                candidates.get(candidate.getId()), candidate);
    }

    public boolean deleteById(int id) {
        return candidates.remove(id, candidates.get(id));
    }
}
