package it.tn.mat.catalogo.domain;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "film")

public class Film {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;   

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "autore", nullable = false)
    private String autore;

    @Column(name = "genere", nullable = false)
    private String genere;

    @Column(name = "anno_pubblicazione", nullable = false)
    private int annoPubblicazione;
}
