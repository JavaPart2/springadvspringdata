package be.vdabbeele.springdata.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "werknemers")
@NamedEntityGraph(name = "Werknemer.metFiliaal",
        attributeNodes = @NamedAttributeNode("filiaal"))
public class Werknemer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String voornaam;
    private String familienaam;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "filiaalid")
    private Filiaal filiaal;

    public Werknemer(String voornaam, String familienaam, Filiaal filiaal) {
        this.voornaam = voornaam;
        this.familienaam = familienaam;
        this.filiaal = filiaal;
    }

    protected Werknemer() {
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    public Filiaal getFiliaal() {
        return filiaal;
    }
}
