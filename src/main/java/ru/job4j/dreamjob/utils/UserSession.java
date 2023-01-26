package ru.job4j.dreamjob.utils;

import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.User;

import javax.servlet.http.HttpSession;

public final class UserSession {
    private Model model;
    private HttpSession session;

    private UserSession(Model model, HttpSession session) {
        this.model = model;
        this.session = session;
        var user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }

    public static UserSession of(Model model, HttpSession session) {
        return new UserSession(model, session);
    }

}
