package huynd.repository;

import huynd.domain.Voucher;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Voucher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query("Select COUNT(v) from Voucher v where v.voucherStatus = false and v.post.postID = :postId ")
    Long countUnusedVoucherByPostID(@Param("postId") Long postId);

    Long countVoucherByPostPostID(@Param("postId") Long postId);

    Optional<Voucher> getTopByVoucherStatusFalseAndPostPostID(@Param("postId") Long postId);

    Optional<Voucher> findOneByVoucherNumber(@Param("voucherName") String voucherNumber);

    List<Voucher> findAllByPostPostID(@Param("postID") Long postID);
}
