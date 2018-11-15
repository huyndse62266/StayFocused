package huynd.repository;

import huynd.domain.PointLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the PointLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointLogRepository extends JpaRepository<PointLog, Long> {

    @Query("select point_log from PointLog point_log where point_log.user.login = ?#{principal.username}")
    List<PointLog> findByUserIsCurrentUser();

    Optional<PointLog> findByvoucherVoucherID(@Param("voucherID") Long voucherID);

    List<PointLog> findAllByUserIdOrderByPointLogDateUsed(@Param("id")Long id);

    void deleteByvoucherVoucherID(@Param("voucherID") Long voucherID);

    @Query("select SUM(p.pointLogPointSpent) from PointLog p where p.user.id = :id")
    Long sumPointLogPointSpentByUserId(@Param("id") Long id);

    List<PointLog> findAllByUserId(@Param("id") Long id);
}
