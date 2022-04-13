package ru.job4j.dream.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;
import ru.job4j.dream.service.CityService;
import ru.job4j.dream.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * 3.2.8. Spring Test, Mockito
 * 2. Тестируем PostController [#504867]
 *
 * @author Dmitry Stepanov, user Dmitry
 * @since 13.04.2022
 */
public class PostControllerTest {
    @Test
    public void whenPostsPostGet() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post1", true, "description1", new City(1, "")),
                new Post(2, "New post2", false, "description2", new City(2, ""))
        );
        User user = new User(1, "a@a.ru", "aaa");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        when(postService.findAll()).thenReturn(posts);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("user", user);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenAddPostGet() {
        User user = new User(1, "a@a.ru", "aaa");
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        List<City> cities = List.of(
                new City(1, "Z"),
                new City(2, "V"));
        CityService cityService = mock(CityService.class);
        when(cityService.findAll()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cityService.findAll());
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenCreatePostPost() {
        CityService cityService = mock(CityService.class);
        PostService postService = mock(PostService.class);
        Post post = new Post(1, "New post1", true, "description1", new City(1, ""));
        when(postService.create(post)).thenReturn(post);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(post);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFormUpdatePostGet() {
        Post post = new Post(1,
                "New post1",
                true,
                "description1",
                new City(1, ""));
        int id = 1;
        List<City> cities = List.of(
                new City(1, "Z"),
                new City(2, "V"));
        User user = new User(1, "a@a.ru", "aaa");
        PostService postService = mock(PostService.class);
        when(postService.findById(id)).thenReturn(post);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("user")).thenReturn(user);
        CityService cityService = mock(CityService.class);
        when(cityService.findAll()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        Model model = mock(Model.class);
        String page = postController.formUpdatePost(model, id, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("post", postService.findById(id));
        verify(model).addAttribute("cities", cityService.findAll());
        assertThat(page, is("updatePost"));
    }

    @Test
    public void whenUpdatePostPost() {
        Post post = new Post(1,
                "New post1",
                true,
                "description1",
                new City(1, ""));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.findById(post.getCity().getId())).thenReturn(new City(1, "Z"));
        when(postService.update(post)).thenReturn(post);
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(post);
        assertThat(page, is("redirect:/posts"));
    }
}