package ru.job4j.dream.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.persistence.PostStore;
import ru.job4j.dream.service.PostService;


/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 4. Thymeleaf, Циклы. [#504841]
 * PostControl. Контроллер.
 * 3. PostController.savePost. Создание вакансии. [#504849]
 * 4. PostController.savePost. Редактирование вакансии. [#504850]
 * 3.2.4. Архитектура Web приложений.
 * 1. Слоеная архитектура. Принцип DI. [#504851].
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
@Controller
public class PostController {
    private final PostService postService = new PostService(PostStore.instOf());

    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/addPost")
    public String addPost(Model model) {
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        postService.create(post);
        return "redirect:/posts";
    }

    @GetMapping("/updatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post) {
        postService.update(post);
        return "redirect:/posts";
    }
}
