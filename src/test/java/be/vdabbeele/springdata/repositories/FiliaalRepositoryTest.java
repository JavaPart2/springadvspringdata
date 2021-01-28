package be.vdabbeele.springdata.repositories;

import be.vdabbeele.springdata.domain.Filiaal;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertFilialen.sql")
public class FiliaalRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String FILIALEN = "filialen";
    private final FiliaalRepository repository;

    public FiliaalRepositoryTest(FiliaalRepository repository) {
        this.repository = repository;
    }

    private long idVanAlfa(){
        return super.jdbcTemplate.queryForObject(
                "select id from filialen where naam = 'Alfa'", Long.class);
    }

    private long idVanBravo(){
        return super.jdbcTemplate.queryForObject(
                "select id from filialen where naam = 'Bravo'", Long.class);
    }

    @Test
    void count(){
        assertThat(repository.count()).isEqualTo(super.countRowsInTable(FILIALEN));
    }

    @Test
    void findById(){
        assertThat(repository.findById(idVanAlfa()).get().getNaam()).isEqualTo("Alfa");
    }

    @Test
    void findAll(){
        assertThat(repository.findAll()).hasSize(super.countRowsInTable(FILIALEN));
    }

    @Test
    void findAllById(){
        assertThat(repository.findAllById(Set.of(idVanAlfa(), idVanBravo())))
                .extracting(filiaal -> filiaal.getId())
                .containsOnly(idVanAlfa(), idVanBravo());
    }

    @Test
    void save(){
        var filiaal = new Filiaal("Delta", "Brugge", BigDecimal.TEN);
        repository.save(filiaal);
        assertThat(filiaal.getId()).isPositive();
        assertThat(super.countRowsInTableWhere(FILIALEN, "id=" + filiaal.getId())).isOne();
    }

    @Test
    void deleteById(){
        long idAlfa = idVanAlfa();
        repository.deleteById(idVanAlfa());
        repository.flush();
        assertThat(super.countRowsInTableWhere(FILIALEN, "id=" + idAlfa)).isZero();
    }

    @Test
    void findByGemeente(){
        var filialen = repository.findByGemeente("Brussel");
        assertThat(filialen).hasSize(2)
                .allSatisfy(filiaal -> assertThat(filiaal.getGemeente()).isEqualTo("Brussel"));
    }

    @Test
    void findByOmzetGreaterThanEqual() {
        var tweeduizend = BigDecimal.valueOf(2_000);
        var filialen = repository.findByOmzetGreaterThanEqual(tweeduizend);
        assertThat(filialen).hasSize(2)
                .allSatisfy(filiaal -> assertThat(filiaal.getOmzet()).isGreaterThanOrEqualTo(tweeduizend));
    }
    @Test
    void findByGemeenteOrderByNaam() {
        var filialen = repository.findByGemeente("Brussel");
        assertThat(filialen).hasSize(2)
                .allSatisfy(filiaal -> assertThat(filiaal.getGemeente()).isEqualTo("Brussel"))
                .extracting(filiaal -> filiaal.getNaam()).isSorted();
    }

    @Test
    void countByGemeente(){
        assertThat(repository.countByGemeente("Brussel")).isEqualTo(2);
    }

    @Test
    void findGemiddeldeOmzet(){
        assertThat(repository.findGemiddeldeOmzet()).isEqualByComparingTo("2000");
    }

    @Test
    void findMetHoogsteOmzet(){
        var filialen = repository.findMetHoogsteOmzet();
        assertThat(filialen).hasSize(1);
        assertThat(filialen.get(0).getNaam()).isEqualTo("Charly");
    }
}
