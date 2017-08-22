package imdb.controller;

import imdb.bindingModel.FilmBindingModel;
import imdb.entity.Film;
import imdb.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FilmController {
    private final FilmRepository filmRepository;

    @Autowired
    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Film> films = this.filmRepository.findAll();

        model.addAttribute("films", films);
        model.addAttribute("view", "film/index");

        return "base-layout";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("view", "film/create");

        return "base-layout";
    }

    @PostMapping("/create")
    public String createProcess(FilmBindingModel filmBindingModel) {
        Film film = new Film(filmBindingModel.getName(), filmBindingModel.getGenre(), filmBindingModel.getDirector(), filmBindingModel.getYear());
        this.filmRepository.saveAndFlush(film);

        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public  String delete(Model model, @PathVariable Integer id) {
        Film film = this.filmRepository.findOne(id);

        model.addAttribute("view", "film/delete");
        model.addAttribute("film", film);

        return "base-layout";
    }

    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id) {
        Film film = this.filmRepository.findOne(id);
        this.filmRepository.delete(film);
        this.filmRepository.flush();

        return  "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id) {
        Film film = this.filmRepository.findOne(id);
        model.addAttribute("view", "film/edit");
        model.addAttribute("film", film);

        return "base-layout";
    }

    @PostMapping("/edit/{id}")
    public String editProcess(@PathVariable Integer id, FilmBindingModel filmBindingModel) {
        Film film = this.filmRepository.findOne(id);
        film.setName(filmBindingModel.getName());
        film.setGenre(filmBindingModel.getGenre());
        film.setDirector(filmBindingModel.getGenre());
        film.setYear(filmBindingModel.getYear());

        this.filmRepository.saveAndFlush(film);

        return "redirect:/";
    }
}
