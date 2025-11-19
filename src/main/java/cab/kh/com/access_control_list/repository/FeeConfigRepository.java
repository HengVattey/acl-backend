package cab.kh.com.access_control_list.repository;

import cab.kh.com.access_control_list.model.FeeConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeeConfigRepository extends JpaRepository<FeeConfig, Long> {
    //List<FeeConfig> findAllById(List<Long> feeIds);

//    void deleteById(Long id);
//
//    Optional<FeeConfig> findById(Long id);
}
