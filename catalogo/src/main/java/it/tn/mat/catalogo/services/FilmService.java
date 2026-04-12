package it.tn.mat.catalogo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.tn.mat.catalogo.domain.Film;
import it.tn.mat.catalogo.domain.FilmForm;
import it.tn.mat.catalogo.repositories.FilmRepository;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;
    
    // Elenco films
    public List<Film> elencoCatalogo() {
        return filmRepository.findAll();
    }

    // Aggiunta film
    public Film newFilm(FilmForm filmForm) {
        Film film = new Film();
        film.setTitolo(filmForm.getTitolo());
        film.setAutore(filmForm.getAutore());
        film.setGenere(filmForm.getGenere().name());
        film.setAnnoPubblicazione(filmForm.getAnnoPubblicazione());
        return filmRepository.save(film);
    }

    // 
    public Film filmPerId(UUID id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film non trovato per UUID: " + id));
    }

    // Modifica film esistente
    public Film updateFilm(UUID id, FilmForm filmForm) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film non trovato per UUID: " + id));

        film.setTitolo(filmForm.getTitolo());
        film.setAutore(filmForm.getAutore());
        film.setGenere(filmForm.getGenere().name());
        film.setAnnoPubblicazione(filmForm.getAnnoPubblicazione());

        return filmRepository.save(film);
    }

    // Eliminazione film per id
    public void deleteSingolo(UUID id) {
        if (!filmRepository.existsById(id)) {
            throw new IllegalArgumentException("Film non trovato per UUID: " + id);
        }
        filmRepository.deleteById(id);
    }

    // Eliminazione di tutti i film
    public void deleteAll() {
        filmRepository.deleteAll();
    }

}
