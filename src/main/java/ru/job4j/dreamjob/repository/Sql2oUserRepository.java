package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.User;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oUserRepository implements UserRepository {

    private final Sql2o sql2o;

    public Sql2oUserRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<User> save(User user) {
        try (var connection = sql2o.open()) {
            var sql = """
                      INSERT INTO users(email, password, name)
                      VALUES (:email, :password, :name)
                      """;
            var query = connection.createQuery(sql, true)
                    .addParameter("email", user.getEmail())
                    .addParameter("password", user.getPassword())
                    .addParameter("name", user.getName());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            user.setId(generatedId);
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<User> findByEmail(String email) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email ");
            query.addParameter("email", email);
            var user = query.setColumnMappings(Candidate.COLUMN_MAPPING)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM users WHERE id = :id");
            query.addParameter("id", id);
            var affectedRows = query.executeUpdate().getResult();
            return affectedRows > 0;
        }
    }

    public Collection<User> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users");
            return query.setColumnMappings(User.COLUMN_MAPPING)
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM users WHERE email = :email "
                                               + "AND password = :password");
            query.addParameter("email", email)
                    .addParameter("password", password);
            var user = query.setColumnMappings(Candidate.COLUMN_MAPPING)
                    .executeAndFetchFirst(User.class);
            return Optional.ofNullable(user);
        }
    }

}
