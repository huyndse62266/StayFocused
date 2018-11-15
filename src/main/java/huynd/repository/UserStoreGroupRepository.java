package huynd.repository;

import huynd.domain.UserStoreGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the UserStoreGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserStoreGroupRepository extends JpaRepository<UserStoreGroup, Long> {
    Optional<UserStoreGroup> findByUserId(@Param("id") Long id);
}
