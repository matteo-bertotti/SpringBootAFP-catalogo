package it.tn.mat.catalogo.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.tn.mat.catalogo.domain.Film;
import it.tn.mat.catalogo.domain.FilmForm;
import it.tn.mat.catalogo.domain.Genere;
import it.tn.mat.catalogo.services.FilmService;
import jakarta.validation.Valid;

@Controller
public class FilmController {
    
    @Autowired
    private FilmService filmService;

    // Elenco film - rotta: / 
    @GetMapping
    public ModelAndView elencoFilm(
            @RequestParam(name = "sort", defaultValue = "titolo") String sort,
            @RequestParam(name = "dir", defaultValue = "asc") String dir) {
        return new ModelAndView("film-list")
                .addObject("films", filmService.elencoCatalogoOrdinato(sort, dir))
                .addObject("sort", sort)
                .addObject("dir", dir);
    }

    // Form per aggiunta film - rotta: /new
    @GetMapping("/new")
    public ModelAndView newFilmForm() {
        return new ModelAndView("film-form").addObject("filmForm", new FilmForm());
    }

    // Aggiunta film - rotta: /new
    @PostMapping("/new")
    public ModelAndView newFilm(@ModelAttribute @Valid FilmForm filmForm, BindingResult br, RedirectAttributes attr) {
        if (br.hasErrors()) {
            return new ModelAndView("film-form").addObject("filmForm", filmForm);
        }
        Film f = filmService.newFilm(filmForm);
        attr.addFlashAttribute("newFilm", "Film aggiunto con successo!");
        return new ModelAndView("redirect:/film?id=" + f.getId());
    }

    // Dettaglio film per id - rotta: /film?id=UUID
    @GetMapping(path = "film", params = "id")
    public ModelAndView filmPerId(@RequestParam("id") UUID id) {
        Optional<Film> filmOpt = Optional.ofNullable(filmService.filmPerId(id));
        if (filmOpt.isPresent()) {
            return new ModelAndView("film-detail").addObject("film", filmOpt.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film non trovato");
        }
    }

    // Form modifica film - rotta: /film/edit/{id}
    @GetMapping("film/edit/{id}")
    public ModelAndView editFilmForm(@PathVariable("id") UUID id) {
        Film film = filmService.filmPerId(id);

        FilmForm filmForm = new FilmForm();
        filmForm.setTitolo(film.getTitolo());
        filmForm.setAutore(film.getAutore());
        filmForm.setAnnoPubblicazione(film.getAnnoPubblicazione());
        try {
            filmForm.setGenere(Genere.valueOf(film.getGenere()));
        } catch (IllegalArgumentException e) {
            filmForm.setGenere(null);
        }

        return new ModelAndView("film-edit")
                .addObject("filmForm", filmForm)
                .addObject("filmId", id);
    }

    // Salvataggio modifica film - rotta: /film/edit/{id}
    @PostMapping("film/edit/{id}")
    public ModelAndView editFilm(@PathVariable("id") UUID id, @ModelAttribute @Valid FilmForm filmForm,
            BindingResult br, RedirectAttributes attr) {
        if (br.hasErrors()) {
            return new ModelAndView("film-edit")
                    .addObject("filmForm", filmForm)
                    .addObject("filmId", id);
        }

        try {
            filmService.updateFilm(id, filmForm);
            attr.addFlashAttribute("updateFilm", "Film aggiornato con successo!");
            return new ModelAndView("redirect:/film?id=" + id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Eliminazione film per id - rotta: /delete?id=UUID
    @GetMapping("film/delete/{id}")
    public ModelAndView deleteFilm(@PathVariable("id") UUID id, RedirectAttributes attr) {
        try {
            filmService.deleteSingolo(id);
            attr.addFlashAttribute("deleteFilm", "Film eliminato con successo!");
            return new ModelAndView("redirect:/");
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());    
        }
    }

    // Eliminazione di tutti i film - rotta: /deleteAll
    @GetMapping("film/deleteAll")
    public ModelAndView deleteAllFilms(RedirectAttributes attr) {
        filmService.deleteAll();
        attr.addFlashAttribute("deleteAll", "Tutti i film sono stati eliminati con successo!");
        return new ModelAndView("redirect:/");  
    }

    
}
