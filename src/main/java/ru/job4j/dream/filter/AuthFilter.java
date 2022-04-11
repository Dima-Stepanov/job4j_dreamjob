package ru.job4j.dream.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 3.2.7. Авторизация и аутентификация
 * 3. Filter [#504865]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 11.04.2022
 */
@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        if (uri.endsWith("loginPage") || uri.endsWith("login")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/loginPage");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
