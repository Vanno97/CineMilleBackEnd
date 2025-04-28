package it.micael.vanini.cinemille.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programmazione")
public class Programmazione implements Comparable<Programmazione> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_programmazioni", columnDefinition = "CHAR(36)")
    private String idProgrammazioni;

    @Column(name = "data_ora", columnDefinition = "DATETIME")
    private Date dataOra;

    @ManyToOne
    @JoinColumn(name = "sala")
    private Sala sala;

    @Override
    public int compareTo(Programmazione o) {
        if (this.dataOra.compareTo(o.getDataOra()) != 0) {
            return this.dataOra.compareTo(o.getDataOra());
        } else {
            return this.sala.getNomeSala().compareTo(o.sala.getNomeSala());
        }
    }
}
