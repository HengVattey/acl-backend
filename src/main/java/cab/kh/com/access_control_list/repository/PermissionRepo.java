package cab.kh.com.access_control_list.repository;

import cab.kh.com.access_control_list.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepo extends JpaRepository<Permission, Long> { Optional<Permission> findByName(String n);}