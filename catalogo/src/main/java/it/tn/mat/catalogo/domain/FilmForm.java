package it.tn.mat.catalogo.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmForm {
    @NotEmpty
    @Size(max = 50)
    private String titolo;

    @NotEmpty
    @Size(max = 50)
    private String autore;

    @NotNull
    private Genere genere;

    @Min(1888)
    @Max(2100)
    private int annoPubblicazione;
}
