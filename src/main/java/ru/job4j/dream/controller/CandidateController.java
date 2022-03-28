package ru.job4j.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dream.store.CandidateStore;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список Вакансий [#504842]
 * CandidateController контроллер.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
@Controller
public class CandidateController {
    private final CandidateStore candidateStore = CandidateStore.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateStore.findAll());
        return "candidates";
    }
}
