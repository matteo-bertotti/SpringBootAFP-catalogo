package it.tn.mat.catalogo.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import it.tn.mat.catalogo.domain.Film;

public interface FilmRepository extends JpaRepository<Film, UUID> {
    
}
