package ru.job4j.dreamjob.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.configuration.DatasourceConfiguration;
import ru.job4j.dreamjob.model.User;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {
    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);

    }

    @AfterEach
    void clearUsers() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    void whenSaveIsSuccessful() {
        User us = new User("title@t.tt", "q", "pass");
        Optional<User> ret = sql2oUserRepository.save(us);
        assert ret.isPresent();
        var savedUser = sql2oUserRepository.findByEmail("title@t.tt");
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(ret);
    }

    @Test
    void whenSaveSameEmailThenFail() {
        User user = new User("title@t.tt", "q", "pass");
        User userD = new User("title@t.tt", "qq", "pass2");
        Optional<User> res = sql2oUserRepository.save(user);
        assert res.isPresent();
        var savedUser = sql2oUserRepository.findByEmail("title@t.tt");
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(res);
        Optional<User> res2 = sql2oUserRepository.save(userD);
        assert res2.isEmpty();
    }
}
