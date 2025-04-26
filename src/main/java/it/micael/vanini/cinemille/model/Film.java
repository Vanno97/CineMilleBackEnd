package it.micael.vanini.cinemille.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_film", columnDefinition = "CHAR(36)")
    private String idFilm;

    @Column(name = "nome_film", columnDefinition = "VARCAHR(255)")
    private String nomeFilm;

    @Column(name = "data_uscita", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    private Date dataUscita;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "film_programmazioni",
            joinColumns = @JoinColumn(name = "film"),
            inverseJoinColumns = @JoinColumn(name = "programmazione")
    )
    private List<Programmazione> programmazioni;
}
