package cab.kh.com.access_control_list.repository;

import cab.kh.com.access_control_list.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String u);
    Optional<User> findByEmail(String e);
}