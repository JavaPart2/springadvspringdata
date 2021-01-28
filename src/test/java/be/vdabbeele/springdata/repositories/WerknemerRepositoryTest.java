package be.vdabbeele.springdata.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Sql("/insertFilialen.sql")
@Sql("/insertWerknemers.sql")
public class WerknemerRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private final WerknemerRepository repository;

    public WerknemerRepositoryTest(WerknemerRepository repository) {
        this.repository = repository;
    }

    @Test
    void findByFiliaalGemeente(){
        var antwerpen ="Antwerpen";
        var werknemers = repository.findByFiliaalGemeente(antwerpen);
        assertThat(werknemers).hasSize(1);
        assertThat(werknemers.get(0).getFiliaal().getGemeente()).isEqualTo(antwerpen);
    }

    @Test
    void findByVoornaamStartingWith(){
        var werknemers = repository.findByVoornaamStartingWith("J");
        assertThat(werknemers).hasSize(2)
                .allSatisfy(werknemer -> assertThat(werknemer.getVoornaam().startsWith("J")));
        assertThat(werknemers).extracting(werknemer -> werknemer.getFiliaal().getNaam());
    }
}
