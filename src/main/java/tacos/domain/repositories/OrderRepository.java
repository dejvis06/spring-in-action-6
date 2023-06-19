package tacos.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.entities.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}