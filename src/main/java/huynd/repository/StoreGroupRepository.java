package huynd.repository;

import huynd.domain.StoreGroup;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the StoreGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreGroupRepository extends JpaRepository<StoreGroup, Long> {
    List<StoreGroup> findAllByStoreTypeStoreTypeID(@Param("storeTypeID") String storeTypeID);

    Long countStoreGroupByStoreTypeStoreTypeID(@Param("storeTypeID") String storeTypeID);

}
