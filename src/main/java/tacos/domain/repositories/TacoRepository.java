package tacos.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.entities.Taco;


public interface TacoRepository
        extends JpaRepository<Taco, Long> {

}