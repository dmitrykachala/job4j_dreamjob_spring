package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRequestRegistrationPageThenGetPage() {
        var view = userController.getRegistrationPage();

        assertThat(view).isEqualTo("users/registration");
    }

    @Test
    public void whenRegisterThenRedirectVacancies() {
        var user = new User(1, "test1", "desc1");

        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);

        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenLoginThenGetLoginPage() {
        var view = userController.getLoginPage();

        assertThat(view).isEqualTo("users/login");
    }

    @Test
    public void whenLoginUserThenRedirectVacancies() {
        var user = new User(1, "test1", "desc1");

        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();
        assertThat(actualUser).isEqualTo(user);

        var request = new MockHttpServletRequest();
        when(userService.findByEmailAndPassword(actualUser.getEmail(), actualUser.getPassword()))
                .thenReturn(Optional.of(actualUser));
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/vacancies");

    }

    @Test
    public void whenLogoutThenLogoutPage() {
        var request = new MockHttpServletRequest();
        var session = request.getSession();
        var view = userController.logout(session);

        assertThat(view).isEqualTo("redirect:/users/login");
    }

}
