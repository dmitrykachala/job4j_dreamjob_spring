package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.store.VacancyRepository;

@Controller
public class VacancyController {
    private final VacancyRepository vacancyRepository = VacancyRepository.instOf();

    @GetMapping("/vacancies")
    public String posts(Model model) {
        model.addAttribute("vacancies", vacancyRepository.findAll());
        return "vacancies/list";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "vacancies/create";
    }
}
