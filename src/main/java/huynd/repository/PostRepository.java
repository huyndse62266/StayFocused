package huynd.repository;

import huynd.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByStoreGroupStoreGroupID(@Param("storeGroupID") Long storeGroupID);

    List<Post> findAllByStoreGroupStoreTypeStoreTypeIDAndPostEndDateGreaterThan(@Param("storeTypeID") String storeTypeID, @Param("date")Date date);

    List<Post> findAllByStoreGroupStoreTypeStoreTypeIDAndPostTitleContaining(@Param("storeTypeID") String storeTypeID,@Param("postTitle") String postTitle);

    Long countPostByStoreGroupStoreTypeStoreTypeID(@Param("storeTypeID") String storeTypeID);
}
