package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Jabba Hut", "worm"));
        candidates.put(2, new Candidate(2, "Eneken Skywalker", "toddler"));
        candidates.put(3, new Candidate(3, "Obi Van Kenobi", "wolf"));
        candidates.put(4, new Candidate(4, "Yoda"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
