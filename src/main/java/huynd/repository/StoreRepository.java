package huynd.repository;

import huynd.domain.Store;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ListStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findAllByStoreGroupStoreGroupID(@Param("storeGroupID") Long storeGroupID);
}
