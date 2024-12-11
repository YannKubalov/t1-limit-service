package t1.limit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import t1.limit.entity.UserLimit;

import java.util.Optional;

public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {
    Optional<UserLimit> findByUserId(Long userId);
}