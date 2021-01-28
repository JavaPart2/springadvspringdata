package be.vdabbeele.springdata.repositories;

import be.vdabbeele.springdata.domain.Filiaal;
import be.vdabbeele.springdata.domain.Werknemer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface WerknemerRepository extends JpaRepository<Werknemer, Long> {
    List<Werknemer> findByFiliaalGemeente(String gemeente);
    @EntityGraph(value = "Werknemer.metFiliaal")
    List<Werknemer> findByVoornaamStartingWith(String woord);
//    List<Filiaal> findByOmzetGreaterThanEqual(BigDecimal vanaf);
//    int countByGemeente(String gemeente);
//    @Query("select avg(f.omzet) from Filiaal f")
//    BigDecimal findGemiddeldeOmzet();
//    List<Filiaal> findMetHoogsteOmzet();
}
