package ru.job4j.dreamjob.repository;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger atomicCandidateId = new AtomicInteger(5);

    private MemoryCandidateRepository() {
        candidates.put(1, new Candidate(1, "Jabba Hut", "worm", 2, 0));
        candidates.put(2, new Candidate(2, "Eneken Skywalker", "toddler", 3, 0));
        candidates.put(3, new Candidate(3, "Obi Van Kenobi", "wolf", 1, 0));
        candidates.put(4, new Candidate(4, "Yoda"));
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
/*        return candidates.replace(candidate.getId(),
                candidates.get(candidate.getId()), candidate);*/
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> {
            return new Candidate(
                    oldCandidate.getId(), candidate.getTitle(), candidate.getDescription(),
                    candidate.getCityId(), candidate.getFileId());
        }) != null;
    }

    public boolean deleteById(int id) {
        return candidates.remove(id, candidates.get(id));
    }
}
