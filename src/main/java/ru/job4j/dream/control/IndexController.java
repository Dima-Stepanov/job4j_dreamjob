package ru.job4j.dream.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 3.2.2. Html, Bootstrap, Thymeleaf
 * 6. HTML Link [#504843]
 * IndexController. Контроллер.
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 28.03.2022
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
