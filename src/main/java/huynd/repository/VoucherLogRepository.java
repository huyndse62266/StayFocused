package huynd.repository;

import huynd.domain.VoucherLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the VoucherLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherLogRepository extends JpaRepository<VoucherLog, Long> {


    Optional<VoucherLog> findByvoucherVoucherID(@Param("voucherID") Long voucherID);

    List<VoucherLog> findAllByUserIdOrderByVoucherLogDateUsed(@Param("id")Long id);

    void deleteByvoucherVoucherID(@Param("voucherID") Long voucherID);

}
