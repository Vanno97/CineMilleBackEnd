package it.micael.vanini.cinemille.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sale")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_sala", columnDefinition = "CHAR(36")
    private String idSala;

    @Column(name = "nome_sala", columnDefinition = "VARCHAR(255)")
    private String nomeSala;

    @Column(name = "posti", columnDefinition = "INT")
    private int posti;

    @Column(name = "imax", columnDefinition = "TINYINT(1)")
    private boolean imax;
}
