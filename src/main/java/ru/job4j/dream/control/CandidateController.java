package ru.job4j.dream.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.persistence.CandidateStore;
import ru.job4j.dream.service.CandidateService;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 5. Список Вакансий [#504842]
 * CandidateController контроллер.
 * 8. HTML form. Создание кандидата. [#504845]
 * 4. PostController.savePost. Редактирование вакансии. [#504850]
 * 5. Создания и редактирования кандидатов. [#504858]
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
@Controller
public class CandidateController {
    private final CandidateService candidateService = new CandidateService(CandidateStore.instOf());

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/addCandidate")
    public String addCandidate(Model model) {
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        candidateService.create(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/updateCandidate/{candidateId}")
    public String fromUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate) {
        candidateService.update(candidate);
        return "redirect:/candidates";
    }
}
