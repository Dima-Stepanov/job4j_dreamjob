package ru.job4j.dream.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.UserService;

import java.util.Optional;

/**
 * 3.2.6. DabaBase в Web
 * 4. Многопоточность в базе данных [#504860]
 * UserController контролер.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 07.04.2022
 */
@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/addUser")
    public String addUser(Model model) {
        return "addUser";
    }

    @PostMapping("/createUser")
    public String createUser(Model model, @ModelAttribute User user) {
        Optional<User> regUser = Optional.ofNullable(userService.create(user));
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует");
            return "redirect:/fail";
        }
        return "redirect:/users";
    }
}
