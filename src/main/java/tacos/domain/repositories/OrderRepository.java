package tacos.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.entities.TacoOrder;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {
}