package tacos.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tacos.domain.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
